package org.blanc.whiteboard.domain;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken implements OAuth2RefreshToken, Serializable{

    @Id
    @Basic(optional = false)
    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Lob
    @Column(name="TOKEN", nullable=false)
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] token;


    @Lob
    @Column(name="AUTHENTICATION", nullable=false)
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] authentication;

    @Transient
    private String value;




    /**
     * Create a new refresh token.
     */
    public RefreshToken(String value) {
        this.value = value;
    }


    /**
     * Default constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private RefreshToken() {
        this(null);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.oauth2.common.IFOO#getValue()
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefreshToken)) {
            return false;
        }

        RefreshToken that = (RefreshToken) o;

        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
