package com.example.textile_inventory_mgmt.textile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    private String bin_no;
    private String category;
    private String hsn_no;
    private String itemCode; // E.g. D N 1758
    private String description;
    private String add_description;
    private String unit; // MTR
    private Double openingBalance;
    private Double closingBalance;
    private Integer reorder_level;
    private String c_lass;
    private Double rate;
    private Double gst;
    private String vendor;
    private String type;
}
