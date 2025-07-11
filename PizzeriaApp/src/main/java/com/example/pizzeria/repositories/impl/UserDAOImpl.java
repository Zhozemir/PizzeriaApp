package com.example.pizzeria.repositories.impl;

import com.example.pizzeria.database.Database;
import com.example.pizzeria.enumerators.UserRole;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.DataAccessException;
import com.example.pizzeria.repositories.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final DataSource dataSource;

    @Autowired
    public UserDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){

                if(!rs.next())
                    return Optional.empty();

                User user = mapRowToUser(rs);

                return Optional.of(user);

            }

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Грешка при търсене на потребителско име = " + username, e
            );

        }
    }

    @Override
    public boolean save(User user) {

        String sql = "INSERT INTO users (username, password, role, name, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();

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
            throw new DataAccessException("Грешка при записване на потребител: " + user.getUsername(), e);
        }
    }

    private User mapRowToUser (ResultSet rs) throws SQLException {

        User user  = new User();

        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(UserRole.valueOf(rs.getString("role")));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));

        return user;

    }

}
