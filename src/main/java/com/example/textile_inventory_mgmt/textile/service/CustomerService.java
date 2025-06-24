package com.example.textile_inventory_mgmt.textile.service;

import com.example.textile_inventory_mgmt.textile.model.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    void saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
    void deleteCustomer(Long id);
}
