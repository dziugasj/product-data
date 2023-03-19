package com.example.demo.rest

import com.example.demo.dto.Product
import com.example.demo.service.ProductCache
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ProductController(
    val productCache: ProductCache
) {

    @GetMapping("/product")
    fun getProductByName(@RequestParam name : String): Product {
        return productCache.getProduct(name)
    }

    @GetMapping("/products")
    fun getProducts(): List<Product> {
        return productCache.getProducts()
    }


}