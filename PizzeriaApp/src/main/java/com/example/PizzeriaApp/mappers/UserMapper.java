package com.example.PizzeriaApp.mappers;

import com.example.PizzeriaApp.dto.UserDTO;
import com.example.PizzeriaApp.models.User;
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

