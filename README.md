[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/rajadilipkolli/investment-profile)

# investment-profile
This repository is the sample microservices based end to end solutions which demonstrates key microservices architecture patterns

## Tools Required
 * JDK 17+
 * Node 18+
 * IDE(IntelliJ Or VSCode or STS)

## Steps to start backend application

Goto Services folder and issue command 

```shell
docker compose up
```

this will bring mongo and zipkin up which will be used as infrastructure for all projects.
Bring all services up in following order

1. discovery-service
2. api-gateway
3. portfolio-service
4. sip-service
5. stock-service

Verify if all services are up here

http://localhost:8761


## Steps to start the application and open webpage(Please setup mongodb and backend services) ahead :


Step 1: - use below command in cmd: -

> 
```bash
 npm install -g @angular/cli
```
 
>
```bash
 ng serve
```

Step 2: - Open Google Chrome and hit **http://localhost:4200**

step 3: - For convenience, Test user is created which can be used to login. you can also create your own profile(signup) and start using application.


Test User Details: -
==================

Email: test@mail.com
password: welcome123


Note: If you are facing error related to Angular Material, Consider doing below: -


1. `ng add @angular/material`
   Note: select below options while installing: -
   ? Choose a prebuilt theme name, or "custom" for a custom theme: Indigo/Pink
   ? Set up global Angular Material typography styles? Yes
   ? Set up browser animations for Angular Material? Yes

 
## Changes in code
 - Due to breaking changes in spring boot 2.6.0 we cannot use spring fox 3.0.0 starter unless it is fixed, hence migrated to **springdoc openui** -  Wont switch back to spring fox
 - Upgraded to **Java 17**
 - Converted all maven wrappers to **Apache Maven Wrapper** using command `mvn wrapper:wrapper`
 - Since api-gateway is migrated to spring cloud gateway which under the hood uses webflux `server.servlet.context-path` is no longer valid, it should be replaced with `spring.webflux.base-path` 