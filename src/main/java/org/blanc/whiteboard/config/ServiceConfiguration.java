package org.blanc.whiteboard.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ffl
 * @since 16-03-2015
 */
@Configuration
@ComponentScan(basePackages = "org.blanc.whiteboard.service.impl", scopedProxy = ScopedProxyMode.INTERFACES)
@EnableTransactionManagement(proxyTargetClass = true)
public class ServiceConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new StandardPasswordEncoder();
	}
}


