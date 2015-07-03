package org.blanc.whiteboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FFL
 * @since 11-03-15
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // For some reason we cant just "permitAll" OPTIONS requests which are needed for CORS support. Spring Security
                // will respond with an HTTP 401 nonetheless.
                // So we just put all other requests types under OAuth control and exclude OPTIONS.
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admin/**").access("#oauth2.hasScope('idem-read')")
                .antMatchers(HttpMethod.POST, "/admin/**").access("#oauth2.hasScope('idem-write')")
                .antMatchers(HttpMethod.PATCH, "/admin/**").access("#oauth2.hasScope('idem-write')")
                .antMatchers(HttpMethod.PUT, "/admin/**").access("#oauth2.hasScope('idem-write')")
                .antMatchers(HttpMethod.DELETE, "/admin/**").access("#oauth2.hasScope('idem-write')");
        http.addFilterAfter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                // Don't allow access to a resource with no token so clear
                // the security context in case it is actually an OAuth2Authentication
                if (tokenExtractor.extract(request) == null) {
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            }
        }, AbstractPreAuthenticatedProcessingFilter.class);
        http.authorizeRequests().anyRequest().authenticated();
    }
}
