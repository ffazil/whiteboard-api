package org.blanc.whiteboard.repository.jpa;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.blanc.whiteboard.domain.AuthorizationCode;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface AuthorizationCodeRepository extends BaseEntityRepository<AuthorizationCode, EntityId> {
    public AuthorizationCode findByCode(String code);
    public void deleteByCode(String code);
}
