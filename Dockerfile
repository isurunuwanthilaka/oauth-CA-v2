# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -P qa clean package

# Package stage
FROM openjdk:11.0.3-jre-slim-stretch
RUN echo "Asia/Colombo" > /etc/timezone
RUN useradd -ms /bin/bash admin

USER admin

COPY --from=build /home/app/target/demo*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/local/lib/app.jar"]