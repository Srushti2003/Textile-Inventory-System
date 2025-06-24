//package com.example.textile_inventory_mgmt.textile.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PurchaseOrder {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String invoiceNo;
//
//    private LocalDate invoiceDate;
//
//    private String customerName;
//    private String state;
//
//    private Double basicAmountTotal;
//    private Double difference;
//    private Double discPercent;
//    private Double discount;
//    private Double brokPercent;
//    private Double brokerage;
//
//    private String brokerName;
//
//    private Double taxableAmount;
//    private Double gstPercent;
//    private Double cgst;
//    private Double sgst;
//    private Double igst;
//    private Double totalGst;
//
//    private Double totalAmount;
//    private Double totalQty;
//
//    private Boolean paymentReceived;
//
//    private String remarks;
//
//    private LocalDate dueDate;
//
//    private Double amountReceived;
//    private String chequeNos;
//    private LocalDate chequeDate;
//
//    private Double amountDue;
//}