# 도커 이미지 지정
FROM openjdk:21

WORKDIR /app/logit

# 빌드된 파일의 위치를 argument로 지정
ARG JAR_PATH=../build/libs
ARG RESOURCE_PATH=../build/resources/main/

# 위의 경로의 파일을 이미지 내부의 app.jar로 복사
COPY ${JAR_PATH}/*.jar /app/logit/LogIT.jar

# 컨테이너 시작시 명령어. 즉 해당 jar파일을 실행하겠다는 것
ENTRYPOINT [ "java", "-jar",  "LogIT.jar" ]