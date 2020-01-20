package eventorganiser.dir.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException  {

        /* Set the username of the user as a session attribute */
        String username = ((User) authentication.getPrincipal()).getUsername();
        request.getSession().setAttribute("SESSION_USERNAME", username);

        // Set the UserId in the sessions
        String uri = "http://localhost:8080/api/get/user?username=" + username;
        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(uri, String.class);
        request.getSession().setAttribute("SESSION_USERID", result);

        //Software Version number stored in session.
        //UPDATE WITH SOFTWARE VERSIONS
        String buildNumber = "1.0.0 RELEASE";
        request.getSession().setAttribute("BUILD", buildNumber);

        //Send the user to /Calendar page
        new DefaultRedirectStrategy().sendRedirect(request, response, "/feedPage");
    }

}

