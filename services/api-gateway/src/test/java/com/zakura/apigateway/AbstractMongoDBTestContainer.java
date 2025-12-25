/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

abstract class AbstractMongoDBTestContainer {

    static final DockerImageName dockerImageName = DockerImageName.parse("mongo:6.0.10");

    protected static final MongoDBContainer MONGO_DB_CONTAINER =
            new MongoDBContainer(dockerImageName).withExposedPorts(27017);

    static {
        MONGO_DB_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setMongoDbContainerURI(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
    }
}
