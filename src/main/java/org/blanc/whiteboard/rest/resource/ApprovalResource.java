package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;
import org.springframework.security.oauth2.provider.approval.Approval;

import java.util.Date;

public class ApprovalResource extends BaseResource {
    private ClientResource client;
    private UserResource user;
    private String scope;
    private Approval.ApprovalStatus approvalStatus;
    private Date expiresAt;
    private Date lastUpdatedAt;

    public ClientResource getClient() {
        return client;
    }

    public void setClient(ClientResource client) {
        this.client = client;
    }

    public UserResource getUser() {
        return user;
    }

    public void setUser(UserResource user) {
        this.user = user;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Approval.ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Approval.ApprovalStatus approvalStatus) {
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
