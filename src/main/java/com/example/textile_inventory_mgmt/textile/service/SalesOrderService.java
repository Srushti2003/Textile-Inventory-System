package com.example.textile_inventory_mgmt.textile.service;

import com.example.textile_inventory_mgmt.textile.model.SalesInvoice;
import com.example.textile_inventory_mgmt.textile.model.SalesTrans;
import com.example.textile_inventory_mgmt.textile.repository.SalesInvoiceRepository;
import com.example.textile_inventory_mgmt.textile.repository.SalesTransRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesInvoiceRepository invoiceRepository;
    private final SalesTransRepository transRepository;

    public SalesInvoice saveOrder(SalesInvoice invoice, List<SalesTrans> items) {
        // Save invoice
        SalesInvoice savedInvoice = invoiceRepository.save(invoice);

        // Set reference and save each transaction
        for (SalesTrans item : items) {
            item.setInvoiceNo(savedInvoice.getInvoiceNo());
            item.setSalesInvoice(savedInvoice);
        }
        transRepository.saveAll(items);

        return savedInvoice;
    }

    public SalesInvoice findInvoiceByNo(String invoiceNo) {
        return invoiceRepository.findByInvoiceNo(invoiceNo);
    }

    public List<SalesTrans> findItemsByInvoiceNo(String invoiceNo) {
        return transRepository.findByInvoiceNo(invoiceNo);
    }
}

