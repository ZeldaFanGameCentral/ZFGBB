FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /usr/src
ADD ./mvn* ./
ADD ./pom.xml ./
ADD ./mbg-plugin/pom.xml ./mbg-plugin/pom.xml
ADD ./model/pom.xml ./model/pom.xml
ADD ./migrator/pom.xml ./migrator/pom.xml
ADD ./app/pom.xml ./app/pom.xml
RUN mvn -pl app -am -DskipTests dependency:go-offline

ADD ./mbg-plugin/src ./mbg-plugin/src
ADD ./model/src ./model/src
ADD ./migrator/src ./migrator/src
ADD ./app/src ./app/src
RUN mvn clean package -Dmaven.test.skip=true

# FIXME: This image should be switched to gcr.io/distroless/java-base-debian12 because it is much smaller. For now, this will work.
FROM tomcat:jre21-temurin-noble AS deploy

# Backup format v1 supports PostgreSQL 18 only, including its client tools.
RUN apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates curl \
    && install -d /usr/share/postgresql-common/pgdg \
    && curl -fsSL https://www.postgresql.org/media/keys/ACCC4CF8.asc \
      -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc \
    && . /etc/os-release \
    && echo "deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt ${VERSION_CODENAME}-pgdg main" \
      > /etc/apt/sources.list.d/pgdg.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends postgresql-client-18 \
    && rm -rf /var/lib/apt/lists/*

ARG ZFGBB_BUILD_VERSION
ENV ZFGBB_BUILD_VERSION=$ZFGBB_BUILD_VERSION

# Copy WAR
COPY --from=build /usr/src/app/target/zfgbb.war /usr/local/tomcat/webapps/

# Create the configured content root inside the image.
RUN mkdir -p \
    /usr/local/tomcat/webapps/content \
    /var/lib/zfgbb/operations \
    /usr/local/tomcat/conf/Catalina/localhost \
    && chown -R 1000:1000 /usr/local/tomcat/webapps \
    /var/lib/zfgbb \
    /usr/local/tomcat/conf/Catalina \
    /usr/local/tomcat/logs \
    /usr/local/tomcat/temp \
    /usr/local/tomcat/work

EXPOSE ${ZFGBB_BACKEND_PORT:-8080}
USER 1000:1000
CMD ["catalina.sh", "run"]


FROM postgres:18-alpine AS database
