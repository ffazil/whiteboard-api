package org.blanc.whiteboard.repository.jpa;

import org.blanc.whiteboard.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    public RefreshToken findByTokenId(String tokenId);
    public void deleteByTokenId(String tokenId);
}
