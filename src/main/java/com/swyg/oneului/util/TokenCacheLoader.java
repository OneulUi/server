package com.swyg.oneului.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;


@Component
public class TokenCacheLoader {
    private final Cache cache;

    public TokenCacheLoader() {
        CacheManager cacheManager = CacheManager.getInstance();
        this.cache = cacheManager.getCache("tokenCache");
    }

    private String generateKey(String loginId, String accessToken) {
        return loginId + "_" + accessToken;
    }

    public void putTokenData(String loginId, String accessToken, String refreshToken) {
        String key = generateKey(loginId, accessToken);
        Element element = new Element(key, refreshToken);
        cache.put(element);
    }

    public void deleteTokenData(String loginId, String accessToken) {
        String key = generateKey(loginId, accessToken);
        cache.remove(key);
    }

    public String getRefreshTokenFromTokenCache(String loginId, String accessToken) {
        String key = generateKey(loginId, accessToken);
        Element element = cache.get(key);
        if (element != null) {
            return (String) element.getObjectValue();
        }
        return null;
    }
}
