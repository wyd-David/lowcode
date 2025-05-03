用maven把aspose的jar包安装到本地仓库，Dfile改为jar包对应路径

mvn install:install-file -Dfile=D:\bingo\aspose-cells-8.5.2.jar -DgroupId=com.external -DartifactId=aspose-cell -Dversion=8.5.2 -Dpackaging=jar

mvn install:install-file -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=15.8.0 -Dpackaging=jar -Dfile=D:\bingo\aspose-words-15.8.0-jdk16.jar

mvn install:install-file -DgroupId=com.aspose -DartifactId=aspose-slides -Dversion=15.9.0 -Dpackaging=jar -Dfile=D:\bingo\aspose.slides-15.9.0.jar