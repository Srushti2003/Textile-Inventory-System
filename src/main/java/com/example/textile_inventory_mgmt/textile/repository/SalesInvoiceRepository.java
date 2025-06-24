package com.example.textile_inventory_mgmt.textile.repository;

import com.example.textile_inventory_mgmt.textile.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {

    // Optional: Find by invoice number
    SalesInvoice findByInvoiceNo(String invoiceNo);

    @Query("SELECT new map(i.invoiceDate as date, SUM(i.totalAmount) as totalAmount) " +
            "FROM SalesInvoice i GROUP BY i.invoiceDate ORDER BY i.invoiceDate")
    List<Map<String, Object>> fetchSalesAmountByDate();

    @Query("SELECT new map(FUNCTION('YEAR', i.invoiceDate) as year, FUNCTION('MONTH', i.invoiceDate) as month, SUM(i.totalAmount) as totalAmount) " +
        "FROM SalesInvoice i GROUP BY FUNCTION('YEAR', i.invoiceDate), FUNCTION('MONTH', i.invoiceDate) " +
        "ORDER BY year, month")
    List<Map<String, Object>> fetchSalesAmountByMonthYear();

    @Query("SELECT new map(FUNCTION('YEAR', i.invoiceDate) as year, SUM(i.totalAmount) as totalAmount) " +
            "FROM SalesInvoice i GROUP BY FUNCTION('YEAR', i.invoiceDate) ORDER BY year")
    List<Map<String, Object>> fetchSalesAmountByYear();

    @Query("SELECT new map(FUNCTION('MONTH', i.invoiceDate) as month, SUM(i.totalAmount) as totalAmount) " +
            "FROM SalesInvoice i WHERE FUNCTION('YEAR', i.invoiceDate) = :year " +
            "GROUP BY FUNCTION('MONTH', i.invoiceDate) ORDER BY month")
    List<Map<String, Object>> fetchSalesAmountByMonthForYear(Integer year);

    @Query("SELECT DISTINCT FUNCTION('YEAR', i.invoiceDate) FROM SalesInvoice i ORDER BY FUNCTION('YEAR', i.invoiceDate) DESC")
    List<Integer> findDistinctYears();

    @Query("SELECT new map(i.customerName as customer, SUM(i.totalQty) as qty) " +
            "FROM SalesInvoice i GROUP BY i.customerName")
    List<Map<String, Object>> fetchQtyByCustomer();

}
