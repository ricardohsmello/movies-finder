## Build stage
FROM registry.access.redhat.com/ubi9/openjdk-21:1.21 AS build

# Copy Maven wrapper and pom.xml first for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

## Runtime stage
FROM registry.access.redhat.com/ubi9/openjdk-21:1.21

ENV LANGUAGE='en_US:en'

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --from=build --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"
ENV URL=""
ENV BEARER=""
ENV MODEL=""
ENV MONGODB_URI=""

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]