package com.example.PizzeriaApp.repositories.impl;

import com.example.PizzeriaApp.database.Database;
import com.example.PizzeriaApp.enumerators.UserRole;
import com.example.PizzeriaApp.models.User;
import com.example.PizzeriaApp.repositories.interfaces.UserDAO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Override
    public Optional<User> findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));

                return Optional.of(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean save(User user) {

        String sql = "INSERT INTO users (username, password, role, name, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getName());
            ps.setString(5, user.getPhone());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0)
                return false;

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if(generatedKeys.next())
                user.setId(generatedKeys.getLong(1));

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
