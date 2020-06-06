FROM openjdk:13-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE

ENV SERVICE_DIR /home/service
RUN mkdir -p $SERVICE_DIR

COPY target/product-service-*.jar $SERVICE_DIR/app.jar

ENTRYPOINT java -jar $SERVICE_DIR/app.jar