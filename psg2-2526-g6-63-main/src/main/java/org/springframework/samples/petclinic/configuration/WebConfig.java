package org.springframework.samples.petclinic.configuration;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	GenericIdToEntityConverter idToEntityConverter;
	
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToEntityConverter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000",
                                        "https://psg2--2526-g6-63.ew.r.appspot.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseDir = uploadDir;
        if (!Path.of(baseDir).isAbsolute()) {
            baseDir = System.getProperty("user.dir") + "/" + baseDir;
        }
        String location = "file:" + Path.of(baseDir).normalize() + "/";
        String mapping = uploadUrl.endsWith("/") ? uploadUrl + "**" : uploadUrl + "/**";
        registry.addResourceHandler(mapping).addResourceLocations(location);
    }

    @Value("${petclinic.upload.dir:uploads}")
    private String uploadDir;

    @Value("${petclinic.upload.url:/uploads}")
    private String uploadUrl;
    
}