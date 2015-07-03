package org.blanc.whiteboard.repository.jpa;

import org.blanc.whiteboard.domain.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author FFL
 * @since 12-03-2015
 */
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
    public AccessToken findByTokenId(String tokenId);
    public void deleteByTokenId(String tokenId);
    public void deleteByRefreshTokenTokenId(String refreshTokenId);
    public AccessToken findByAuthenticationId(String authenticationId);
    @Query("select a from AccessToken a where a.user.username = :username and a.client.clientId = :clientId")
    public List<AccessToken> findByUserAndClient(@Param("username") String username, @Param("clientId") String clientId);
    @Query("select a from AccessToken a where a.client.clientId = :clientId")
    public List<AccessToken> findByClient(@Param("clientId") String clientId);
}
