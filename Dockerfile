## Multi-stage build for GoFDFS Movie Ticket Booking System
## Stage 1: Build with Maven (produces fat jar via assembly plugin)
FROM maven:3.9.9-eclipse-temurin-11 AS build
WORKDIR /app

# Pre-copy pom to leverage dependency layer caching
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline || true

# Copy source and build
COPY src ./src
RUN mvn -q -DskipTests clean package assembly:single

## Stage 2: Minimal runtime image
FROM eclipse-temurin:11-jre
WORKDIR /app

# Copy the shaded jar
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar

# Expose the Jetty port
EXPOSE 3000

# Allow passing custom JVM opts (e.g., -Xms128m -Xmx256m)
ENV JAVA_OPTS=""

# Health info
LABEL org.opencontainers.image.title="GoFDFS Movie Ticket Booking" \
      org.opencontainers.image.description="Java Servlet + Embedded Jetty movie ticket booking demo" \
      org.opencontainers.image.source="https://github.com/madhav-1204/GoFDFS-movie-ticket-booking" \
      org.opencontainers.image.licenses="MIT"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
