package hk.org.ha.kcc.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyUtil {
    @Value("5f2f3d82-94cc-4a0a-b731-b61d8a2631d2")
    private String apiKey;

    public boolean validateApiKey(String authApiKey) {
        if (apiKey.equals(authApiKey))
            return true;
        return false;
    }
}
