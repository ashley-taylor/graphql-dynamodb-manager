package com.fleetpin.graphql.database.manager.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@com.fleetpin.graphql.database.manager.test.annotations.TestDatabase(
	objectMapper = ObjectMapperCreator.class,
	classPath = "com.fleetpin.graphql.database.manager.test"
)
public @interface TestDatabase {
}
