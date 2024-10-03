# Car Demo Project

## Used technologies

- Java 23
- Maven 3.3.2
- PostgreSQL
- Spring Boot 3.3.4
  - Spring REST controller
  - Spring DI
- JPA
- Lombok
- slf4j
- Mockito
- JUnit

*The project was built in IntelliJ IDEA*

## Setup

In the project root directory use:

- `mvn spring-boot:run` to run the application
- `mvn test` to run the tests

The source files can be found under *src/main*.  
The tests can be found under *src/test*.

The application expects that there is a PostgreSQL database on the localhost port 5432. The database URL can be changed in the
*src/main/resources/application.properties* file.

## Usage

The application handles requests made to the http://localhost:8080/car root URL and the following sub-locations:

- [/start](http://localhost:8080/car/start) - starts the car
- [/stop](http://localhost:8080/car/stop) - stops the car
- [/refuel](http://localhost:8080/car/refuel) - refuels the car
- [/fuel-level](http://localhost:8080/car/fuel-level) - displays the fuel level
- [/dashboard](http://localhost:8080/car/dashboard) - displays the dashboard