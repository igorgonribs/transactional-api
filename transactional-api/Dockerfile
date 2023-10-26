# Build stage
FROM maven:3.8.5-openjdk-17 AS builder
COPY . /home/app/
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true

RUN mv /home/app/target/transactional-api-*.jar /usr/local/lib/transactional-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/transactional-api.jar"]