package org.blanc.whiteboard.repository.mongo;

import org.blanc.whiteboard.domain.mongo.VerificationToken;
import org.blanc.whiteboard.domain.mongo.VerificationTokenType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, Long> {

    VerificationToken findById(String id);

    VerificationToken findByToken(String token);

    List<VerificationToken> findByUserId(String userId);

    List<VerificationToken> findByUserIdAndTokenType(String userId, VerificationTokenType tokenType);
}
