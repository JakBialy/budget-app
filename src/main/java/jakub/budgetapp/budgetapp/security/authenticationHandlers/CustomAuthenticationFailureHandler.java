package jakub.budgetapp.budgetapp.security.authenticationHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  When authentication failed status 401 (unauthorized) is send along with
     *  simple information about type of exception and actual date as a JSON
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param exception AuthenticationException
     * @throws IOException IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        Map<String, Object> data = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        data.put("exception", exception.getMessage());
        data.put("time", formattedDateTime);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
