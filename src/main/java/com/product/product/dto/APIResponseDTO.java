package com.product.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDTO<T> {
    private String status;
    private T result;
    private List<ErrorDTO> errors;
}
