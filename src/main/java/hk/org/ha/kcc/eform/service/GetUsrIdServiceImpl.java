package hk.org.ha.kcc.eform.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class GetUsrIdServiceImpl implements GetUsrIdService {
    @Override
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // get token -> claim -> "preferred_username"
        return authentication.getName();
    }
}