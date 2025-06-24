package com.example.textile_inventory_mgmt.textile.repository;

import com.example.textile_inventory_mgmt.textile.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
