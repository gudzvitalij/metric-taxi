FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/MetricTaxi-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","MetricTaxi-0.0.1-SNAPSHOT.jar"]