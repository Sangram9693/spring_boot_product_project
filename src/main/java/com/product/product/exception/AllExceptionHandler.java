package com.product.product.exception;

import com.product.product.dto.APIResponseDTO;
import com.product.product.dto.ErrorDTO;
import com.product.product.utils.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        APIResponseDTO<?> serviceResponse = new APIResponseDTO<>();
        List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });
        serviceResponse.setStatus(Message.FAILED);
        serviceResponse.setErrors(errors);
        return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductCreateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponseDTO<?> handleProductCreateError(ProductCreateException exception) {
        List<ErrorDTO> list = new ArrayList<>();
        list.add(new ErrorDTO("", exception.getMessage()));
        APIResponseDTO<?> serviceResponse = new APIResponseDTO<>();
        serviceResponse.setStatus(Message.FAILED);
        serviceResponse.setErrors(list);
        return serviceResponse;
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponseDTO<?> handaleProductNotFound(ProductNotFound ex) {
        List<ErrorDTO> list = new ArrayList<>();
        list.add(new ErrorDTO("", ex.getMessage()));

        APIResponseDTO apiResponseDTO = new APIResponseDTO();
        apiResponseDTO.setErrors(list);
        apiResponseDTO.setStatus(Message.PRODUCT_NOT_FOUND);
        return apiResponseDTO;
    }

    @ExceptionHandler(SomethingWentWrong.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponseDTO<?> handleSomethingWentWrong(SomethingWentWrong err) {
        List<ErrorDTO> errorDTOS = new ArrayList<>();
        errorDTOS.add(new ErrorDTO("", err.getMessage()));
        APIResponseDTO apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setErrors(errorDTOS);
        apiResponseDTO.setStatus(Message.SOMETHING_WRONG);
        return apiResponseDTO;
    }
}
