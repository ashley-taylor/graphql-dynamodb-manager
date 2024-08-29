package com.fleetpin.graphql.database.manager.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.function.Supplier;

public class ObjectMapperCreator implements Supplier<ObjectMapper> {

	@Override
	public ObjectMapper get() {
		return new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.registerModule(new ParameterNamesModule())
			.registerModule(new Jdk8Module())
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
			.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
			.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
}
