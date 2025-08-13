package com.example.demo.okta.developer.jugtours;

import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.model.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewInitializer {

    private final ProductRepository repository;

    /*@EventListener(ApplicationReadyEvent.class)
    public void showProductsOnStartup() {
        System.out.println("🛍️ Интернет-магазин запущен!");
        System.out.println("📦 Загрузка каталога товаров...");

        List<Product> products = repository.findAll();

        if (products.isEmpty()) {
            System.out.println("⚠️  Каталог товаров пуст! Добавьте товары через базу данных.");
        } else {
            System.out.println("✅ В каталоге доступно товаров: " + products.size());
            System.out.println("───────────────────────────────────────");

            products.forEach(p ->
                    System.out.printf("🏷️  %-30s │ %10.2f ₽ │ ID: %d%n",
                            p.getName().length() > 30 ?
                                    p.getName().substring(0, 27) + "..." : p.getName(),
                            p.getPrice(),
                            p.getId()
                    )
            );
            System.out.println("───────────────────────────────────────");
        }
    }*/
}
