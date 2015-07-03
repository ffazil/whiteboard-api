package org.blanc.whiteboard.repository.jpa;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.blanc.whiteboard.domain.Client;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface ClientRepository extends BaseEntityRepository<Client,EntityId> {
    public Client findByClientId(String clientId);
    public void deleteByClientId(String clientId);
}