package com.example.PizzeriaApp.controllers;

import com.example.PizzeriaApp.controllers.validators.ProductControllerValidator;
import com.example.PizzeriaApp.controllers.requests.ProductCreateRequest;
import com.example.PizzeriaApp.dto.ProductDTO;
import com.example.PizzeriaApp.mappers.ProductMapper;
import com.example.PizzeriaApp.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addProduct(@RequestBody ProductCreateRequest productCreateRequest) {

        //boolean isValid = productControllerValidator.createProductValidator(productCreateRequest);

        productControllerValidator.createProductValidator(productCreateRequest);

        //if(!isValid)
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        //boolean success = productService.addProduct(productCreateRequest.getName(), productCreateRequest.getPrice());
        return ResponseEntity.ok("Продуктът е добавен");

    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getActiveProducts() {

        List<ProductDTO> products = productService.getActiveProducts().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);

    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id) {

        //boolean isValid = productControllerValidator.validateDeactivateProduct(id);

        productControllerValidator.validateDeactivateProduct(id);

        //if(!isValid)
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        return ResponseEntity.ok("Продуктът е деактивиран");

    }

}

