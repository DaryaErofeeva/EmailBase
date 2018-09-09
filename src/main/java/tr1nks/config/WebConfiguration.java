package tr1nks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/theme/**").addResourceLocations("classpath:/static/theme/");
        registry.addResourceHandler("css/**").addResourceLocations("classpath:/static/theme/css/");
        registry.addResourceHandler("fonts/**").addResourceLocations("classpath:/static/theme/fonts/");
        registry.addResourceHandler("js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("includes/**").addResourceLocations("classpath:/templates/includes/");
    }
}
