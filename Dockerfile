## BUILD ##
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY . .

RUN mvn -q clean package -Dmaven.test.skip=true


## RUN ##
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
