FROM openjdk:8

RUN apt-get update && apt-get install -y maven

ENV TEST_RESOURCE_PATH=/app/templates

WORKDIR /app
COPY ./ /app

RUN ["mvn", "clean", "package"]
EXPOSE 8000

CMD ["java", "-jar", "target/freemarker-test-1.0-SNAPSHOT.jar"]
