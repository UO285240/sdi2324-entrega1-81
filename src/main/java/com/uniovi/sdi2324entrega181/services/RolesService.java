package com.uniovi.sdi2324entrega181.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {

    String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
    public String[] getRoles() {
        return roles;
    }

}
