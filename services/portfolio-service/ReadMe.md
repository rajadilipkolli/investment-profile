# Portfolio Service

There is an issue with AOT in native images,hence for time being reverting to normal images

### Run tests
`$ ./mvnw clean verify`

### Run locally
```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### URL's

* Swagger `http://localhost:8082/portfolio-service/swagger-ui/index.html`