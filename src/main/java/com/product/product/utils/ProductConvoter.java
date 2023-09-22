package com.product.product.utils;

import com.product.product.dto.ProductRequestDTO;
import com.product.product.dto.ProductResponseDTO;
import com.product.product.model.Product;

public interface ProductConvoter {
    static ProductResponseDTO productToResponseConvoter(Product product) {
        ProductResponseDTO responseDTO = ProductResponseDTO.builder().id(product.getId()).name(product.getName())
                .price(product.getPrice()).description(product.getDescription())
                .productEmail(product.getProductEmail()).build();

        return responseDTO;
    }

    static Product requestToProductConvoter(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setProductEmail(productRequestDTO.getProductEmail());
        return product;
    }
}
