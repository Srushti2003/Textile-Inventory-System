package com.example.textile_inventory_mgmt.textile.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sales_invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Invoice_No")
    private String invoiceNo;

    @Column(name = "Invoice_Date")
    private LocalDate invoiceDate;

    @Column(name = "Cust_id")  // ensure the column matches your DB table
    private String custId;

    @Column(name = "Customer_Name")
    private String customerName;

    @Column(name = "State")
    private String state;

    @Column(name = "Basic_Amount_Total")
    private Double basicAmountTotal;

    @Column(name = "Difference")
    private Double difference;

    @Column(name = "Disc_Percent")
    private Double discPercent;

    @Column(name = "Discount")
    private Double discount;

    @Column(name = "Brok_Percent")
    private Double brokPercent;

    @Column(name = "Brokerage")
    private Double brokerage;

    @Column(name = "Broker_Name")
    private String brokerName;

    @Column(name = "Taxable_Amount")
    private Double taxableAmount;

    @Column(name = "GST_Percent")
    private Double gstPercent;

    @Column(name = "CGST")
    private Double cgst;

    @Column(name = "SGST")
    private Double sgst;

    @Column(name = "IGST")
    private Double igst;

    @Column(name = "Total_GST")
    private Double totalGst;

    @Column(name = "Total_Amount")
    private Double totalAmount;

    @Column(name = "Total_Qty")
    private Double totalQty;

    @Column(name = "Payment_Received")
    private Boolean paymentReceived;

    @Column(name = "Remarks")
    private String remarks;

    @Column(name = "Due_Date")
    private LocalDate dueDate;

    @Column(name = "Amount_Received")
    private Double amountReceived;

    @Column(name = "Cheque_Nos")
    private String chequeNos;

    @Column(name = "Cheque_Date")
    private LocalDate chequeDate;

    @Column(name = "Amount_Due")
    private Double amountDue;

    @OneToMany(mappedBy = "salesInvoice", cascade = CascadeType.ALL)
    private List<SalesTrans> items;
}


