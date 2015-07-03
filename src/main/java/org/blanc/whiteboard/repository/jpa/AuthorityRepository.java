package org.blanc.whiteboard.repository.jpa;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.blanc.whiteboard.domain.Authority;

import java.util.List;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface AuthorityRepository extends BaseEntityRepository<Authority, EntityId> {
    public Authority findByRole(String role);
    public List<Authority> findByRoleIn(List<String> roles);
}
