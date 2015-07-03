package org.blanc.whiteboard.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;

import javax.persistence.*;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "AUTHORIZATION_CODE")
public class AuthorizationCode extends BaseEntity {

    @Column(name = "CODE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @Lob
    @Column(name = "AUTHENTICATION")
    @Basic(fetch = FetchType.EAGER)
    private byte[] authentication;

    public AuthorizationCode(){

    }

    public AuthorizationCode(String code, byte[] authentication) {
        this.code = code;
        this.authentication = authentication;
    }

    public String getCode() {
        return code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}
