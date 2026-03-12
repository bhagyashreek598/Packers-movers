package com.backend.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

/**
 * Maintains an in-memory blacklist of JWT tokens that have been logged out.
 *
 * How it works:
 *  - On logout  -> token is added here with its expiry time.
 *  - On request -> JwtFilter checks this list before trusting the token.
 *  - Auto-clean -> expired tokens purged automatically so memory doesn't grow forever.
 */
@Service
public class TokenBlacklistService {

    private static final Logger log = LoggerFactory.getLogger(TokenBlacklistService.class);

    // key = JWT string , value = token expiry Date
    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    /**
     * Blacklist a token when user logs out.
     * Stores the token along with its expiry so we can clean it up later.
     */
    public void blacklist(String token, Claims claims) {
        Date expiry = claims.getExpiration();
        blacklist.put(token, expiry);
        log.info("Token blacklisted for user: {} | expires at: {}", claims.getSubject(), expiry);
        // Auto-remove any tokens that have already expired
        purgeExpiredTokens();
    }

    /**
     * Returns true if the token has been blacklisted (user logged out).
     */
    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }

    /**
     * Removes all tokens whose expiry has already passed — they are invalid
     * by JWT standards anyway, so no need to keep them in the blacklist.
     */
    private void purgeExpiredTokens() {
        Date now = new Date();
        int removed = 0;
        for (Map.Entry<String, Date> entry : blacklist.entrySet()) {
            if (entry.getValue().before(now)) {
                blacklist.remove(entry.getKey());
                removed++;
            }
        }
        if (removed > 0) {
            log.info("Purged {} expired tokens from blacklist", removed);
        }
    }
}
