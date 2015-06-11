package org.blanc.whiteboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ffl on 11-06-2015.
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.blanc.whiteboard.config")
public class WhiteboardAPIStarter {
	public static void main(String[] args) {
		SpringApplication.run(WhiteboardAPIStarter.class, args);
	}
}
