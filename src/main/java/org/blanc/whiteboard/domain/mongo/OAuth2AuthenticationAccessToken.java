package org.blanc.whiteboard.domain.mongo;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.*;

public class OAuth2AuthenticationAccessToken extends BaseEntity implements OAuth2AccessToken{


    private String authenticationId;
	private String tokenId;

    private String userName;
    private String clientId;

	private byte[] token;
	private byte[] authentication;


	private String value;
	private Date expiration;
	private String tokenType = BEARER_TYPE.toLowerCase();
	private Set<String> scope = new HashSet<String>(0);
	private OAuth2AuthenticationRefreshToken refreshToken;
	private Map<String, String> additionalInformation = new HashMap<String, String>(0);

	public OAuth2AuthenticationAccessToken(String value){

	}

	public OAuth2AuthenticationAccessToken(OAuth2AccessToken accessToken){
		this(accessToken.getValue());
		Map<String, String> additionalInformationProxy = new HashMap<String, String>();
		Map<String, Object> proxy = accessToken.getAdditionalInformation();
		if(proxy != null) {
			Set<Map.Entry<String, Object>> entries = proxy.entrySet();
			if(entries != null) {
				Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Object> entry = iterator.next();
					additionalInformationProxy.put(entry.getKey(), (String)entry.getValue());
				}
			}
		}
		setAdditionalInformation(additionalInformationProxy);
		setRefreshToken((OAuth2AuthenticationRefreshToken) accessToken.getRefreshToken());
		setExpiration(accessToken.getExpiration());
		setScope(accessToken.getScope());
		setTokenType(accessToken.getTokenType());
	}



    public Map<String, Object> getAdditionalInformation() {
		Map<String, Object> additionalInformationProxy = new HashMap<String, Object>();
		if(additionalInformation != null) {
			Set<Map.Entry<String, String>> entries = additionalInformation.entrySet();
			if(entries != null) {
				Iterator<Map.Entry<String, String>> iterator = entries.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, String> entry = iterator.next();
					additionalInformationProxy.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return additionalInformationProxy;
    }

    public Set<String> getScope() {
        return scope;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public boolean isExpired() {
		return expiration != null && expiration.before(new Date());
    }

    public Date getExpiration() {
		return expiration;
    }

    public int getExpiresIn() {
		return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
				.intValue() : 0;
    }

    public String getValue() {
        return value;
    }

	public void setAdditionalInformation(Map<String, String> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public void setRefreshToken(OAuth2AuthenticationRefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public byte[] getAuthentication() {
		return authentication;
	}
}
