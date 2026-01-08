FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY checkstyle.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-Xms64m", "-Xmx256m", "-XX:MaxMetaspaceSize=64m", "-XX:+UseContainerSupport", "-jar", "app.jar"]