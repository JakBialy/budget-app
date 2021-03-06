package jakub.budgetapp.budgetapp.security.authenticationHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {


    /**
     *  When logout succeed status 200 (ok) is send
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication Authentication
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setStatus(HttpStatus.OK.value());
    }
}
