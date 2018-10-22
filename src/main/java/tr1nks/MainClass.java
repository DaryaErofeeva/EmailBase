package tr1nks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({
        "tr1nks.component",
        "tr1nks.config",
        "tr1nks.controller",
        "tr1nks.service",
})
public class MainClass extends SpringBootServletInitializer {
    public static final Class[] classes = {MainClass.class,};
    public static ApplicationContext ac;

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "true");//todo remove in prod
        System.setProperty("spring.thymeleaf.cache", "false");
        ac = SpringApplication.run(classes, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(classes);
    }
}
