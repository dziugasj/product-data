package com.example.demo.rest

import com.example.demo.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(exception: ProductNotFoundException): ResponseEntity<String> {
        exception.printStackTrace()
        return ResponseEntity("", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<String> {
        exception.printStackTrace()
        return ResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}