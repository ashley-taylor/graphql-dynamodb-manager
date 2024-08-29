package com.fleetpin.graphql.database.manager.test.hashed;

import com.fleetpin.graphql.database.manager.test.ObjectMapperCreator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@com.fleetpin.graphql.database.manager.test.annotations.TestDatabase(
	objectMapper = ObjectMapperCreator.class,
	classPath = "com.fleetpin.graphql.database.manager.test.hashed",
	hashed = true
)
public @interface TestDatabase {
}
