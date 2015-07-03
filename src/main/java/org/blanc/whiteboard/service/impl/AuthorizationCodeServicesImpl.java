package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.domain.AuthorizationCode;
import org.blanc.whiteboard.repository.jpa.AuthorizationCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ffl
 * @since 18-03-2015
 */
@Service("authorizationCodeServicesImpl")
public class AuthorizationCodeServicesImpl extends RandomValueAuthorizationCodeServices{
    private static final Logger log = LoggerFactory.getLogger(AuthorizationCodeServicesImpl.class);

    @Autowired
    private AuthorizationCodeRepository authorizationCodeRepository;

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        return super.createAuthorizationCode(authentication);
    }

    @Override
    @Transactional
    public OAuth2Authentication consumeAuthorizationCode(String code)
            throws InvalidGrantException {
        return super.consumeAuthorizationCode(code);
    }


    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode(code);
        authorizationCode.setAuthentication(SerializationUtils.serialize(authentication));
        authorizationCodeRepository.save(authorizationCode);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication;
        try{
            authentication= SerializationUtils.deserialize(authorizationCodeRepository.findByCode(code).getAuthentication());
            if(authentication != null){
                authorizationCodeRepository.deleteByCode(code);
            }
        }
        catch(Exception e){
            log.info(e.getMessage());
            return null;
        }


        return authentication;
    }
}
