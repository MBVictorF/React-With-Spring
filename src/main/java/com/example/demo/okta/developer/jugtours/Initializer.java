package com.example.demo.okta.developer.jugtours;

import com.example.demo.okta.developer.jugtours.model.Product;
import com.example.demo.okta.developer.jugtours.model.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
class Initializer implements CommandLineRunner {
    private final ProductRepository repository;

    private static Connection connection;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Initializer(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        // Проверяем подключение к базе данных
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connection to PostgreSQL established successfully!");

            // Детальная диагностика
            debugDatabase(connection);

            connection.close();
        } catch (SQLException e) {
            System.err.println("❌ Failed to establish connection to PostgreSQL!");
            e.printStackTrace();
            return;
        }

        // Проверяем данные через JPA
        debugJPARepository();

        // Выводим все продукты из базы данных
        displayAllProducts();
    }

    private void debugDatabase(Connection connection) throws SQLException {
        System.out.println("\n=== ДИАГНОСТИКА БАЗЫ ДАННЫХ ===");

        // 1. Проверяем все таблицы в базе
        System.out.println("1. Все таблицы в базе данных:");
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            String schema = tables.getString("TABLE_SCHEM");
            System.out.printf("   - Схема: %s, Таблица: %s%n", schema, tableName);
        }
        tables.close();

        // 2. Проверяем конкретно таблицу user_product
        System.out.println("\n2. Поиск таблицы user_product:");
        ResultSet userProductTable = metaData.getTables(null, null, "user_product", new String[]{"TABLE"});
        boolean tableExists = false;
        while (userProductTable.next()) {
            tableExists = true;
            String tableName = userProductTable.getString("TABLE_NAME");
            String schema = userProductTable.getString("TABLE_SCHEM");
            System.out.printf("   ✅ Найдена таблица: %s в схеме: %s%n", tableName, schema);
        }
        userProductTable.close();

        if (!tableExists) {
            System.out.println("   ❌ Таблица user_product не найдена!");
            return;
        }

        // 3. Проверяем структуру таблицы user_product
        System.out.println("\n3. Структура таблицы user_product:");
        ResultSet columns = metaData.getColumns(null, null, "user_product", null);
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String dataType = columns.getString("TYPE_NAME");
            int size = columns.getInt("COLUMN_SIZE");
            String nullable = columns.getString("IS_NULLABLE");
            System.out.printf("   - %s: %s(%d) %s%n",
                    columnName, dataType, size, nullable.equals("YES") ? "nullable" : "not null");
        }
        columns.close();

        // 4. Прямой SQL запрос к таблице
        System.out.println("\n4. Данные в таблице user_product (прямой SQL):");
        String directQuery = "SELECT id, name, description, price, image_url FROM user_product";
        try (PreparedStatement stmt = connection.prepareStatement(directQuery);
             ResultSet rs = stmt.executeQuery()) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("   Запись %d: ID=%s, Name=%s, Price=%s, Image=%s%n",
                        count,
                        rs.getObject("id"),
                        rs.getString("name"),
                        rs.getObject("price"),
                        rs.getString("image_url")
                );
            }
            System.out.printf("   Всего записей через SQL: %d%n", count);

        } catch (SQLException e) {
            System.err.println("   ❌ Ошибка выполнения прямого SQL запроса:");
            e.printStackTrace();
        }

        // 5. Проверяем схемы Hibernate
        System.out.println("\n5. Проверка схемы по умолчанию:");
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT current_schema()")) {
            if (rs.next()) {
                System.out.printf("   Текущая схема: %s%n", rs.getString(1));
            }
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW search_path")) {
            if (rs.next()) {
                System.out.printf("   Search path: %s%n", rs.getString(1));
            }
        }
    }

    private void debugJPARepository() {
        System.out.println("\n=== ДИАГНОСТИКА JPA REPOSITORY ===");

        try {
            // Подсчет через repository
            long count = repository.count();
            System.out.printf("1. Repository.count(): %d%n", count);

            // Получение всех записей
            List<Product> products = repository.findAll();
            System.out.printf("2. Repository.findAll().size(): %d%n", products.size());

            if (!products.isEmpty()) {
                System.out.println("3. Продукты через JPA:");
                products.forEach(product -> {
                    System.out.printf("   - ID: %d, Name: %s, Price: %s%n",
                            product.getId(),
                            product.getName(),
                            product.getPrice()
                    );
                });
            } else {
                System.out.println("3. ❌ JPA Repository не находит данные!");
            }

        } catch (Exception e) {
            System.err.println("❌ Ошибка в JPA Repository:");
            e.printStackTrace();
        }
    }

    private void displayAllProducts() {
        System.out.println("\n=== ФИНАЛЬНЫЙ СПИСОК ПРОДУКТОВ ===");

        List<Product> allProducts = new ArrayList<>();//repository.findAll();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM user_product";
            statement.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*if (allProducts.isEmpty()) {
            System.out.println("❌ JPA Repository возвращает пустой список");

            // Попробуем добавить тестовый продукт
            System.out.println("Добавляем тестовый продукт...");
            try {
                Product testProduct = new Product(
                        "Test Product",
                        "Test Description",
                        new BigDecimal("99.99"),
                        "/images/test.jpg"
                );
                Product saved = repository.save(testProduct);
                System.out.printf("✅ Тестовый продукт добавлен с ID: %d%n", saved.getId());

                // Проверяем снова
                long newCount = repository.count();
                System.out.printf("Новое количество продуктов: %d%n", newCount);

            } catch (Exception e) {
                System.err.println("❌ Ошибка при добавлении тестового продукта:");
                e.printStackTrace();
            }

        } else {
            System.out.printf("✅ Найдено продуктов: %d%n", allProducts.size());
            allProducts.forEach(product -> {
                System.out.printf("   - ID: %d, Name: %s, Price: %s%n",
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                );
            });
        }*/
    }
}
