package com.example.PizzeriaApp.mappers;

import com.example.PizzeriaApp.dto.ProductDTO;
import com.example.PizzeriaApp.models.Product;
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
