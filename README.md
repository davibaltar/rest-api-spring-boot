# REST API using Spring Boot
A REST API developed using Spring Boot with JPA, Hibernate, Maven, TomCat, JUnit and H2 database. The API represents a TODO list. Job is a computer routine which can have depedency's from others jobs and cannot have self-dependency between jobs.

#### Technologies used:
- **Java SDK:** v13.0.1
- **Apache Maven:** v3.6.3
- **SpringToolSuite4:** v4.6.0
- **Operating System:** macOS Mojave v10.14.6

## Build

To build the package, run the following command at the project root:

```
mvn clean package spring-boot:repackage
```
To start the project:

```
java -jar target/unisys-test-api-0.0.1-SNAPSHOT.jar
```

## Tests
To perform automated tests, run the following command at the root of the project:

```
mvn test
```

## Start

To start the project, run the following command at the root of the project:

```
mvn spring-boot:run
```

## Endpoints
Base path: [http://localhost:8080/](http://localhost:8080/)

## Database Access
The database used was H2. To use the H2 console, access:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

and change the **JDBC URL** field to:

```
jdbc:h2:mem:testdb
```

## Notes

1. If the **parentJob** parameter pertaining to *Job* is null or the reference is not found, *Job* will not be added.

2. To comply with the removal of the *Job* according to the documentation, the references to the removed *Job* will also be deleted. In a real environment this could cause instability in the database, but as this is just a test, there will be no problems.

3. If *Job* is not added because it breaks an insertion rule, an empty *Job* object will be returned, with **id** equal to *null*.

4. When a *Job* is deleted, its respective *Tasks* are also deleted.