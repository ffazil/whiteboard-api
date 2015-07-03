package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author FFL
 * @since 17/03/15
 * Enhance Access Token with Tenant information.
 */
@Service("tenantTokenEnhancer")
public class TenantTokenEnhancer implements TokenEnhancer{

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String userName = authentication.isClientOnly() ? null : authentication.getName();
        User user = userRepository.findByUsername(userName);

        Map<String, Object> tenantInformation = user.getTenantInformation();

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(tenantInformation);
        return accessToken;
    }
}
