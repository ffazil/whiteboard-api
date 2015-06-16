package org.blanc.whiteboard.config;

import org.blanc.whiteboard.rest.jersey.controller.SampleResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ffl on 11-06-2015.
 */
//@Configuration
@ComponentScan(basePackages = {"org.blanc.whiteboard.jersey"})
public class JerseyConfiguration extends ResourceConfig{
	public JerseyConfiguration(){
		register(SampleResource.class);
	}
}
