## Run Spring Boot application
```shell
./mvnw spring-boot:run
```

### Run tests
```shell
./mvnw clean verify
```

### Run locally
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Using Testcontainers at Development Time
You can run `TestSipServiceApplication.java` from your IDE directly.
You can also run the application using Maven as follows:

```shell
./mvnw spotless:apply spring-boot:test-run
```


### SIP Return Calculation
> The formula for SIP return calculation is based on the formula for future value of annuity-due.

> FV = P × ((1 + i)n - 1) / i) × (1 + i)

> Where,
> FV = Future value
> P = Amount invested at the start of every payment interval
> n = Number of payments
> i = Periodic interest rate
> r = Expected return rate in % per annum

> Monthly SIP Formula
> For a monthly SIP payment P for a period of n months:

> Maturity amount = P × ((1 + i)n - 1) / i) × (1 + i)

> where,
> i = r / 100 / 12
