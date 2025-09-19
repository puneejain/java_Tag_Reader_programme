package com.toll.demo.fastag.controller;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.toll.demo.entity.FastagDetails;

@RestController
@RequestMapping("/app")
public class TestScanController {

    private final SimpMessagingTemplate messagingTemplate;

    public TestScanController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 🔴 Test endpoint: http://localhost:8080/app/test-scan
    @PostMapping("/test-scan")
    public String sendTestScan(@RequestBody FastagDetails details) {
        messagingTemplate.convertAndSend("/topic/scans", details);
        return "Dummy scan pushed for TagId=" + details.getTagId();
    }
}
