package com.example.textile_inventory_mgmt.textile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cust_id;

    private String customerName;
    private String address;
    private String city;
    private String state;
    private String cust_type;
    private String tax_type;
    private Integer pin;
    private String contact_person;
    private String email;
    private String mobile;
    private String gstin;
    private String vendor_code;
    private Integer payment_terms; // in how many days cust has to pay the bill amt to comapany
    private Double openingBalance;
    private Double closingBalance;
    private Double opBal_receipt;
}
