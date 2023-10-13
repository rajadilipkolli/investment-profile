# API Gateway


## Run Spring Boot application
```shell
mvn spring-boot:run
```

### Run tests
```shell
./mvnw clean verify
```

### Run locally
```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Building native image
```shell
./mvnw -Pnative spring-boot:build-image -DskipTests 
```

### UseFul Urls

* swagger `http://localhost:8085/api-gateway/swagger-ui/index.html`
