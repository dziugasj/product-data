package com.example.demo.service

import com.example.demo.client.dto.ProductData
import com.example.demo.dto.Product
import com.example.demo.exception.ProductNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ProductCache {

    private val map: MutableMap<String, Product> = ConcurrentHashMap()

    companion object {
        val logger = LoggerFactory.getLogger(ProductCache::class.java)!!
    }

    fun saveProducts(productData: List<Product>) {
        productData.forEach(::addProductIfPossible)
    }

    private fun addProductIfPossible(productData: Product) {
        if (productData.name != null) {
            map[productData.name] = productData
        } else {
            logger.warn("Product will be not added to cache since it has NULL name [product=$productData]")
        }
    }

    fun getProducts(): List<Product> {
        return map.values.toList()
    }

    fun getProductByName(name: String): Product {
        return map[name] ?: throw ProductNotFoundException("Product not found by name '$name'")
    }

    fun getProductsByCategoryAndInStock(category: String, inStock: Boolean?): List<Product> {
        logger.info("Starting search by [category=$category inStock=$inStock]")

        val productsFound = map.values
            .filter { product -> product.category == category }
            .toList()

        logger.info("Finished search by [category=$category inStock=$inStock found=${productsFound.size}]")
        return productsFound
    }
}