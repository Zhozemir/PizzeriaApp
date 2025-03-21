package com.example.PizzeriaApp.controllers.requests;

import java.util.List;

public class OrderCreateRequest {

    private List<Long> productIds;

    public OrderCreateRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

}
