package com.example.textile_inventory_mgmt.textile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sales_trans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesTrans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Invoice_No")
    private String invoiceNo;

    @Column(name = "Item_Code")
    private String itemCode;

    @Column(name = "Description")
    private String description;

    @Column(name = "No of pieces")
    private String noOfPieces;

    @Column(name = "Unit_Of_Measurement")
    private String unitOfMeasurement;

    @Column(name = "HSN_No")
    private String hsnNo;

    @Column(name = "Broker_Name")
    private String brokerName;

    @Column(name = "Quantity")
    private Double quantity;

    @Column(name = "Shortage")
    private Double shortage;

    @Column(name = "GR")
    private Double gr;

    @Column(name = "Net_Qty")
    private Double netQty;

    @Column(name = "Rate")
    private Double rate;

    @Column(name = "Basic_Amount")
    private Double basicAmount;

    @Column(name = "GST_Rate")
    private Double gstRate;

    @Column(name = "GSTIN")
    private String gstin;

    @Column(name = "Total_Amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "Invoice_No", referencedColumnName = "Invoice_No", insertable = false, updatable = false)
    private SalesInvoice salesInvoice;
}

