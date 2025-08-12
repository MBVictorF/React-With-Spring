package com.example.demo.okta.developer.jugtours.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Поиск по названию (без учета регистра)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Поиск по ценовому диапазону
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Топ 5 самых дорогих продуктов
    List<Product> findTop5ByOrderByPriceDesc();

    // Поиск продуктов дешевле определенной цены
    List<Product> findByPriceLessThan(BigDecimal price);

    // Поиск продуктов дороже определенной цены
    List<Product> findByPriceGreaterThan(BigDecimal price);

    // Кастомный запрос для поиска продуктов с изображениями
    @Query("SELECT p FROM Product p WHERE p.imageUrl IS NOT NULL")
    List<Product> findProductsWithImages();

    // Кастомный запрос для поиска продуктов без изображений
    @Query("SELECT p FROM Product p WHERE p.imageUrl IS NULL OR p.imageUrl = ''")
    List<Product> findProductsWithoutImages();

    // Поиск по части описания
    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByDescriptionContaining(@Param("keyword") String keyword);
}
