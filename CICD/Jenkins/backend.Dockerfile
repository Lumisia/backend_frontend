FROM eclipse-temurin:17-jdk AS builder

WORKDIR /workspace

COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY gradle gradle
COPY src src

RUN chmod +x gradlew && ./gradlew clean bootJar -x test

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
