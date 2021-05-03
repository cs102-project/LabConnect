package me.labconnect.webapp;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
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
    
    @Bean
    @Primary
    public ObjectMapper jsonObjectIdMapper() {
        
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(ObjectId.class, new StdDelegatingSerializer(new ObjectIdConverter()));
        
        return new ObjectMapper().setSerializationInclusion(Include.NON_NULL).registerModule(simpleModule);
        
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
