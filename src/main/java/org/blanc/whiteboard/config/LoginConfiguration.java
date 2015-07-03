package org.blanc.whiteboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author FFL
 * @since 11/03/15
 */
@Configuration
@Order(-10)
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsManagerImpl")
    private UserDetailsManager userDetailsManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
                .and()
                .authorizeRequests().anyRequest().authenticated();
        // @formatter:on
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManager);
        auth.userDetailsService(userDetailsManager);
    }
}
