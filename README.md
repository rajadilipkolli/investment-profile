# investment-profile
end to end application

## Steps to start backend application

Goto Services folder and issue command `docker compose up`, this will bring mongo and zipkin up which will be used as infrastructure for all projects.
Bring all services up in following order

* discovery-service
* api-gateway
* portfolio-service
* sip-service
* stock-service

## Steps to start the application and open webpage(Please setup mongodb and backend services before): -
=================================================================================================
Step 1: - use below command in cmd: -
> npm install -g @angular/cli
 
> ng serve

Step 2: - Open Google Chrome and hit localhost:4200

step 3: - For convenience, I have already set up a test user to login. you can also create your own profile(signup) and start using application.

Test User Details: -
==================

Email: test@mail.com
password: welcome123


Note: If you are facing error related to Angular Material, Consider doing below: -
1. ng add @angular/material
   Note: select below options while installing: -
   ? Choose a prebuilt theme name, or "custom" for a custom theme: Indigo/Pink
   ? Set up global Angular Material typography styles? Yes
   ? Set up browser animations for Angular Material? Yes

 
## Changes in code
Due to breaking changes in spring boot 2.6.0 we cannot use spring fox 3.0.0 starter unless it is fixed, hence migrated to springdoc openui
