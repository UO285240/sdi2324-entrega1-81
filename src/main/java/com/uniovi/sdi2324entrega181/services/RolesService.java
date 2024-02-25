package com.uniovi.sdi2324entrega181.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {

    String[] roles = {"USUARIO_ESTANDAR", "USUARIO_ADMIN"};
    public String[] getRoles() {
        return roles;
    }

}
