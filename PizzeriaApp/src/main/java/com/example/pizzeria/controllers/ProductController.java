package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.validators.ProductControllerValidator;
import com.example.pizzeria.controllers.requests.ProductCreateRequest;
import com.example.pizzeria.dto.ProductDTO;
import com.example.pizzeria.mappers.ProductMapper;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductControllerValidator productControllerValidator;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductControllerValidator productControllerValidator, ProductMapper productMapper) {

        this.productService = productService;
        this.productControllerValidator = productControllerValidator;
        this.productMapper = productMapper;

    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductCreateRequest productCreateRequest) {

        productControllerValidator.createProductValidator(productCreateRequest);

        Product created = productService.addProduct(productCreateRequest.getName(), productCreateRequest.getPrice());

        ProductDTO dto = productMapper.toDTO(created);

        URI location = URI.create("/api/products" + dto.getId());

        return ResponseEntity
                .created(location)
                        .body(dto);


    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getActiveProducts() {

        List<ProductDTO> products = productService.getActiveProducts().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {

        productControllerValidator.validateDeactivateProduct(id);

        productService.deactivateProduct(id);

        return ResponseEntity.noContent().build();

    }

}

