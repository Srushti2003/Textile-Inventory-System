package com.example.textile_inventory_mgmt.textile.controller;

import com.example.textile_inventory_mgmt.textile.model.Customer;
import com.example.textile_inventory_mgmt.textile.repository.CustomerRepository;
import com.example.textile_inventory_mgmt.textile.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    // Show form and list of customers
    @GetMapping("/customers")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());  // for form binding
        model.addAttribute("customers", customerService.getAllCustomers()); // optional
        return "customer"; // Loads templates/customer.html
    }

    // Save customer
    @PostMapping("/customers")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customers?success";
    }

    @GetMapping("/customers/show")
    public String showCustomers(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers); // Must match in HTML
        return "show-customers"; // should be in src/main/resources/templates/show-customers.html
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getCustomerById(id); // Add this method in service
        model.addAttribute("customer", customer);
        return "edit-customer"; // You’ll create this form
    }

    @PostMapping("/customers/update")
    public String updateCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer); // save acts as both add/update
        return "redirect:/customers/show?updated"; // add query param
    }
}
