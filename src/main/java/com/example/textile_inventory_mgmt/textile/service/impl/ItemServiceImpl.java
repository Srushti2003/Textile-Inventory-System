package com.example.textile_inventory_mgmt.textile.service.impl;

import com.example.textile_inventory_mgmt.textile.model.Item;
import com.example.textile_inventory_mgmt.textile.repository.ItemRepository;
import com.example.textile_inventory_mgmt.textile.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item getItemById(String binNo) {
        return itemRepository.findById(binNo).orElse(null);
    }

    @Override
    public void deleteItem(String binNo) {
        itemRepository.deleteById(binNo);
    }
}
