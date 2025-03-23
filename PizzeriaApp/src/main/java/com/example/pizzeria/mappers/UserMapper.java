package com.example.pizzeria.mappers;

import com.example.pizzeria.dto.UserDTO;
import com.example.pizzeria.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());

        return dto;

    }
}

