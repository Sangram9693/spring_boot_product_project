package com.product.product.controller;

import com.product.product.dto.ProductRequestDTO;
import com.product.product.dto.ProductResponseDTO;
import com.product.product.exception.ProductCreateException;
import com.product.product.exception.ProductNotFound;
import com.product.product.exception.SomethingWentWrong;
import com.product.product.service.ProductService;
import com.product.product.utils.Message;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO productRequestDTO) throws ProductCreateException {
        log.info("ProductController:create -> Creating product");
        ProductResponseDTO productResponseDTO = productService.create(productRequestDTO);
        log.debug("ProductController:create -> Successfully created with product id = {}", productResponseDTO.getId());

        log.info("ProductController:create -> end of method");
        return productResponseDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequestDTO) throws Exception {
        log.info("ProductController:update -> Creating product");
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);
        log.debug("ProductController:update -> Successfully updated with product id = {}", productResponseDTO.getId());

        log.info("ProductController:update -> end of method");
        return productResponseDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getById(@PathVariable Long id) throws ProductNotFound {
        return productService.findById(id);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAll() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws SomethingWentWrong, ProductNotFound {
        productService.deleteById(id);
        return Message.DELETE_SUCCESS;
    }
}
