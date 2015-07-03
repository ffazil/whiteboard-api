package org.blanc.whiteboard.domain;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "ACCESS_TOKEN")
public class AccessToken implements OAuth2AccessToken, Serializable{

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "AUTHENTICATION_ID")
    private String authenticationId;

    @Column(name = "TOKEN_ID", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String tokenId;

    @Lob
    @Column(name="TOKEN", nullable=false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] token;

    @Lob
    @Column(name="AUTHENTICATION", nullable=false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] authentication;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT__ID")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER__ID")
    private User user;

    @Transient
    private String value;

    @Column(name = "EXPIRATION", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;

    @Column(name = "TOKEN_TYPE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String tokenType = BEARER_TYPE.toLowerCase();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "REFRESH_TOKEN__ID")
    private RefreshToken refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "ACCESS_TOKEN_SCOPE", joinColumns = @JoinColumn(name = "ACCESS_TOKEN__ID"))
    private Set<String> scope = new HashSet<String>(0);

    @ElementCollection
    @MapKeyColumn(name = "MESSAGE")
    @Column(name = "INFORMATION")
    @CollectionTable(name = "ACCESS_TOKEN_ADDITIONAL_INFORMATION", joinColumns = @JoinColumn(name = "ACCESS_TOKEN__ID"))
    private Map<String, String> additionalInformation = new HashMap<String, String>(0);

    /**
     * Create an access token from the value provided.
     */
    public AccessToken(String value) {
        this.value = value;
    }

    /**
     * Private constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private AccessToken() {
        this((String) null);
    }

    /**
     * Copy constructor for access token.
     *
     * @param accessToken
     */
    public AccessToken(OAuth2AccessToken accessToken) {
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
        setRefreshToken(accessToken.getRefreshToken());
        setExpiration(accessToken.getExpiration());
        setScope(accessToken.getScope());
        setTokenType(accessToken.getTokenType());
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * The token value.
     *
     * @return The token value.
     */
    public String getValue() {
        return value;
    }

    public int getExpiresIn() {
        return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                .intValue() : 0;
    }

    protected void setExpiresIn(int delta) {
        setExpiration(new Date(System.currentTimeMillis() + delta));
    }

    /**
     * The instant the token expires.
     *
     * @return The instant the token expires.
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * The instant the token expires.
     *
     * @param expiration The instant the token expires.
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * Convenience method for checking expiration
     *
     * @return true if the expiration is before the current time
     */
    public boolean isExpired() {
        return expiration != null && expiration.before(new Date());
    }

    /**
     * The token type, as introduced in draft 11 of the OAuth 2 spec. The spec doesn't define (yet) that the valid token
     * types are, but says it's required so the default will just be "undefined".
     *
     * @return The token type, as introduced in draft 11 of the OAuth 2 spec.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * The token type, as introduced in draft 11 of the OAuth 2 spec.
     *
     * @param tokenType The token type, as introduced in draft 11 of the OAuth 2 spec.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * The refresh token associated with the access token, if any.
     *
     * @return The refresh token associated with the access token, if any.
     */
    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    /**
     * The refresh token associated with the access token, if any.
     *
     * @param refreshToken The refresh token associated with the access token, if any.
     */
    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = (RefreshToken) refreshToken;
    }

    /**
     * The scope of the token.
     *
     * @return The scope of the token.
     */
    public Set<String> getScope() {
        return scope;
    }

    /**
     * The scope of the token.
     *
     * @param scope The scope of the token.
     */
    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
        AccessToken token = new AccessToken(tokenParams.get(ACCESS_TOKEN));

        if (tokenParams.containsKey(EXPIRES_IN)) {
            long expiration = 0;
            try {
                expiration = Long.parseLong(String.valueOf(tokenParams.get(EXPIRES_IN)));
            }
            catch (NumberFormatException e) {
                // fall through...
            }
            token.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000L)));
        }

        if (tokenParams.containsKey(REFRESH_TOKEN)) {
            String refresh = tokenParams.get(REFRESH_TOKEN);
            RefreshToken refreshToken = new RefreshToken(refresh);
            token.setRefreshToken(refreshToken);
        }

        if (tokenParams.containsKey(SCOPE)) {
            Set<String> scope = new TreeSet<>();
            for (StringTokenizer tokenizer = new StringTokenizer(tokenParams.get(SCOPE), " ,"); tokenizer
                    .hasMoreTokens();) {
                scope.add(tokenizer.nextToken());
            }
            token.setScope(scope);
        }

        if (tokenParams.containsKey(TOKEN_TYPE)) {
            token.setTokenType(tokenParams.get(TOKEN_TYPE));
        }

        return token;
    }

    /**
     * Additional information that token granters would like to add to the token, e.g. to support new token types.
     *
     * @return the additional information (default empty)
     */
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

    /**
     * Additional information that token granters would like to add to the token, e.g. to support new token types. If
     * the values in the map are primitive then remote communication is going to always work. It should also be safe to
     * use maps (nested if desired), or something that is explicitly serializable by Jackson.
     *
     * @param additionalInformation the additional information to set
     */
    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = new LinkedHashMap<String, String>(additionalInformation);
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }


}
