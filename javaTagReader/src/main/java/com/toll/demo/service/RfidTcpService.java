package com.toll.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.toll.demo.entity.FastagDetails;
import com.toll.demo.repository.FastagDetailsRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import jakarta.annotation.PostConstruct;

@Service
public class RfidTcpService {

    private final FastagDetailsRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public RfidTcpService(FastagDetailsRepository repository, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    private static final String RFID_HOST = "127.168.0.1";
    private static final int RFID_PORT = 54321;
    private static final long RECONNECT_DELAY_MS = 2000L;

    @PostConstruct
    public void startReader() {
        new Thread(this::runLoop, "rfid-reader-thread").start();
    }

    private void runLoop() {
        while (true) {
            try (Socket socket = new Socket(RFID_HOST, RFID_PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println("✅ Connected to RFID reader at " + RFID_HOST + ":" + RFID_PORT);

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String tagId = line.trim();
                    System.out.println("📌 Tag scanned: " + tagId);

                    Optional<FastagDetails> existing = repository.findByTagId(tagId);

                    FastagDetails details;
                    if (existing.isPresent()) {
                        details = existing.get();
                        System.out.println("Fetched from database: " + details);
                    } else {
                        System.out.println("⚠️ Tag not found in database, saving new record...");
                        FastagDetails newDetails = new FastagDetails();
                        newDetails.setTagId(tagId);
                        details = repository.save(newDetails);
                    }

                    // always send to websocket (UI)
                    try {
                        messagingTemplate.convertAndSend("/topic/scans", details);
                    } catch (Exception e) {
                        System.err.println("WebSocket send failed: " + e.getMessage());
                    }
                }

                // if readLine() returned null, server closed connection — loop will attempt reconnect
                System.out.println("Connection closed by server, will attempt reconnect...");

            } catch (Exception ex) {
                System.err.println("Connection error: " + ex.getMessage());
            }

            // wait then reconnect
            try {
                Thread.sleep(RECONNECT_DELAY_MS);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
