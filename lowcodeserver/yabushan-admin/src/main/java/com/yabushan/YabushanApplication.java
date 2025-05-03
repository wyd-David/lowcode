package com.yabushan;

import lombok.extern.slf4j.Slf4j;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 *
 * @author yabushan
 */
@EnableWebSecurity
@EnableAsync
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class },scanBasePackages = {"com.yabushan","org.jeecg.modules.jmreport"})
@Slf4j
//开启WebSocket
@EnableWebSocket
@ComponentScan(basePackages = {"com.yabushan"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {cn.afterturn.easypoi.util.PoiPublicUtil.class})})
public class YabushanApplication
{
    public static void main(String[] args) throws UnknownHostException {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext application = SpringApplication.run(YabushanApplication.class, args);
        Environment env = application.getEnvironment();
        String applicationName = env.getProperty("server.name");
        String path = env.getProperty("server.servlet.context-path", "");
        String port = env.getProperty("server.port");
        String host = InetAddress.getLocalHost().getHostAddress();
        String local = String.format("Local: \t\thttp://localhost:%s%s\n\t", port, path);
        String External = String.format("External-ip: \t\thttp://%s:%s%s\n\t", host, port, path);
        String swaggerDoc = String.format("swaggerDoc: \t\thttp://%s:%s%s/swagger-ui.html\n\t", host, port, path);
        String knife4jDoc = String.format("knife4jDoc: \t\thttp://%s:%s%s/doc.html\n\t", host, port, path);

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application "+
                        applicationName+" is running! Access URLs:\n\t" +
                        local +
                        External +
                        swaggerDoc +
                        knife4jDoc +
                        "----------------------------------------------------------",
                applicationName);

        String version = SpringVersion.getVersion();

        String version1 = SpringBootVersion.getVersion();

        System.out.println(version);

        System.out.println(version1);

    }
}
