Technology Stack: -
===================
1. Java jdk 1.8
2. Apache Maven 3.6.3

Build all the projects with below command: -
==========================================
mvn clean install

Run springboot applications using "mvn spring-boot:run" command in command prompt in below order: -
================================================================================================
1. discovery-service
2. sip-service
3. stock-service
4. portfolio-service
5. api-gateway

	Note:- You can also import all these spring boot apps/projects in eclipse/sts and run as "Java Application" OR "Spring Boot App"

Eureka Server: -
================================================
http://localhost:8761/

SwaggerUI Urls: -
================================================
http://localhost:8081/api-gateway/swagger-ui/
http://localhost:8082/portfolio-service/swagger-ui/
http://localhost:8083/stock-service/swagger-ui/
http://localhost:8084/sip-service/swagger-ui/

Note:- 
================================================
1. Please check the Postman collections shared along to setup the uri's in postman. While hitting any post request from postman, please ensure that the mediatype of the body should be 'application/json'. While exporting the collection, that might have changed to 'raw' type
2. All the requests are being passed from api-gateway and being authorized.


## Mongo DB

Connect to MongoDB Server using MongoDB compass or you can use command prompt to do so: -
======================================================================================
1. Using command prompt: -
   Run below commands to run mongo server: -
   >> mongod

Note:
1. If you have specified your own DB path, start mongodb server using your dbpath. For example if your dbpath is "D:\Mongo\server\data" Run below commands to start mongo server: -
>> mongod --dbpath "D:\Mongo\server\data"
2. Open Mongo CLI in new command prompt window: -
>> mongo

Run below commands in Mongo CLI: -
-------------------------------

For login-service: -
=================================
1. create DB: -
   >> use auth_db
2. >> db.users.insertOne(
   {"firstName":"firstName",
   "lastName":"lastName",
   "email":"test@mail.com",
   "password":"$2a$10$mrq0WtVfOzS0SuDYCjLoVOSwmgUlJE7z4Iq8fC3LQkqNayzrY9tXq",
   "pan":"ABCD234567",
   "phone":1234323432})

3. >> db.roles.insertMany([
   { name: "ROLE_USER" },
   { name: "ROLE_MODERATOR" },
   { name: "ROLE_ADMIN" }])


For Stock Service: -
================================
1. create DB: -
   >> use stock_db
2. Insert data in 'stocks' collection: -
   >> db.stocks.insertMany([
   {name: 'Birla', investmentType: 'Stock', currentPrice: 145, anticipatedGrowth: 15, term: 2},
   {name: 'DSP', investmentType: 'Mutual Fund', currentPrice: 220, anticipatedGrowth: 10, term: 4},
   {name: 'Maruti', investmentType: 'Stock', currentPrice: 86, anticipatedGrowth: 5, term: 1},
   {name: 'SBI',  investmentType: 'Fix Deposit', currentPrice: 130, anticipatedGrowth: 17, term: 7}])
3. Insert data in 'types' collection: -
   >> db.types.insertMany([
   { name: "STOCK" },
   { name: "MUTUAL_FUND" },
   { name: "FIXED_DEPOSIT" }])

For portfolio service: -
===============================
1. create DB: -
   >>use portfolio_db

2. Insert data in investments collection: -
   >> db.investments.insertMany([
   {userName: "test@mail.com", name: 'Sensex', type: 'Stock', quantity:100, costPrice: 500, currentPrice: 625, profitLossPercent: 25, profit: true},
   {userName: "test@mail.com", name: 'Nifty', type: 'Stock', quantity:10, costPrice: 50, currentPrice: 44, profitLossPercent: 12, profit: false},
   {userName: "test@mail.com", name: 'Reliance', type: 'Mutual Fund', quantity:25, costPrice: 350, currentPrice: 375, profitLossPercent: 24.5, profit: true},
   {userName: "test@mail.com", name: 'Mrf',  type: 'Fix Deposit', quantity:160, costPrice: 600, currentPrice: 1200, profitLossPercent: 100, profit: true}])
