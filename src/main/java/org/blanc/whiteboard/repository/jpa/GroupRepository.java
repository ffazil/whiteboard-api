package org.blanc.whiteboard.repository.jpa;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.ddd.jpa.BaseEntityRepository;
import org.blanc.whiteboard.domain.Group;

/**
 * @author ssm
 * @since 17-Mar-15
 */
public interface GroupRepository extends BaseEntityRepository<Group, EntityId> {
    public Group findByName(String name);
    public void deleteByName(String name);
}