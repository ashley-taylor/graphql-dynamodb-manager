package com.fleetpin.graphql.database.manager;

import java.util.ArrayList;
import java.util.function.Consumer;

public record ScanResult(ArrayList<Item<?>> items, Object next) {
	public record Item<T extends Table>(String organisationId, T entity, Consumer<T> replace, Consumer<T> delete) {}
}
