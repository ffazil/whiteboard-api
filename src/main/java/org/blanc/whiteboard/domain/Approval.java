package org.blanc.whiteboard.domain;

import com.tracebucket.tron.ddd.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author FFL
 * @since 12-03-2015
 */
@Entity
@Table(name = "APPROVAL")
public class Approval extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENT__ID")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER__ID")
    private User user;

    @Column(name = "SCOPE")
    @Basic(fetch = FetchType.EAGER)
    private String scope;

    @Column(name = "APPROVAL_STATUS", nullable = false, columnDefinition = "ENUM('APPROVED','DENIED') default 'DENIED'")
    @Enumerated(EnumType.STRING)
    private org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus;

    @Column(name = "EXPIRES_AT")
    @Basic(fetch = FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Column(name = "LAST_UPDATED_AT")
    @Basic(fetch = FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedAt;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
