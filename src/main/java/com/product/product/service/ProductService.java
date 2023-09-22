package com.product.product.service;

import com.product.product.dto.ProductRequestDTO;
import com.product.product.dto.ProductResponseDTO;
import com.product.product.exception.ProductCreateException;
import com.product.product.exception.ProductNotFound;
import com.product.product.exception.SomethingWentWrong;
import com.product.product.model.Product;
import com.product.product.repository.IProductRepository;
import com.product.product.utils.Message;
import com.product.product.utils.ProductConvoter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private IProductRepository iProductRepository;

    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) throws ProductCreateException {
        log.info("ProductService.create() -> product is creating");
        ProductResponseDTO productResponseDTO;
        try {
            Product product = ProductConvoter.requestToProductConvoter(productRequestDTO);
            Product savedProduct = iProductRepository.save(product);
            log.debug("ProductService.create() -> product created");
            productResponseDTO = ProductConvoter.productToResponseConvoter(savedProduct);
        } catch (Exception e) {
            log.debug("ProductService.create() -> product created");
            throw new ProductCreateException(Message.CREATE_ERROR);
        }

        log.info("ProductService.create() -> method end");
        return productResponseDTO;
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) throws Exception {
        log.info("ProductService.update() -> product is creating");
        ProductResponseDTO productResponseDTO;
        try{
            Optional<Product> optionalProduct = iProductRepository.findById(id);
            if(optionalProduct.isPresent()) {
                Product product = ProductConvoter.requestToProductConvoter(productRequestDTO);
                product.setId(id);
                Product savedProduct = iProductRepository.save(product);
                log.debug("ProductService.update() -> product created");

                productResponseDTO = ProductConvoter.productToResponseConvoter(savedProduct);
            } else {
                log.error("ProductService.update() -> Product not found");
                throw new ProductNotFound(Message.PRODUCT_NOT_FOUND);
            }

        } catch (Exception e) {
            log.debug("ProductService.update() -> product created");
            throw new ProductCreateException(Message.CREATE_ERROR);
        }

        log.info("ProductService.update() -> method end");
        return productResponseDTO;
    }

    public ProductResponseDTO findById(Long id) throws ProductNotFound {
        Optional<Product> optionalProduct = iProductRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new ProductNotFound(Message.PRODUCT_NOT_FOUND);
        }

        return ProductConvoter.productToResponseConvoter(optionalProduct.get());
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> productList = iProductRepository.findAll();
        List<ProductResponseDTO> response = new ArrayList<>();
        productList.forEach(
                (e) -> {
                    response.add(ProductConvoter.productToResponseConvoter(e));
                }
        );
        return response;
    }

    public void deleteById(Long id) throws ProductNotFound, SomethingWentWrong {
        Optional<Product> optionalProduct = iProductRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new ProductNotFound(Message.PRODUCT_NOT_FOUND);
        }

        try {
            iProductRepository.deleteById(id);
        } catch (Exception e) {
            log.error("ProductService.deleteById() -> Error: {}", e);
            throw new SomethingWentWrong(Message.SOMETHING_WRONG);
        }
    }
}
