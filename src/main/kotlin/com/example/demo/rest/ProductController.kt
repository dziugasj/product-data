package com.example.demo.rest

import com.example.demo.dto.Product
import com.example.demo.service.ProductCache
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ProductController(
    val productCache: ProductCache
) {

    @PutMapping("/product")
    fun updateProductStockLevel(
        @RequestParam name: String,
        @RequestParam stockLevel: Long
    ) {
        return productCache.updateProductStockLevel(name, stockLevel)
    }

    @GetMapping("/product")
    fun getProductByName(@RequestParam name: String): Product {
        return productCache.getProductByName(name)
    }

    @GetMapping("/products")
    fun getProductsByCategoryAndInStock(
        @RequestParam category: String,
        @RequestParam(required = false) inStock: Boolean?
    ): List<Product> {
        return productCache.getProductsByCategoryAndInStock(category, inStock)
    }

    @GetMapping("/all-products")
    fun getProducts(): List<Product> {
        return productCache.getProducts()
    }


}