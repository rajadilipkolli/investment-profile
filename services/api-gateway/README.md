# API Gateway


## Run Spring Boot application
```shell
mvn spring-boot:run
```

### Run tests
`$ ./mvnw clean verify`

### Run locally
```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
### UseFul Urls

* swagger `http://localhost:8085/api-gateway/swagger-ui/index.html`