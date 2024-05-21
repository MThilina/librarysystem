# Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host machine to the container
COPY target/library-system.jar library-system

# Expose the port that the application will run on
EXPOSE 8080

ENV DATASOURCE_URL=jdbc:h2:mem:libraryDb
ENV DATASOURCE_DRIVER=org.h2.Driver
ENV USER_NAME=management
ENV PASSWORD=management
ENV DATASOURCE_DIALECT=org.hibernate.dialect.H2Dialect

# Enable H2 console
ENV H2_CONSOLE=true
ENV ACTIVE_PROFILE=local

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "library-system"]