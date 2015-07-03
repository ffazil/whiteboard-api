package org.blanc.whiteboard.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingWebBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author FFL
 * @since 11-03-2015
 */
@Configuration
@Conditional(value = NonExistingWebBeans.class)
@ComponentScan(basePackages = {"org.blanc.whiteboard.web", "org.blanc.whiteboard.rest"})
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }
}
