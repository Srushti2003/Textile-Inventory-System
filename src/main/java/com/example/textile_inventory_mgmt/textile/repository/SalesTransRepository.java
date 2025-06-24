package com.example.textile_inventory_mgmt.textile.repository;

import com.example.textile_inventory_mgmt.textile.model.SalesInvoice;
import com.example.textile_inventory_mgmt.textile.model.SalesTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalesTransRepository extends JpaRepository<SalesTrans, Long> {

    // Optional: Get all transactions for a given invoice
    List<SalesTrans> findByInvoiceNo(String invoiceNo);
    List<SalesTrans> findBySalesInvoice(SalesInvoice invoice);

    @Query("SELECT new map(t.itemCode as item, SUM(t.quantity) as qty) " +
            "FROM SalesTrans t GROUP BY t.itemCode")
    List<Map<String, Object>> fetchTopSellingItems();



}

