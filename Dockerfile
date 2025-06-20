# Etapa 1: build (modo JVM)
FROM quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-21 AS build
WORKDIR /code
COPY --chown=quarkus:quarkus --chmod=0755 mvnw ./
COPY --chown=quarkus:quarkus .mvn .mvn
COPY --chown=quarkus:quarkus pom.xml ./
USER quarkus
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagem final (modo JVM)
FROM registry.access.redhat.com/ubi9/openjdk-21-runtime
WORKDIR /work/

# Copia o app JVM
COPY --from=build /code/target/quarkus-app /work/quarkus-app

# Troca para root para mudar permissões
USER root
RUN chown -R 1001:root /work \
 && chmod -R "g+rwX" /work

EXPOSE 8080

# Volta ao usuário não-root para rodar o app
USER 1001
CMD ["java", "-jar", "/work/quarkus-app/quarkus-run.jar", "-Dquarkus.http.host=0.0.0.0"]
