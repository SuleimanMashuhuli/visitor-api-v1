ARG VERSION=0.0.1-SNAPSHOT
FROM eclipse-temurin:17-jdk-jammy
LABEL maintainer="suleiman.mashuhuli@abcthebank.com"
WORKDIR /app
COPY target/visitor-api-0.0.1-SNAPSHOT.jar ./
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "./visitor-api-0.0.1-SNAPSHOT.jar"]
