# Stock-market
A small production ready Spring boot application for stock trading. It is protected by OAuth2

### Prerequisite
1. Mysql server
2. JDK
3. Maven
4. IDE like Intellij
 
### How to Run ###
1. Pull the source code from Github
2. Include the project in Intellij
3. Change values the application.properties settings.
4. __mvn clean install__ to build the project.
5. Sample __Postman__ request are in folder resources/templates


### Getting Access Token ###
1. http request to _http://localhost:8080/oauth/token_
2. User Credentials
    1. grant type : password
    2. username : user
    3. password : user
    4. client_id : client
    5. client_secret : clientpassword


### Contact ###
email : ___sidharthdash19@gmail.com___
