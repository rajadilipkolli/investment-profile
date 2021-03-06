## Run Spring Boot application
```
mvn spring-boot:run
```
# SIP Return Calculation
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
