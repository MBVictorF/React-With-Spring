package com.example.demo.okta.developer.jugtours.web;

import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Получить все продукты
     */
    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        log.info("Запрос всех продуктов");
        return productService.getAllProducts();
    }

    /**
     * Получить продукт по ID
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        log.info("Запрос продукта с ID: {}", id);
        Optional<Product> product = productService.getProductById(id);
        return product.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Создать новый продукт
     */
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) throws URISyntaxException {
        log.info("Запрос создания продукта: {}", product);
        Product result = productService.saveProduct(product);
        return ResponseEntity.created(new URI("/api/product/" + result.getId()))
                .body(result);
    }

    /**
     * Обновить продукт
     */
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        log.info("Запрос обновления продукта: {}", product);
        Product result = productService.updateProduct(product);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Удалить продукт
     */
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        log.info("Запрос удаления продукта с ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск продуктов по названию
     */
    @GetMapping("/products/search")
    public Collection<Product> searchProducts(@RequestParam String name) {
        log.info("Поиск продуктов по названию: {}", name);
        return productService.searchProductsByName(name);
    }

    /**
     * Получить продукты в ценовом диапазоне
     */
    @GetMapping("/products/price-range")
    public Collection<Product> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        log.info("Поиск продуктов в диапазоне цен: {} - {}", min, max);
        return productService.getProductsByPriceRange(min, max);
    }

    /**
     * Получить статистику продуктов
     */
    @GetMapping("/products/stats")
    public ResponseEntity<?> getProductStats() {
        long totalCount = productService.getProductCount();
        var stats = new ProductStats(totalCount);
        return ResponseEntity.ok(stats);
    }

    // Внутренний класс для статистики
    public static class ProductStats {
        public long totalProducts;

        public ProductStats(long totalProducts) {
            this.totalProducts = totalProducts;
        }
    }
}
