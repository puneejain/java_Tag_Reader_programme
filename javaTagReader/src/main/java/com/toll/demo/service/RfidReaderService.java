package com.toll.demo.service;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.clou.uhf.G3Lib.CLReader;
import com.clou.uhf.G3Lib.ClouInterface.IAsynchronousMessage;
import com.clou.uhf.G3Lib.Protocol.Tag_Model;
import com.toll.demo.entity.FastagDetails;
import com.toll.demo.repository.FastagDetailsRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RfidReaderService implements IAsynchronousMessage {

    private final FastagDetailsRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final String READER_IP   = "192.168.0.100";
    private static final int READER_PORT    = 6000;

    public RfidReaderService(FastagDetailsRepository repository,
                             SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            String connStr = READER_IP + ":" + READER_PORT;
            boolean connected = CLReader.CreateTcpConn(connStr, this);

            if (connected) {
                System.out.println("✅ Connected to RFID reader at " + connStr);

                // Config set karo (string params ke saath)
                CLReader.SetPower(connStr, "30");   // 30 dBm
                CLReader.SetReaderGPOState(connStr, "0");    // continuous mode

                // Start EPC inventory
                
                CLReader.Get6B(connStr, connStr);
            } else {
                System.err.println("❌ Failed to connect to RFID reader!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Correct callback
    @Override
    public void OutPutTags(Tag_Model tagModel) {
        String tagId = tagModel._EPC; // ya tagModel._Epc (check decompile)
        System.out.println("📌 EPC Tag scanned: " + tagId);

        Optional<FastagDetails> existing = repository.findByTagId(tagId);
        FastagDetails details;
        if (existing.isPresent()) {
            details = existing.get();
        } else {
            FastagDetails newDetails = new FastagDetails();
            newDetails.setTagId(tagId);
            details = repository.save(newDetails);
        }

        try {
            messagingTemplate.convertAndSend("/topic/scans", details);
        } catch (Exception e) {
            System.err.println("WebSocket send failed: " + e.getMessage());
        }
    }

    @Override
    public void OutPutTagsOver() {
        System.out.println("✅ Inventory cycle completed");
    }

    @Override
    public void GPIControlMsg(int gpiIndex, int gpiState, int gpiStopTime) {
        System.out.println("⚡ GPI event received: " + gpiIndex + " = " + gpiState);
    }

    public void OutPutScanData(String connStr, byte[] data) {
        System.out.println("📡 Raw scan data length: " + data.length + " from " + connStr);
    }

    @Override
    public void PortClosing(String connStr) {
        System.out.println("⚠️ Port closing: " + connStr);
    }

    @Override
    public void PortConnecting(String connStr) {
        System.out.println("🔄 Port connecting: " + connStr);
    }

    @Override
    public void WriteDebugMsg(String msg) {
        System.out.println("🐞 Debug: " + msg);
    }

    @Override
    public void WriteLog(String msg) {
        System.out.println("📝 Log: " + msg);
    }
}