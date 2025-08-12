package com.example.demo.okta.developer.jugtours.service;

import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.model.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Получить все продукты из базы данных
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Найти продукт по ID
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Сохранить новый продукт
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Обновить существующий продукт
     */
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Удалить продукт по ID
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Проверить количество продуктов в базе
     */
    public long getProductCount() {
        return productRepository.count();
    }

    /**
     * Найти продукты в определенном ценовом диапазоне
     */
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Найти продукты по названию (частичное совпадение)
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Получить самые дорогие продукты
     */
    public List<Product> getTopExpensiveProducts(int limit) {
        return productRepository.findTop5ByOrderByPriceDesc();
    }
}
