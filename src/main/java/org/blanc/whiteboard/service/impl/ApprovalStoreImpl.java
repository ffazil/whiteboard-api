package org.blanc.whiteboard.service.impl;

import com.tracebucket.tron.ddd.domain.EntityId;
import org.blanc.whiteboard.repository.jpa.ApprovalRepository;
import org.blanc.whiteboard.repository.jpa.ClientRepository;
import org.blanc.whiteboard.repository.jpa.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author FFL
 * @since 18-03-2015
 */
@Service("approvalStoreImpl")
@Transactional
public class ApprovalStoreImpl implements ApprovalStore{
    private static final Logger logger = LoggerFactory.getLogger(ApprovalStoreImpl.class);

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    private boolean handleRevocationsAsExpiry = false;

    @Override
    public boolean addApprovals(Collection<Approval> approvals) {


        logger.debug(String.format("Adding approvals: [%s]", approvals));
        boolean success = true;
        for (Approval approval : approvals) {
            if (!refreshApproval(approval)) {
               success = false;
            }
        }
        return success;
    }

    private Boolean refreshApproval(Approval approval){

        Boolean refreshed = false;
        org.blanc.whiteboard.domain.Approval a = approvalRepository.findByClientAndUserAndScope(approval.getClientId(), approval.getUserId(), approval.getScope());
        if(a != null){
            a.setExpiresAt(approval.getExpiresAt());
            a.setApprovalStatus(approval.getStatus());
            a.setLastUpdatedAt(approval.getLastUpdatedAt());
        }
        else{
            a = new org.blanc.whiteboard.domain.Approval();
            a.setUser(userRepository.findByUsername(approval.getUserId()));
            a.setClient(clientRepository.findByClientId(approval.getClientId()));
            a.setExpiresAt(approval.getExpiresAt());
            a.setLastUpdatedAt(approval.getLastUpdatedAt());
            a.setScope(approval.getScope());
            a.setApprovalStatus(approval.getStatus());
        }


        a = approvalRepository.save(a);
        if(a != null)
            refreshed = true;
        return refreshed;
    }

    public void setHandleRevocationsAsExpiry(boolean handleRevocationsAsExpiry) {
        this.handleRevocationsAsExpiry = handleRevocationsAsExpiry;
    }


    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        logger.debug(String.format("Revoking approvals: [%s]", approvals));
        boolean success = true;
        for (final Approval approval : approvals) {
            if (handleRevocationsAsExpiry) {
                int refreshed = approvalRepository.updateExpiresAtByClientIdAndUserIdAndScope(new EntityId(approval.getUserId()), new EntityId(approval.getClientId()), approval.getScope());
                if (refreshed != 1) {
                    success = false;
                }
            }
            else {
                int refreshed = approvalRepository.deleteByClientIdAndUserIdAndScope(new EntityId(approval.getUserId()), new EntityId(approval.getClientId()), approval.getScope());
                if (refreshed != 1) {
                    success = false;
                }
            }
        }
        return success;
    }

    @Override
    public List<Approval> getApprovals(String userName, String clientId) {
        List<org.blanc.whiteboard.domain.Approval> approvals = approvalRepository.findByUserNameAndClientId(userName, clientId);
        List<Approval> approvals1 = null;
        if(approvals != null && approvals.size() > 0) {
            approvals1 = new ArrayList<Approval>();
            for(org.blanc.whiteboard.domain.Approval approval : approvals) {
               Approval approval1 = new Approval(approval.getUser().getUsername(),
                       approval.getClient().getClientId(),
                       approval.getScope(), approval.getExpiresAt(),
                       approval.getApprovalStatus(), approval.getLastUpdatedAt());
                approvals1.add(approval1);
            }
            return approvals1;
        }
        return Collections.emptyList();
    }
}
