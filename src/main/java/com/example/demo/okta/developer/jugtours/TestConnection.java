package com.example.demo.okta.developer.jugtours;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/*
@Component
@RequiredArgsConstructor
public class TestConnection {

    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void runTest() throws Exception {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM public.user_product")) {
            while (rs.next()) {
                System.out.printf("JDBC test: id=%d, name=%s, price=%s%n",
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"));
            }
        }
    }
}*/
