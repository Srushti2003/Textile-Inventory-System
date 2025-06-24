package com.example.textile_inventory_mgmt.textile.controller;

import com.example.textile_inventory_mgmt.textile.model.SalesInvoice;
import com.example.textile_inventory_mgmt.textile.model.SalesTrans;
import com.example.textile_inventory_mgmt.textile.repository.CustomerRepository;
import com.example.textile_inventory_mgmt.textile.repository.ItemRepository;
import com.example.textile_inventory_mgmt.textile.repository.SalesInvoiceRepository;
import com.example.textile_inventory_mgmt.textile.repository.SalesTransRepository;
import com.example.textile_inventory_mgmt.textile.model.Customer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final SalesInvoiceRepository invoiceRepository;
    private final SalesTransRepository transRepository;
    private final CustomerRepository customerRepo;
    private final ItemRepository itemRepo;

    @GetMapping("/buy")
    public String showBuyForm(Model model, @RequestParam(required = false) Boolean showAlert) {
        model.addAttribute("showAlert", showAlert);
        model.addAttribute("customers", customerRepo.findAll());
        model.addAttribute("items", itemRepo.findAll());
        return "buy";
    }

    @PostMapping("/purchase-order")
    public String submitOrder(
            @RequestParam("purchaseOrder.customerId") Long customerId,
            @RequestParam("purchaseOrder.orderDate") String orderDateStr,
            HttpServletRequest request,
            Model model
    ) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        // Generate invoice number (you can implement logic for next serial number)
        String invoiceNo = "INV" + System.currentTimeMillis();

        LocalDate orderDate = LocalDate.parse(orderDateStr);
        List<SalesTrans> transList = new ArrayList<>();

        double totalQty = 0.0;
        double basicAmountTotal = 0.0;

        int index = 0;
        while (true) {
            String prefix = "purchaseOrderItems[" + index + "]";
            String itemCode = request.getParameter(prefix + ".itemCode");
            if (itemCode == null) break;

            String description = request.getParameter(prefix + ".description");
            String unit = request.getParameter(prefix + ".unit");
            String rateStr = request.getParameter(prefix + ".rate");
            String qtyStr = request.getParameter(prefix + ".quantity");
            String remarks = request.getParameter(prefix + ".remarks");

            double rate = Double.parseDouble(rateStr);
            double qty = Double.parseDouble(qtyStr);
            double basicAmount = rate * qty;

            SalesTrans item = new SalesTrans();
            item.setInvoiceNo(invoiceNo);
            item.setItemCode(itemCode);
            item.setDescription(description);
            item.setUnitOfMeasurement(unit);
            item.setRate(rate);
            item.setQuantity(qty);
            item.setBasicAmount(basicAmount);
            item.setTotalAmount(basicAmount);
            item.setNoOfPieces("1");
            item.setHsnNo("54");
            item.setGstRate(5.0);
            item.setGr(0.0);
            item.setShortage(0.0);
            item.setNetQty(qty);

            transList.add(item);

            basicAmountTotal += basicAmount;
            totalQty += qty;
            index++;
        }

        double cgst = basicAmountTotal * 0.025;
        double sgst = basicAmountTotal * 0.025;
        double totalGST = cgst + sgst;
        double totalAmount = basicAmountTotal + totalGST;

        SalesInvoice invoice = new SalesInvoice();
        invoice.setInvoiceNo(invoiceNo);
        invoice.setInvoiceDate(orderDate);
        invoice.setCustId(String.valueOf(customer.getCust_id()));
        invoice.setCustomerName(customer.getCustomerName());
        invoice.setState("Maharashtra");
        invoice.setBasicAmountTotal(basicAmountTotal);
        invoice.setDiscPercent(0.0);
        invoice.setDiscount(0.0);
        invoice.setBrokPercent(0.0);
        invoice.setBrokerage(0.0);
        invoice.setTaxableAmount(basicAmountTotal);
        invoice.setGstPercent(5.0);
        invoice.setCgst(cgst);
        invoice.setSgst(sgst);
        invoice.setIgst(0.0);
        invoice.setTotalGst(totalGST);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalQty(totalQty);
        invoice.setPaymentReceived(true);
        invoice.setRemarks("");
        invoice.setDueDate(orderDate.plusDays(30));
        invoice.setAmountReceived(0.0);
        invoice.setChequeNos("");
        invoice.setChequeDate(null);
        invoice.setAmountDue(totalAmount);

        // Save invoice and items
        invoiceRepository.save(invoice);
        for (SalesTrans t : transList) {
            t.setSalesInvoice(invoice);
        }
        transRepository.saveAll(transList);

        model.addAttribute("orderId", invoice.getId()); // Pass ID to generate invoice link
        return "order-success"; // View name without .html

    }

    @GetMapping("/invoice/{id}")
    public void generateInvoice(@PathVariable Long id, HttpServletResponse response) {
        try {
            Optional<SalesInvoice> optionalInvoice = invoiceRepository.findById(id);
            if (!optionalInvoice.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
                return;
            }

            SalesInvoice invoice = optionalInvoice.get();
            List<SalesTrans> items = transRepository.findBySalesInvoice(invoice);

            // Set response for PDF download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Invoice_" + invoice.getInvoiceNo() + ".pdf");

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Title
            document.add(new Paragraph("Sales Invoice", titleFont));
            document.add(new Paragraph(" "));

            // Invoice Info
            document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo(), normalFont));
            document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate(), normalFont));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName(), normalFont));
            document.add(new Paragraph("Customer ID: " + invoice.getCustId(), normalFont));
            document.add(new Paragraph("State: " + invoice.getState(), normalFont));
            document.add(new Paragraph("Remarks: " + invoice.getRemarks(), normalFont));
            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("Item Code");
            table.addCell("Description");
            table.addCell("Quantity");
            table.addCell("Rate");
            table.addCell("Basic Amount");

            for (SalesTrans item : items) {
                table.addCell(item.getItemCode());
                table.addCell(item.getDescription());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getRate()));
                table.addCell(String.valueOf(item.getBasicAmount()));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            // Financial Details
            document.add(new Paragraph("Basic Amount Total: ₹" + invoice.getBasicAmountTotal()));
            document.add(new Paragraph("Discount %: " + invoice.getDiscPercent()));
            document.add(new Paragraph("Discount: ₹" + invoice.getDiscount()));
            document.add(new Paragraph("Broker Name: " + invoice.getBrokerName()));
            document.add(new Paragraph("Brokerage %: " + invoice.getBrokPercent()));
            document.add(new Paragraph("Brokerage: ₹" + invoice.getBrokerage()));
            document.add(new Paragraph("Taxable Amount: ₹" + invoice.getTaxableAmount()));
            document.add(new Paragraph("GST %: " + invoice.getGstPercent()));
            document.add(new Paragraph("CGST: ₹" + invoice.getCgst()));
            document.add(new Paragraph("SGST: ₹" + invoice.getSgst()));
            document.add(new Paragraph("IGST: ₹" + invoice.getIgst()));
            document.add(new Paragraph("Total GST: ₹" + invoice.getTotalGst()));
            document.add(new Paragraph("Total Amount: ₹" + invoice.getTotalAmount()));
            document.add(new Paragraph("Total Quantity: " + invoice.getTotalQty()));
            document.add(new Paragraph("Amount Received: ₹" + invoice.getAmountReceived()));
            document.add(new Paragraph("Amount Due: ₹" + invoice.getAmountDue()));
            document.add(new Paragraph("Cheque Nos: " + invoice.getChequeNos()));
            document.add(new Paragraph("Cheque Date: " + invoice.getChequeDate()));
            document.add(new Paragraph("Payment Received: " + (invoice.getPaymentReceived() != null && invoice.getPaymentReceived() ? "Yes" : "No")));
            document.add(new Paragraph("Due Date: " + invoice.getDueDate()));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating invoice");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


}
