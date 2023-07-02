# Stock Service

### Run tests
`$ ./mvnw clean verify`

### Run locally
```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Urls
Swagger - http://localhost:8083/api-gateway/stock-service/swagger-ui.html