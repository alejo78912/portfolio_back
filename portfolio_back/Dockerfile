FROM maven:3.8.1-openjdk-17-slim as build
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/alejandro-0.0.1-SNAPSHOT.jar .

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "alejandro-0.0.1-SNAPSHOT.jar"]