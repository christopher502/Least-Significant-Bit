FROM openjdk:17-oracle
COPY ./target/SteganographyAlgorithm-0.0.1-SNAPSHOT.jar SteganographyAlgorithm-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SteganographyAlgorithm-0.0.1-SNAPSHOT.jar"]