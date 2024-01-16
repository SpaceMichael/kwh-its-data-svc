package hk.org.ha.kcc.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyUtil {
    @Value("${api.key}")
    private String apiKey;

    public boolean validateApiKey(String authApiKey) {
        if (apiKey.equals(authApiKey))
            return true;
        return false;
    }
}
