FROM java:8
MAINTAINER Subhash
EXPOSE 8085
VOLUME /tmp
ADD target/apigatewayservice-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]