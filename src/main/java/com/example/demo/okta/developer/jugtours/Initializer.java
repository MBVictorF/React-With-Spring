/*
package com.example.demo.okta.developer.jugtours;

*/
/*import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.model.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class Initializer implements CommandLineRunner {

    private final ProductRepository repository;

    public Initializer(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        debugJPARepository();
        displayAllProducts();
    }

    *//*
*/
/**
     * Диагностика работы репозитория
     *//*
*/
/*
    private void debugJPARepository() {
        System.out.println("\n=== ДИАГНОСТИКА JPA REPOSITORY ===");

        try {
            // Подсчет записей
            long count = repository.count();
            System.out.printf("1. Repository.count(): %d%n", count);

            // Получение всех продуктов
            List<Product> products = repository.findAll();
            System.out.printf("2. Repository.findAll().size(): %d%n", products.size());

            if (!products.isEmpty()) {
                System.out.println("3. Список продуктов:");
                products.forEach(product ->
                        System.out.printf("   - ID: %d, Name: %s, Price: %s%n",
                                product.getId(),
                                product.getName(),
                                product.getPrice()
                        )
                );
            } else {
                System.out.println("❌ Продукты в базе отсутствуют");
            }

        } catch (Exception e) {
            System.err.println("❌ Ошибка в JPA Repository:");
            e.printStackTrace();
        }
    }

    *//*
*/
/**
     * Вывод всех продуктов в финальном списке
     *//*
*/
/*
    private void displayAllProducts() {
        System.out.println("\n=== ФИНАЛЬНЫЙ СПИСОК ПРОДУКТОВ (JPA) ===");

        List<Product> allProducts = repository.findAll();

        if (allProducts.isEmpty()) {
            System.out.println("❌ Список пуст. Добавляем тестовый продукт...");
            Product testProduct = new Product(
                    "Test Product",
                    "Test Description",
                    new BigDecimal("99.99"),
                    "/images/test.jpg"
            );
            Product saved = repository.save(testProduct);
            System.out.printf("✅ Тестовый продукт добавлен с ID: %d%n", saved.getId());
        } else {
            System.out.printf("✅ Найдено продуктов: %d%n", allProducts.size());
            allProducts.forEach(product ->
                    System.out.printf("   - ID: %d, Name: %s, Price: %s%n",
                            product.getId(),
                            product.getName(),
                            product.getPrice()
                    )
            );
        }
    }
}*//*


import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.model.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ProductInitializer {

    private final ProductRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void showProductsOnStartup() {
        System.out.println("📦 Данные в таблице user_product:");

        List<Product> products = repository.findAll();

        if (products.isEmpty()) {
            System.out.println("⚠ Таблица пуста!");
        } else {
            products.forEach(product ->
                    System.out.printf("➡ ID: %d, Name: %s, Price: %s, Image: %s%n",
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl()
                    )
            );
        }
    }
}
*/
