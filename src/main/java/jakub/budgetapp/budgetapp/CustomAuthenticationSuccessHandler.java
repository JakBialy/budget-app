package jakub.budgetapp.budgetapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("username", SecurityContextHolder.getContext().getAuthentication().getName());

        int authorityCounter = 1;

        for (GrantedAuthority grantedAuthority: authentication.getAuthorities()) {
            data.put("authority_" + authorityCounter, grantedAuthority.getAuthority());
            authorityCounter++;
        }

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
        response.setStatus(HttpStatus.OK.value());
    }
}
