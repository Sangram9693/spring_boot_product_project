package com.product.product.dto;

import com.product.product.utils.Message;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    @Size(min = 3, message = Message.MIN_LENGTH + 3)
    private String name;

    @Size(min = 3, message = Message.MIN_LENGTH + 3)
    private String description;

    @Min(value = 1, message = Message.PRICE)
    private int price;

    @Email
    private String productEmail;
}
