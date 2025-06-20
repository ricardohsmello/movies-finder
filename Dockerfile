# Etapa 1: build (modo JVM)
FROM quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-21 AS build
WORKDIR /code
COPY --chown=quarkus:quarkus --chmod=0755 mvnw ./
COPY --chown=quarkus:quarkus .mvn .mvn
COPY --chown=quarkus:quarkus pom.xml ./
COPY src src
USER quarkus

RUN ./mvnw clean package -DskipTests


FROM registry.access.redhat.com/ubi9/openjdk-21:1.21

ENV LANGUAGE='en_US:en'

COPY --from=build /code/target/quarkus-app /deployments/quarkus-app
COPY --from=build /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /code/target/quarkus-app/*.jar /deployments/
COPY --from=build /code//target/quarkus-app/app/ /deployments/app/
COPY --from=build /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-app/quarkus-run.jar"


ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
