# Use Maven official image to build the application
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy pom.xml and download project dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the entire project
COPY . .

# Build the project
RUN mvn clean package

# Use OpenJDK 17 to run the app
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Expose the port on which the application will run
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
#Bilmia
