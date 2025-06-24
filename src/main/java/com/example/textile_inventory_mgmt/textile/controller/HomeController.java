package com.example.textile_inventory_mgmt.textile.controller;

import com.example.textile_inventory_mgmt.textile.repository.SalesInvoiceRepository;
import com.example.textile_inventory_mgmt.textile.repository.SalesTransRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    private final SalesInvoiceRepository invoiceRepository;
    private final SalesTransRepository transRepository;

    public HomeController(SalesInvoiceRepository invoiceRepository, SalesTransRepository transRepository) {
        this.invoiceRepository = invoiceRepository;
        this.transRepository = transRepository;
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "year", required = false) Integer year, Model model) {
        // Debug logging
        System.out.println("Received year parameter: " + year);

        List<Map<String, Object>> qtyByCustomer = invoiceRepository.fetchQtyByCustomer();
        List<Map<String, Object>> topItems = transRepository.fetchTopSellingItems();
        List<Map<String, Object>> salesByYear = invoiceRepository.fetchSalesAmountByYear();

        List<Integer> years = IntStream.rangeClosed(2020, 2030)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        Integer selectedYear = year;
        if (selectedYear == null || !years.contains(selectedYear)) {
            int currentYear = Year.now().getValue();
            if (years.contains(currentYear)) {
                selectedYear = currentYear;
            } else {
                selectedYear = years.get(0); // Default to the most recent year in the list
            }
        }

        // Debug logging
        System.out.println("Final selected year: " + selectedYear);
        System.out.println("Available years: " + years);

        List<Map<String, Object>> salesData = invoiceRepository.fetchSalesAmountByMonthForYear(selectedYear);

        model.addAttribute("salesData", salesData);
        model.addAttribute("customerStats", qtyByCustomer);
        model.addAttribute("itemStats", topItems);
        model.addAttribute("salesByYear", salesByYear);
        model.addAttribute("years", years);
        model.addAttribute("selectedYear", selectedYear);

        return "home";
    }

}

