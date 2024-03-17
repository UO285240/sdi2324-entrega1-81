package com.uniovi.sdi2324entrega181.handlers;

import com.uniovi.sdi2324entrega181.services.LogService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginErrorHandler extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private LogService logService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String email = request.getParameter("username");

        logService.logLOGINERR(email);

        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }
}
