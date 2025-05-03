FROM  dockertest.xxx.com.cn/library/java:openjdk8-latest
MAINTAINER yabushan
VOLUME /tmp
EXPOSE 10084
ADD ../../yabushan-admin/target/yabushan-admin.jar /app/
ENTRYPOINT ["java","-jar","/app/yabushan-admin.jar"]
RUN echo "test build successfully !"
