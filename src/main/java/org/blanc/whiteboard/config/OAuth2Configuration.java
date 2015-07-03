package org.blanc.whiteboard.config;

import org.blanc.whiteboard.service.impl.JpaTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * @author FFL
 * @since 11-03-2015
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("clientDetailsServiceImpl")
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("tenantTokenEnhancer")
    private TokenEnhancer tenantTokenEnhancer;

    @Autowired
    private JpaTokenStore jpaTokenStore;

    @Autowired
    @Qualifier("approvalStoreImpl")
    private ApprovalStore approvalStore;

    @Autowired
    @Qualifier("authorizationCodeServicesImpl")
    private AuthorizationCodeServices authorizationCodeServices;



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                .getKeyPair("test");
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain(){
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(com.google.common.collect.Lists.newArrayList(tenantTokenEnhancer, jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public DefaultTokenServices tokenServices() throws Exception {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(jpaTokenStore);
        return tokenServices;
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
        .approvalStore(approvalStore)
                .authorizationCodeServices(authorizationCodeServices)
        .tokenServices(tokenServices());

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                "isAuthenticated()");
    }


}
