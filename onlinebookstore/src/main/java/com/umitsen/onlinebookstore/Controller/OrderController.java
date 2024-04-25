package com.umitsen.onlinebookstore.Controller;

import com.umitsen.onlinebookstore.Entity.Order;
import com.umitsen.onlinebookstore.Entity.OrderRequest;
import com.umitsen.onlinebookstore.Exception.*;
import com.umitsen.onlinebookstore.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping
    public void placeOrder(@RequestBody OrderRequest orderRequest) throws InsufficientStockException, BookNotFoundException, InsufficientOrderTotalException, UserNotFoundException {
        orderService.placeNewOrder(orderRequest);
    }
    @GetMapping("{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable int userId){
        List<Order> userOrders = orderService.getOrdersForUser(userId);
        return ResponseEntity.ok(userOrders);
    }
    @GetMapping("/details/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsById(@PathVariable Long orderId) throws OrderNotFoundException {
        Map<String, Object> orderDetails = orderService.getOrderDetailsById(orderId);
        return ResponseEntity.ok(orderDetails);
    }



}
