# Library System

A simple library system with RESTful APIs for managing borrowers and books, implemented using Java 17 and Spring Boot.

## Table of Contents

- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)


## Project Structure

```text
library-system/
├── src/
│   ├── main/
│   │   ├── java/com/librarysystem/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── LibrarySystemApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   ├── test/
│   │   ├── java/com/librarysystem/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   └── LibrarySystemApplicationTests.java
│   │   └── resources/
│   │       └── application-test.properties
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```
## Getting Started
### Prerequisites
- Java 17
- Maven 3.6+
- Docker
- Docker Compose

### Installation
- Clone the repository:
```
  git clone https://github.com/MThilina/librarysystem.git
  cd librarysystem
```
- Build the application:
```
  mvn clean package
```
### Running the Application

- Run the application locally:

```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
- Build the Docker image:
```
docker build -t library-system .
```
- Run the Docker container:
```
docker run -p 8080:8080 library-system
```
- Run Docker Compose:
```
docker-compose up --build
```

### API Documentation

 The API documentation is available via Swagger. Once the application is running, you can access it at:

```
http://localhost:8080/swagger-ui.html
```

### Testing

  This segment consist with the test cases.Test coverage report included in the context path in the test_coverage_report directory.
  It contains a index.html and by browsing it can identify the test coverage of the system.
  In order to execute test cases;
  
```
mvn test
```
### Deployment

#### Using Docker

- Build the Docker Image:
```
docker build -t library-system .
```

- Run the Docker Container:
```
docker run -p 8080:8080 library-system
```

#### Using Docker Compose

- Build and Start the Services:
```
docker-compose up --build
```

- Stop the Services:
```
docker-compose down
```

## Contributing
Contributions are welcome! Please submit a pull request or open an issue to discuss what you would like to change.