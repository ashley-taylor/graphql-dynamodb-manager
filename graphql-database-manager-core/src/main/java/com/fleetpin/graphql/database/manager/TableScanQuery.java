package com.fleetpin.graphql.database.manager;

import java.util.List;

public record TableScanQuery(TableScanMonitor monitor, Integer parallelism, List<ScanUpdater<?>> updaters) {
	interface TableScanMonitor {
		public void onScanSegmentStart(int segment, int itemCount, Object from);

		public void onScanSegmentComplete(int segment);
	}
}
