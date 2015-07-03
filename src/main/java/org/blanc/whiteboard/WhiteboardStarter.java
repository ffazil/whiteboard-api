package org.blanc.whiteboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FFL
 * @Since 11-03-2015
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.blanc.whiteboard.config")
public class WhiteboardStarter {
    public static void main(String[] args) {
        SpringApplication.run(WhiteboardStarter.class, args);
    }
}
