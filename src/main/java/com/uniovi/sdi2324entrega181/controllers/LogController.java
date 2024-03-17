package com.uniovi.sdi2324entrega181.controllers;

import com.uniovi.sdi2324entrega181.entities.Log;
import com.uniovi.sdi2324entrega181.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/log/log")
    public String showAll(Model model, Pageable pageable) {
        Page<Log> logs = logService.getLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


    @RequestMapping(value = "/log/pet")
    public String showPET(Model model, Pageable pageable) {
        Page<Log> logs = logService.getPETLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


    @RequestMapping(value = "/log/alta")
    public String showALTA(Model model, Pageable pageable) {
        Page<Log> logs = logService.getALTALogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


    @RequestMapping(value = "/log/login-ex")
    public String showLOGINEX(Model model, Pageable pageable) {
        Page<Log> logs = logService.getLOGINEXLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


    @RequestMapping(value = "/log/login-err")
    public String showLOGINERR(Model model, Pageable pageable) {
        Page<Log> logs = logService.getLOGINERRLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


    @RequestMapping(value = "/log/logout")
    public String showLOGOUT(Model model, Pageable pageable) {
        Page<Log> logs = logService.getLOGOUTLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }
    @RequestMapping(value = "/log/rem")
    public String removeAll(Model model, Pageable pageable) {
        logService.removeAll();
        Page<Log> logs = logService.getLOGOUTLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "/log/log";
    }


}

