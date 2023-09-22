package com.product.product.dto;

import com.product.product.utils.Message;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductResponseDTO{
    private Long id;
    private String name;
    private String description;
    private int price;
    private String productEmail;
}
