package com.example.Lab04.controller;

import com.example.Lab04.service.CartService;
import com.example.Lab04.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/list";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id) {
        cartService.addToCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam int productId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }

    @PostMapping("/order")
    public String order(Model model) {
        if (cartService.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        var newOrder = orderService.checkout(cartService.getItems(), cartService.getTotal());
        cartService.clear();

        model.addAttribute("order", newOrder);
        return "order/success";
    }
}