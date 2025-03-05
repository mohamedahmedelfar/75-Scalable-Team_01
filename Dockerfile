FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app
COPY ../AdvancedLab/Mini-Project1-Base-main /app
EXPOSE 8080
CMD ["java","-jar","/app/target/mini1.jar"]