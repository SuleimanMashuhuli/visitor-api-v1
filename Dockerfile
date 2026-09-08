ARG VERSION=0.0.1-SNAPSHOT
FROM openjdk-17-jdk-slim
LABEL maintainer="suleiman.mashuhuli@abcthebank.com"
WORKDIR /app
COPY target/visitor-api-0.0.1-SNAPSHOT.jar ./
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "./visitor-api-0.0.1-SNAPSHOT.jar"]
