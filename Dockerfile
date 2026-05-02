FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /usr/src
ADD ./mvn* ./
ADD ./pom.xml ./
ADD ./mbg-plugin/pom.xml ./mbg-plugin/pom.xml
ADD ./model/pom.xml ./model/pom.xml
ADD ./migrator/pom.xml ./migrator/pom.xml
ADD ./app/pom.xml ./app/pom.xml

ADD ./mbg-plugin/src ./mbg-plugin/src
ADD ./model/src ./model/src
ADD ./migrator/src ./migrator/src
ADD ./app/src ./app/src
RUN mvn clean package -Dmaven.test.skip=true

# FIXME: This image should be switched to gcr.io/distroless/java-base-debian12 because it is much smaller. For now, this will work.
FROM tomcat:jre21-temurin-noble AS deploy

# Copy WAR
COPY --from=build /usr/src/app/target/zfgbb.war /usr/local/tomcat/webapps/

# Create content directories INSIDE the image
RUN mkdir -p \
    /usr/local/tomcat/webapps/content/images \
    && chown -R 1000:1000 /usr/local/tomcat/webapps

EXPOSE ${ZFGBB_BACKEND_PORT:-8080}
CMD ["catalina.sh", "run"]


FROM postgres:16-alpine AS database
