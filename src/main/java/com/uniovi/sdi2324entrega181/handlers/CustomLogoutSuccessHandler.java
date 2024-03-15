package com.uniovi.sdi2324entrega181.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase para mostrar un mensaje personalizado al cerrar sesión
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                // Redirigir a la página de inicio de sesión con el parámetro logout
                response.sendRedirect("/login?logout=true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}