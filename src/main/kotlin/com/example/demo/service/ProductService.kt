package com.example.demo.service

import com.example.demo.client.ProductClient
import com.example.demo.mapper.ProductMapper.mapProductDataToProduct
import org.springframework.stereotype.Service

@Service
class ProductService(
    val productClient: ProductClient,
    val productCache: ProductCache
) {

    fun fetchProductsAndSaveInCache() {
        productCache.saveProducts(mapProductDataToProduct(productClient.fetchProducts()))
    }
}