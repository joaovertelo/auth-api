FROM amazoncorretto:21

WORKDIR /app

COPY target/auth-api-0.0.1-SNAPSHOT.jar /app/spring-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-app.jar"]