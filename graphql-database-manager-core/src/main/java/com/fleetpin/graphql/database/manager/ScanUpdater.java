package com.fleetpin.graphql.database.manager;

import com.fleetpin.graphql.database.manager.ScanResult.Item;
import java.util.function.BiConsumer;

public record ScanUpdater<T extends Table>(Class<?> type, BiConsumer<ScanContext<T>, T> updater) {
	public static class ScanContext<T extends Table> {

		private VirtualDatabase virtualDatabase;
		private Item<T> item;

		protected ScanContext(VirtualDatabase virtualDatabase, Item<T> item) {
			this.virtualDatabase = virtualDatabase;
			this.item = item;
		}

		public VirtualDatabase getVirtualDatabase() {
			return virtualDatabase;
		}

		public void delete() {
			item.delete().accept(item.entity());
		}

		public void replace(T entity) {
			item.replace().accept(entity);
		}
	}
}
