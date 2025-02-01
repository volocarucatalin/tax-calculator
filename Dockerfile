FROM mysql:8
COPY ./sql/ /docker-entrypoint-initdb.d/

# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application jar file into the container
COPY target/tax-calculator-0.0.1.jar app.jar

# Set environment variables (optional, can also be passed at runtime)
# ENV SOME_ENV_VARIABLE=value

# Command to run the application
CMD ["java", "-jar", "app.jar"]