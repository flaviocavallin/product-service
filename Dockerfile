FROM maven:3.3-jdk-8 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jre-alpine
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE

ENV SERVICE_DIR /home/service
RUN mkdir -p $SERVICE_DIR

COPY --from=build /home/app/target/product-service-*.jar $SERVICE_DIR/app.jar

ENTRYPOINT java -jar $SERVICE_DIR/app.jar