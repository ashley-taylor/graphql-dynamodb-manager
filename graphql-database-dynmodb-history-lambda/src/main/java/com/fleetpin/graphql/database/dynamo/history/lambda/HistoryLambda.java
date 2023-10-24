package com.fleetpin.graphql.database.dynamo.history.lambda;

import static java.util.stream.Collectors.groupingBy;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fleetpin.graphql.database.manager.dynamo.HistoryUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.Record;
import software.amazon.awssdk.services.dynamodb.model.WriteRequest;

public abstract class HistoryLambda implements RequestHandler<DynamodbEvent, Void> {

	public HistoryLambda() {}

	public abstract String getTableName();

	public abstract DynamoDbAsyncClient getClient();

	@Override
	public Void handleRequest(DynamodbEvent input, Context context) {
		var records = com.amazonaws.services.lambda.runtime.events.transformers.v2.DynamodbEventTransformer.toRecordsV2(input);
		process(records);
		return null;
	}

	public void process(List<Record> records) {
		int chunkSize = 25;

		AtomicInteger counter = new AtomicInteger();
		var chunks = HistoryUtil
			.toHistoryValue(records.stream())
			.map(item -> WriteRequest.builder().putRequest(builder -> builder.item(item)).build())
			.collect(groupingBy(x -> counter.getAndIncrement() / chunkSize))
			.values();

		var futures = chunks
			.stream()
			.filter(chunk -> !chunk.isEmpty())
			.map(chunk -> {
				var items = new HashMap<String, List<WriteRequest>>();
				items.put(getTableName(), chunk);
				return writeItems(items);
			})
			.toArray(CompletableFuture[]::new);

		try {
			CompletableFuture.allOf(futures).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private CompletableFuture<Object> writeItems(Map<String, List<WriteRequest>> items) {
		return getClient()
			.batchWriteItem(builder -> builder.requestItems(items))
			.thenCompose(resposne -> {
				var unprocessed = resposne.unprocessedItems();

				if (unprocessed.isEmpty()) {
					return CompletableFuture.completedFuture(null);
				} else {
					return writeItems(unprocessed);
				}
			});
	}
}
