package com.fleetpin.graphql.database.manager;

import com.fleetpin.graphql.database.manager.ScanUpdater.ScanContext;
import com.fleetpin.graphql.database.manager.TableScanQuery.TableScanMonitor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TableScanQueryBuilder {

	private int parallelism = Runtime.getRuntime().availableProcessors() * 5;
	private List<ScanUpdater<?>> updaters = new ArrayList<>();
	private TableScanMonitor monitor;

	public TableScanQueryBuilder parallelism(int parallelism) {
		this.parallelism = parallelism;
		return this;
	}

	public TableScanQueryBuilder updater(ScanUpdater<?> updater) {
		updaters.add(updater);
		return this;
	}

	public <T extends Table> TableScanQueryBuilder updater(Class<T> type, BiConsumer<ScanContext<T>, T> updater) {
		updaters.add(new ScanUpdater<T>(type, updater));
		return this;
	}

	public TableScanQuery build() {
		return new TableScanQuery(monitor, parallelism, updaters);
	}
}
