package hk.org.ha.kcc.common.security;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import hk.org.ha.kcc.common.logging.AlsXLoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ApiKeyUtil {
    @Value("${api.key}")
    private String apiKey;
    private static final AlsXLogger log = AlsXLoggerFactory.getXLogger(MethodHandles.lookup().lookupClass());

    public boolean validateApiKey(String authApiKey) {
        //log.debug("authApiKey: " + authApiKey);
        if (apiKey.equals(authApiKey))
            return true;
        return false;
    }
}