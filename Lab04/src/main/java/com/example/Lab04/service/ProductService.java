package com.example.Lab04.service;

import com.example.Lab04.model.Product;
import com.example.Lab04.repository.CategoryRepository;
import com.example.Lab04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Product> getAllProducts(int page, int pageSize, String sortDir, Integer categoryId) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }

        return productRepository.findAll(pageable);
    }

    public Page<Product> getSearchProducts(String keyword, int page, int pageSize, String sortDir, Integer categoryId) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = categoryId != null;

        if (hasKeyword && hasCategory) {
            return productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
        }

        if (hasKeyword) {
            return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        if (hasCategory) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }

        return productRepository.findAll(pageable);
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