# Video Rental Store
A REST API that represents a system for video rental store. The repository also contains a simple React front.

#### Technologies used:
- **Java SDK:** v13.0.1
- **Apache Maven:** v3.6.3
- **SpringToolSuite4:** v4.6.0
- **Operating System:** macOS Mojave v10.14.6

## Contents
- [Backend](#backend)
- [Frontend](#frontend)

## Backend
The development used Spring Boot with JPA, Hibernate, Maven, TomCat, JUnit, H2 database, Swagger documentation and JWT authentication.

### Install
To install, run the following command at the project root:

```
mvn install
```

### Build
To build the package, run the following command at the project root:

```
mvn clean package spring-boot:repackage
```

To start the project:

```
java -jar target/spring-boot-locadora-api-0.0.1-SNAPSHOT.jar
```

### Tests
The tests check the situations:
- Add user under 18
- Add user over 18 years old
- User with invalid CPF
- User with valid CPF
- User to rent a maximum of 5 films at a time
- A maximum of two renewals
- Among others

To run the tests, run the following command at the project root:

```
mvn test
```

### Start
To start the project, run the following command at the project root:

```
mvn spring-boot:run
```

### Host, Port and Base Path
[http://localhost:8088/](http://localhost:8088/)

### Swagger Documentation
To view the API documentation with the details of each endpoints, visit:

[http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html)

### Database
The relational database H2 was used. Because it is in-memory, H2 facilitates testing and implementation. To access the H2 console, visit:

[http://localhost:8088/h2-console](http://localhost:8088/h2-console)

Fields:  
**JDBC URL: ** 

```
jdbc:h2:mem:testdb
```
**User name: ** sa  
**Password: **  

#### Database diagram

![](https://github.com/davibaltar/rest-api-spring-boot/blob/master/diagrama_db.png?raw=true)

## Frontend
The frontend was developed using React + Material UI. The front is not completely finished.
 
[http://localhost:3000](http://localhost:3000)

To install the dependencies, run the following command at the project root:

```
npm install
```
To run the project, run the following command at the project root:

```
npm start
```

#### Front images

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-01.png?raw=true)

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-02.png?raw=true)

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-03.png?raw=true)

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-04.png?raw=true)

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-05.png?raw=true)

![](https://github.com/davibaltar/public-store/blob/master/front-locadora-06.png?raw=true)
