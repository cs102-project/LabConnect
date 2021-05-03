package me.labconnect.webapp;

import java.util.List;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Responsible of the configuration of web application
 * 
 * @author Berkan Şahin
 * @version 02.05.2021
 */
@Configuration
public class WebappConfiguration implements WebMvcConfigurer {
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        
        builder.serializerByType(ObjectId.class, new ToStringSerializer());
        
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(builder.build());
        
        converters.add(converter);
        
    }
    
    /**
     * Adds resource handlers
     * 
     * @param registry Serves static resources through Spring MVC
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Serve static content
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/static/");
        registry.addResourceHandler("/*.js").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.json").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.ico").addResourceLocations("classpath:/static/");

    }

    /**
     * Adds view controllers to configurer (for set login condition)
     * 
     * @param registry Assists the registration of automated
     *                               controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Respond to API requests with 400
        registry.addStatusController("/api/**", HttpStatus.BAD_REQUEST);

        String excludedExtensions = "css|js|png|svg|ico|java|gif|jpg|jpeg";

        // Catch all routes and forward them to React homepage. Prevent infinite loops
        // to index.html.
        registry.addViewController("/**/{regex:^(?!index\\.html|.*\\.(?:" + excludedExtensions + ")).*$}")
                .setViewName("forward:/index.html");

    }

}
