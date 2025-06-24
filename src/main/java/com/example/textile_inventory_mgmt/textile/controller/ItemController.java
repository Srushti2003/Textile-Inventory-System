package com.example.textile_inventory_mgmt.textile.controller;

import com.example.textile_inventory_mgmt.textile.model.Item;
import com.example.textile_inventory_mgmt.textile.repository.ItemRepository;
import com.example.textile_inventory_mgmt.textile.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    // Show form and item list
    @GetMapping("/items")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());  // for form binding
        model.addAttribute("items", itemService.getAllItems()); // optional
        return "item"; // Loads templates/item.html
    }

    // Save item
    @PostMapping("/items")
    public String saveItem(@ModelAttribute Item item) {
        itemService.saveItem(item);
        return "redirect:/items?success";
    }

    // Show all items
    @GetMapping("/items/show")
    public String showItems(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "show-items";
    }

    // Edit item (load form)
    @GetMapping("/items/edit/{binNo}")
    public String editItem(@PathVariable("binNo") String binNo, Model model) {
        Item item = itemRepository.findById(binNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Bin No: " + binNo));
        model.addAttribute("item", item);
        return "edit-item"; // templates/edit-item.html
    }

    // Update item (handle form submit)
    @PostMapping("/items/update")
    public String updateItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item); // reuse save method
        return "redirect:/items/show?updated";
    }

    // Delete item
    @GetMapping("/items/delete/{binNo}")
    public String deleteItem(@PathVariable("binNo") String binNo) {
        itemRepository.deleteById(binNo);
        return "redirect:/items/show?deleted";
    }

}
