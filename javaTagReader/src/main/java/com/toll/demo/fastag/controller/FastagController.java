package com.toll.demo.fastag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.toll.demo.model.Fastag;
import com.toll.demo.service.FastagService;

@Controller
public class FastagController {

    private final FastagService service;

    public FastagController(FastagService service) {
        this.service = service;
    }
    
    @GetMapping("/fastag")
    public String fastagForm() {
        return "index";
    }


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/fastag")
    public String handleAction(@RequestParam String action,
                               @ModelAttribute Fastag fastag,
                               Model model) {

        if ("store".equals(action)) {
            service.save(fastag);
            model.addAttribute("message", "Saved successfully!");
        } else if ("show".equals(action)) {
            Fastag result = service.getById(fastag.getTagId());
            model.addAttribute("fastag", result);
        } else if ("report".equals(action)) {
            model.addAttribute("fastags", service.getAll());
        }

        return "index";
    }
}