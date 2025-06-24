//package com.example.textile_inventory_mgmt.textile.service.impl;
//
//import com.example.textile_inventory_mgmt.textile.model.PurchaseOrder;
//import com.example.textile_inventory_mgmt.textile.model.PurchaseOrderItem;
//import com.example.textile_inventory_mgmt.textile.repository.PurchaseOrderRepository;
//import com.example.textile_inventory_mgmt.textile.service.PurchaseOrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PurchaseOrderServiceImpl implements PurchaseOrderService {
//
//    @Autowired
//    private PurchaseOrderRepository purchaseOrderRepository;
//
//    @Override
//    public void saveOrder(PurchaseOrder order) {
//        for (PurchaseOrderItem item : order.getItems()) {
//            item.setPurchaseOrder(order); // set FK reference
//        }
//        purchaseOrderRepository.save(order);
//    }
//}
