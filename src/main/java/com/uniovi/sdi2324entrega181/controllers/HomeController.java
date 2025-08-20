package com.uniovi.sdi2324entrega181.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
