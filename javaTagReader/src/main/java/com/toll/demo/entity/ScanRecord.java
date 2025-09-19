//package com.toll.demo.entity;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@Entity
//@ToString
//@Table(name = "scan_records")
//public class ScanRecord {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    
//    private String uid;
//
//    @ManyToOne
//    @JoinColumn(name = "tag_id")
//    private Tag tag;
//
//    private LocalDateTime scannedAt;
//}
