package com.example.textile_inventory_mgmt.textile.repository;

import com.example.textile_inventory_mgmt.textile.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends JpaRepository<Item, String> {
    // Since itemCode is String, use String as ID type
}
