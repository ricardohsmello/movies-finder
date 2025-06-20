# Stage 1: Build the application
FROM registry.access.redhat.com/ubi8/openjdk-17 AS build
WORKDIR /app

# Copy the Maven wrapper and project files
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ ./src/

# Build the Quarkus application
RUN ./mvnw package -Dquarkus.package.type=fast-jar -DskipTests

# Stage 2: Create the final runtime image
FROM registry.access.redhat.com/ubi8/openjdk-17-runtime
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/quarkus-app/lib/ ./lib/
COPY --from=build /app/target/quarkus-app/*.jar ./
COPY --from=build /app/target/quarkus-app/app/ ./app/
COPY --from=build /app/target/quarkus-app/quarkus/ ./quarkus/

# Expose the application port
EXPOSE 8080

# Define the entry point to run the Quarkus application
CMD ["java", "-jar", "quarkus-run.jar"]