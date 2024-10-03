# Start with the official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code to the working directory
COPY pom.xml .
COPY src ./src

# Package the application (skip tests if needed)
RUN mvn clean package -DskipTests

# Start with the OpenJDK 17 image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/your-app-name.jar /app/app.jar

# Expose the port your application runs on (optional)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
