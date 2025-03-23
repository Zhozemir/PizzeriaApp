package com.example.pizzeria.mappers;

import com.example.pizzeria.dto.ProductDTO;
import com.example.pizzeria.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        return dto;
    }

}
