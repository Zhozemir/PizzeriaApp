package com.example.pizzeria.repositories.impl;

import com.example.pizzeria.database.Database;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.DataAccessException;
import com.example.pizzeria.repositories.interfaces.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final DataSource dataSource;

    @Autowired
    public OrderDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public boolean save(Order order) {

        String sql = "INSERT INTO orders (user_id, status, created_on) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (order.getUser() == null || order.getUser().getId() == null)
                throw new IllegalStateException("Грешка: Поръчката няма свързан потребител!");

            ps.setLong(1, order.getUser().getId());
            ps.setString(2, order.getStatus().name());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreatedOn()));

            int affected = ps.executeUpdate();

            if(affected == 0)
                return false;

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next())
                order.setId(keys.getLong(1));

            // записване на връзката между поръчка и продукти
            if(order.getProducts() != null) {

                for(Product product : order.getProducts()){

                    addProductToOrder(order.getId(), product.getId(), conn);

                }

            }
            return true;
        } catch(SQLException ex) {
            throw new DataAccessException("Не може да се запише поръчка за userId=" + (order.getUser() != null ? order.getUser().getId() : "null"), ex);
        }
    }

    private void addProductToOrder(Long orderId, Long productId, Connection conn) {

        String sql = "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);
            ps.setLong(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Не може да се свръже поръчка " + orderId + " с продукт " + productId, e
            );
        }
    }

    @Override
    public Optional<Order> findById(Long id) {

        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");
                if(deliveredTs != null){
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));

                Optional<User> userOpt = getUserById(rs.getLong("user_id"), conn);
                userOpt.ifPresent(order::setUser);

                return Optional.of(order);


            }

        } catch(SQLException e) {
            throw new DataAccessException("Не може да се зареди поръчка id=" + id, e);
        }
        return Optional.empty();
    }

    private List<Product> getProductsForOrder(Long orderId, Connection conn) {

        List<Product> products = new ArrayList<>();

        String sql = """
                     SELECT products.* FROM products
                     JOIN order_products ON products.id = order_products.product_id
                     WHERE order_products.order_id = ?
                     """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setActive(rs.getBoolean("is_active"));
                products.add(product);

            }

        } catch(SQLException e) {
            throw new DataAccessException("Не могат да се заредят продукти за поръчка " + orderId, e);
        }
        return products;
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");
                if(deliveredTs != null){
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));
                orders.add(order);

            }

        } catch(SQLException e) {
            throw new DataAccessException("Не могат да се заредят поръчки със статус " + status, e);
        }
        return orders;
    }

    @Override
    public List<Order> findByUserId(Long id) {

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");
                if(deliveredTs != null){
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));

                Optional<User> userOpt = getUserById(rs.getLong("user_id"), conn);
                userOpt.ifPresent(order::setUser);

                orders.add(order);
            }

        } catch(SQLException e) {
            throw new DataAccessException("Не могат да се заредят поръчките на uderId=" + id, e);
        }
        return orders;

    }

    @Override
    public Optional<User> getUserById(Long userId, Connection conn) {

        if(userId == null)
            return Optional.empty();

        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                return Optional.of(user);

            }

        } catch (SQLException e) {
            throw new DataAccessException("Не може да се зареди потребител id=" + userId, e);
        }
        return Optional.empty();
    }


    @Override
    public List<Order> findAll() {

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()){

                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");
                if(deliveredTs != null){
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));
                orders.add(order);

            }
        } catch(SQLException e) {
            throw new DataAccessException("Не могат да се заредят всички поръчки" , e);
        }
        return orders;
    }

    @Override
    public boolean updateStatus(Long orderId, OrderStatus newStatus) {

        String sql = """
                UPDATE orders
                SET status = ?,
                delivered_on = CASE WHEN ? = 'DELIVERED' THEN CURRENT_TIMESTAMP
                ELSE delivered_on END
                WHERE id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setString(2, newStatus.name());
            ps.setLong(3, orderId);
            int affected = ps.executeUpdate();
            return affected > 0;

        } catch(SQLException e) {
            throw new DataAccessException("Не може да се обнови статуса на поръчка  " + orderId, e);
        }
    }

    @Override
    public List<Order> findByCreatedOnBetween(LocalDateTime start, LocalDateTime end) {

        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE created_on BETWEEN ? AND ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");
                if(deliveredTs != null){
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));
                orders.add(order);

            }

        } catch(SQLException e) {
            throw new DataAccessException("Не могат да се заредят поръчки между " + start + " и " + end, e);
        }
        return orders;
    }

    @Override
    public List<Order> findDeliveredAfter(LocalDateTime since){

        List<Order> orders = new ArrayList<>();

        String sql  = "SELECT * FROM orders WHERE status = ? AND delivered_on > ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, OrderStatus.DELIVERED.name());
            ps.setTimestamp(2, Timestamp.valueOf(since));
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setStatus(OrderStatus.DELIVERED);
                order.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());

                Timestamp deliveredTs = rs.getTimestamp("delivered_on");

                if (deliveredTs != null) {
                    order.setDeliveredOn(deliveredTs.toLocalDateTime());
                }

                order.getProducts().addAll(getProductsForOrder(order.getId(), conn));
                getUserById(rs.getLong("user_id"), conn).ifPresent(order::setUser);
                orders.add(order);

            }
        } catch (SQLException еx){
                throw new DataAccessException("Не могат да се заредят нови доставени поръчки след " + since, еx);
        }
            return orders;

    }

}
