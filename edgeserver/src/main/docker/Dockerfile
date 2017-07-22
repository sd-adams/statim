FROM java:8
VOLUME /tmp
ADD ./target/edgeserver-0.0.1.jar app.jar
EXPOSE 8080
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]