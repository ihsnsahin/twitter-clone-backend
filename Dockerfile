Dockerfile
# 1. AŞAMA: Projeyi Derleme (Build Stage)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Bağımlılıkları önbelleğe almak için önce pom.xml kopyalanır
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kaynak kodlar kopyalanır ve proje derlenir (testler atlanır)
COPY src ./src
RUN mvn clean package -DskipTests

# 2. AŞAMA: Uygulamayı Çalıştırma (Runtime Stage)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# İlk aşamada üretilen .jar dosyasını kopyalıyoruz
COPY --from=build /app/target/*.jar app.jar

# Uygulamanın çalışacağı port
EXPOSE 3000

# Spring Boot uygulamasını başlatma komutu
ENTRYPOINT ["java", "-jar", "app.jar"]