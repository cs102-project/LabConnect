package me.labconnect.webapp;

import org.springframework.context.annotation.Configuration;
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

    /**
     * Adds resource handlers
     * 
     * @param ResourceHandlerRegistry Serves static resources through Spring MVC
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
     * @param ViewControllerRegistry Assists the registration of automated
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
