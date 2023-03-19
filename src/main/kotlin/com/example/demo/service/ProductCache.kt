package com.example.demo.service

import com.example.demo.dto.Product
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ProductCache {

    private val map: MutableMap<String, Product> = ConcurrentHashMap()

    companion object {
        val logger = LoggerFactory.getLogger(ProductCache::class.java)!!
    }

    fun saveProducts(products: List<Product>) {
        products.forEach(::addProductIfPossible)
    }

    private fun addProductIfPossible(product: Product) {
        if (product.name != null) {
            map[product.name] = product
        } else {
            logger.warn("Product will be not added to cache since it has NULL name [product=$product]")
        }
    }
}