# Discovery Service

### Run tests
```shell
./mvnw clean verify
```

### Run locally
```shell
$ docker-compose -f docker/docker-compose.yml up -d
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```