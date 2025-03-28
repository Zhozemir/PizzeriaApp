package com.example.pizzeria.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    //private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE";
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

    // "sa" == system administrator
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void initializeDatabase() {

        //try-with-resources:
        //Това е конструкция, която гарантира, че всички ресурси, които отворим
        // (като връзка към базата данни и SQL statement),
        // ще бъдат затворени автоматично, когато блокът завърши,
        // независимо дали изпълнението приключва нормално или с изключение

        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {

            String createUserTable = """
                     CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(255) UNIQUE,
                    password VARCHAR(255),
                    role VARCHAR(50),
                    name VARCHAR(255),
                    phone VARCHAR(50)
                    )
                    """;
            stmt.execute(createUserTable);

            String createProductTable = """
                     CREATE TABLE IF NOT EXISTS products (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    price DOUBLE,
                    is_active BOOLEAN
                    )
                    """;
            stmt.execute(createProductTable);

            String createOrderTable = """
                    CREATE TABLE IF NOT EXISTS orders (
                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                   user_id BIGINT,
                   FOREIGN KEY (user_id) REFERENCES users(id),
                   status VARCHAR(50),
                   created_on TIMESTAMP
                   )
                   """;

            // da napravq foreign key kum user (za da printiram orderi za vseki user)

            stmt.execute(createOrderTable);

            // съврзваща таблица между поръчки и продукти
            String createOrderProductsTable = """
                     CREATE TABLE IF NOT EXISTS order_products (
                    order_id BIGINT,
                    product_id BIGINT,
                    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
                    )
                    """;
            stmt.execute(createOrderProductsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
