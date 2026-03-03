package com.example.Lab04.service;


import com.example.Lab04.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Lab04.repository.CategoryRepository;
import com.example.Lab04.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}

