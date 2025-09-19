/*
 * package com.toll.demo.service; import java.io.BufferedReader; import
 * java.io.InputStreamReader; import java.net.Socket; import
 * java.time.LocalDateTime; import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.messaging.simp.SimpMessagingTemplate; import
 * org.springframework.stereotype.Service;
 * 
 * import com.toll.demo.entity.ScanRecord; import com.toll.demo.entity.Tag;
 * import com.toll.demo.repository.ScanRecordRepository; import
 * com.toll.demo.repository.TagRepository;
 * 
 * import jakarta.annotation.PostConstruct;
 * 
 * 
 * 
 * 
 * @Service public class ReaderTcpService {
 * 
 * @Value("${reader.ip:192.168.1.117}") private String readerIp;
 * 
 * @Value("${reader.port:9090}") private int readerPort;
 * 
 * private final TagRepository tagRepo; private final ScanRecordRepository
 * scanRepo; private final SimpMessagingTemplate messagingTemplate;
 * 
 * public ReaderTcpService(TagRepository tagRepo, ScanRecordRepository scanRepo,
 * SimpMessagingTemplate messagingTemplate) { this.tagRepo = tagRepo;
 * this.scanRepo = scanRepo; this.messagingTemplate = messagingTemplate; }
 * 
 * @PostConstruct public void start() { new Thread(this::listen).start(); }
 * 
 * private void listen() { try (Socket socket = new Socket(readerIp,
 * readerPort); BufferedReader br = new BufferedReader(new
 * InputStreamReader(socket.getInputStream()))) {
 * System.out.println("✅ Connected to reader " + readerIp + ":" + readerPort);
 * 
 * String line; while ((line = br.readLine()) != null) { String uid =
 * parseUid(line); if (uid != null) handleUid(uid); } } catch (Exception e) {
 * e.printStackTrace(); } }
 * 
 * private String parseUid(String line) { if (line == null || line.isEmpty())
 * return null; if (line.startsWith("UID:")) { return
 * line.substring(4).trim().toUpperCase(); } return line.trim().toUpperCase(); }
 * 
 * private void handleUid(String uid) { System.out.println("📌 Tag read: " +
 * uid);
 * 
 * Tag tag = tagRepo.findByUid(uid).orElse(null); ScanRecord scan = new
 * ScanRecord(); scan.setUid(uid); scan.setTag(tag);
 * scan.setScannedAt(LocalDateTime.now()); scanRepo.save(scan);
 * 
 * Map<String, Object> payload = Map.of( "uid", uid, "name", tag != null ?
 * tag.getName() : null, "registrationNumber", tag != null ?
 * tag.getRegistrationNumber() : null, "mobileNumber", tag != null ?
 * tag.getMobileNumber() : null, "unitNumber", tag != null ? tag.getUnitNumber()
 * : null, "other", tag != null ? tag.getOther() : null, "scannedAt",
 * scan.getScannedAt().toString() );
 * 
 * messagingTemplate.convertAndSend("/topic/scans", payload); } }
 * 
 */