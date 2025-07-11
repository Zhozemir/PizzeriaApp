package com.example.pizzeria.repositories.impl;

import com.example.pizzeria.database.Database;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.repositories.DataAccessException;
import com.example.pizzeria.repositories.interfaces.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final DataSource dataSource;

    @Autowired
    public ProductDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(Product product) {

        String sql = "INSERT INTO products (name, price, is_active) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setBoolean(3, product.isActive());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) return false;

            try(ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next())
                    product.setId(keys.getLong(1));

            }

            return true;

        } catch(SQLException e) {

            throw new DataAccessException(
                    "Грешка при записване на продукт: " + product.getName(), e
            );

        }
    }

    @Override
    public Optional<Product> findById(Long id) {

        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = dataSource.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next())
                    return Optional.empty();

                Product product = mapRowToProduct(rs);
                return Optional.of(product);

            }

        } catch (SQLException e) {

            throw new DataAccessException("Грешка при четене на продукт с ID=" + id, e);

        }
    }

    @Override
    public List<Product> findActiveProducts() {

        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_active = true";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                products.add(mapRowToProduct(rs));
            }

            return products;

        } catch(SQLException e) {
            throw new DataAccessException("Грешка при зареждане на активни продукти", e);
        }
    }

    @Override
    public boolean update(Product product) {

        String sql = "UPDATE products SET name = ?, price = ?, is_active = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setBoolean(3, product.isActive());
            ps.setLong(4, product.getId());
            int affected = ps.executeUpdate();
            return affected > 0;

        } catch(SQLException e) {
            throw new DataAccessException("Грешка при обновяване на продукт с ID=" + product.getId(), e);
        }
    }

    @Override
    public Optional<Product> findActiveById(Long id){
        return findById(id).filter(Product::isActive);
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException{

        Product p = new Product();

        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setActive(rs.getBoolean("is_active"));
        return p;

    }

}