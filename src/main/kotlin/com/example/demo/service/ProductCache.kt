package com.example.demo.service

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

    fun clear() {
        map.clear()
    }

    fun saveProducts(products: List<Product>) {
        logger.info("Starting saving products [count=${products.size}]")
        products.forEach { p -> map[p.name] = p }
        logger.info("Finished saving products")
    }

    fun getProductByName(name: String): Product {
        return map[name] ?: throw ProductNotFoundException("Product not found by name '$name'")
    }

    fun getProductsByCategoryAndInStock(category: String, inStock: Boolean?): List<Product> {
        logger.info("Starting search by [category=$category inStock=$inStock]")

        val productsFound = map.values
            .filter { product -> product.filterByCategoryAndInStock(category, inStock) }
            .toList()

        logger.info("Finished search by [category=$category inStock=$inStock found=${productsFound.size}]")
        return productsFound
    }

    private fun Product.filterByCategoryAndInStock(category: String, inStock: Boolean?): Boolean {
        return this.category == category && (inStock == null || if (inStock) this.stockLevel > 0L else this.stockLevel == 0L)
    }

    fun updateProductStockLevel(name: String, newStockLevel: Long) {
        map.compute(name) { _, p ->
            updateStockLevelOrThrow(p, name, newStockLevel)
        }
    }

    private fun updateStockLevelOrThrow(product: Product?, name: String, newStockLevel: Long): Product {
        if (product == null)
            throw ProductNotFoundException("Product not found by name '$name'")

        return product.copy(stockLevel = newStockLevel)
    }
}