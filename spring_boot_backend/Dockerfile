FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY spring_boot_backend/pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY spring_boot_backend/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]