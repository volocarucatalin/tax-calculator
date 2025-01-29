# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application jar file into the container
COPY target/tax-calculator-0.0.1.jar app.jar

# Expose the port your application is running on
EXPOSE 8087

# Set environment variables (optional, can also be passed at runtime)
# ENV SOME_ENV_VARIABLE=value

# Command to run the application
CMD ["java", "-jar", "app.jar"]