# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package

# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
