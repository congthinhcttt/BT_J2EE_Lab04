package com.example.Lab04.service;

import com.example.Lab04.model.CartItem;
import com.example.Lab04.model.Order;
import com.example.Lab04.model.OrderDetail;
import com.example.Lab04.model.Product;
import com.example.Lab04.repository.OrderDetailRepository;
import com.example.Lab04.repository.OrderRepository;
import com.example.Lab04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order checkout(List<CartItem> cartItems, long total) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> details = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getId()).orElse(null);

            if (product != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(savedOrder);
                detail.setProduct(product);
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getPrice());
                detail.setSubTotal(item.getPrice() * item.getQuantity());

                details.add(detail);
            }
        }

        orderDetailRepository.saveAll(details);
        savedOrder.setOrderDetails(details);

        return savedOrder;
    }
}