# Dockerfile 
# by Safwaan Taher

# Get the source image
FROM amazoncorretto:17-alpine-jdk
# Set working directory and create directories needed
WORKDIR /app
# Copy files, expose port and run SpringBoot app
COPY ./target/thinkersol-backend-API.jar /app
EXPOSE 8080
CMD ["java","-jar","thinkersol-backend-API.jar"]
