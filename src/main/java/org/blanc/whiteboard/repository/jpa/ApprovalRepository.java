package org.blanc.whiteboard.repository.jpa;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.blanc.whiteboard.domain.Approval;
import org.blanc.whiteboard.domain.Client;
import org.blanc.whiteboard.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface ApprovalRepository extends BaseEntityRepository<Approval, EntityId> {
    public Approval findByClientAndUserAndScope(Client client, User user, String scope);

    @Query("Select a from org.blanc.whiteboard.domain.Approval a where a.client.clientId = :clientId and a.user.username = :username and a.scope = :scope")
    public Approval findByClientAndUserAndScope(@Param("clientId") String clientId, @Param("username") String username,
                                                @Param("scope") String scope);

    @Query("Select a from org.blanc.whiteboard.domain.Approval a where a.client.clientId = :clientId and a.user.username = :username")
    public List<Approval> findByUserNameAndClientId(@Param("username") String username,
                                                    @Param("clientId") String clientId);

    @Modifying
    @Query(value = "update org.blanc.whiteboard.domain.Approval a set a.expiresAt = :expiresAt where a.user.entityId = :userId and a.client.entityId = :clientId and a.scope = :scope")
    public int updateExpiresAtByClientIdAndUserIdAndScope(@Param("userId") EntityId userId,
                                                          @Param("clientId") EntityId clientId, @Param("scope") String scope);

    @Modifying
    @Query(value = "delete from org.blanc.whiteboard.domain.Approval a where a.user.entityId = :userId and a.client.entityId = :clientId and a.scope = :scope")
    public int deleteByClientIdAndUserIdAndScope(@Param("userId") EntityId userId, @Param("clientId") EntityId clientId,
                                                 @Param("scope") String scope);


}
