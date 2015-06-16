package org.blanc.whiteboard.repository.mongo;

import org.blanc.whiteboard.domain.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByEmailAddress(final String name);

    public User findById(final String id);
}
