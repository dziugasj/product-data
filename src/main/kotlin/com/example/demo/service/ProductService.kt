package com.example.demo.service

import com.example.demo.client.ProductClient
import com.example.demo.mapper.ProductMapper.mapProductDataToProduct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
    val productClient: ProductClient,
    val productCache: ProductCache
) {
    companion object {
        val logger = LoggerFactory.getLogger(ProductService::class.java)!!
    }

    fun clear() {
        productCache.clear()
    }

    fun fetchProductsAndSaveInCache() {
        logger.info("Starting fetch and save products")
        productCache.saveProducts(mapProductDataToProduct(productClient.fetchProducts()))
        logger.info("Finished fetch and save products")
    }
}