package com.example.productservice.exception.handler;

import com.example.productservice.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestControllerExceptionHandlerTest {

    private static final String ERROR_MESSAGE = "Product not found for id=1";

    @Test
    public void restControllerExceptionHandlerTest() {
        RestControllerExceptionHandler handler = new RestControllerExceptionHandler();

        ResponseEntity<ApiError> responseEntity = handler.notFound(new EntityNotFoundException(ERROR_MESSAGE));

        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(ERROR_MESSAGE);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
