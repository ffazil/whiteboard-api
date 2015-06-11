package org.blanc.whiteboard.config;

import com.google.common.collect.Lists;
import org.blanc.whiteboard.service.impl.MongoTokenStore;
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
 * @since 11-06-2015
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private MongoTokenStore mongoTokenStore;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		//Allow access to test clients
		clients.inMemory()
				.withClient("353b302c44574f565045687e534e7d6a")
				.secret("286924697e615a672a646a493545646c")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write")
				.authorities("ROLE_TEST");

		//Web Application clients
		clients.inMemory()
				.withClient("7b5a38705d7b3562655925406a652e32")
				.secret("655f523128212d6e70634446224c2a48")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write")
				.authorities("ROLE_WEB");

		//iOS clients
		clients.inMemory()
				.withClient("5e572e694e4d61763b567059273a4d3d")
				.secret("316457735c4055642744596b302e2151")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write")
				.authorities("ROLE_IOS");

		//Android clients
		clients.inMemory()
				.withClient("302a7d556175264c7e5b326827497349")
				.secret("4770414c283a20347c7b553650425773")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write")
				.authorities("ROLE_ANDROID");
	}


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
        tokenEnhancerChain.setTokenEnhancers(Lists.<TokenEnhancer>newArrayList(jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }


    @Bean
    public DefaultTokenServices tokenServices() throws Exception {
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(mongoTokenStore);
        return tokenServices;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
        .tokenServices(tokenServices());

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                "isAuthenticated()");
    }


}
