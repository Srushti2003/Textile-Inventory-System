package com.example.textile_inventory_mgmt.textile.service;

import com.example.textile_inventory_mgmt.textile.model.Item;
import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    void saveItem(Item item);
    Item getItemById(String binNo);
    void deleteItem(String binNo);
}
