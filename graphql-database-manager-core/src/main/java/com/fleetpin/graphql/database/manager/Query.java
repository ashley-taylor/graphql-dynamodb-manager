package com.fleetpin.graphql.database.manager;

import java.util.Objects;

public class Query<T extends Table> {
	private final Class<T> type;
	private final String index;
	private final String startsWith;
	private final String after;
	private final Integer limit;
	private final Integer threadCount;
	private final Integer threadIndex;

	Query(Class<T> type, String startsWith, String after, Integer limit, String index, Integer threadCount, Integer threadIndex) {
		if (type == null) {
			throw new RuntimeException("type can not be null, did you forget to call .on(Table::class)?");
		}

		if (threadCount != null && !isPowerOfTwo(threadCount)) {
			throw new RuntimeException("Thread count must be a power of two");
		}

		this.type = type;
		this.startsWith = startsWith;
		this.after = after;
		this.limit = limit;
		this.threadCount = threadCount;
		this.threadIndex = threadIndex;
		this.index = index;
	}

	public Class<T> getType() {
		return type;
	}

	public String getStartsWith() {
		return startsWith;
	}

	public String getAfter() {
		return after;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getThreadCount() { return threadCount; }

	public Integer getThreadIndex() { return threadIndex; }

	public String getIndex() { return index; }

	public boolean hasLimit() {
		return getLimit() != null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(after, limit, startsWith, type, index, threadIndex, threadCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Query other = (Query) obj;
		return (
			Objects.equals(after, other.after) &&
			Objects.equals(limit, other.limit) &&
			Objects.equals(startsWith, other.startsWith) &&
			Objects.equals(type, other.type) &&
			Objects.equals(threadCount, other.threadCount) &&
			Objects.equals(threadIndex, other.threadIndex) &&
			Objects.equals(index, other.index)
		);
	}

	static boolean isPowerOfTwo(int n)
	{
		if (n == 0)
			return false;

		return (int)(Math.ceil((Math.log(n) / Math.log(2))))
				== (int)(Math.floor(
				((Math.log(n) / Math.log(2)))));
	}
}
