FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /target/realestate-0.0.1-SNAPSHOT.jar realestate.jar
ENTRYPOINT ["java","-jar","realestate.jar"]