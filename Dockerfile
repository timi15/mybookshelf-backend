FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY checkstyle.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-Xms64m", "-Xmx256m", "-XX:MaxMetaspaceSize=256m", "-XX:+UseContainerSupport", "-jar", "app.jar"]