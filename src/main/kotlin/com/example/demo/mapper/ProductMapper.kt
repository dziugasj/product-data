package com.example.demo.mapper

import com.example.demo.client.dto.ProductData
import com.example.demo.dto.Product
import org.slf4j.LoggerFactory

object ProductMapper {

    private val logger = LoggerFactory.getLogger(ProductMapper::class.java)!!

    private const val INITIAL_STOCK_LEVEL : Long = 0

    fun mapProductDataToProduct(products: List<ProductData>): List<Product> {
        return products
            .filter(::filterNullName)
            .map(::mapToProduct)
    }

    private fun filterNullName(productData: ProductData): Boolean {
        return if (productData.name == null) {
            logger.warn("Product will be not added to cache since it has NULL name [product=$productData]")
            false
        } else {
            true
        }
    }

    private fun mapToProduct(productData: ProductData): Product {
        return Product(
            name = productData.name!!,
            category = productData.category,
            price = productData.price,
            description = productData.description,
            stockLevel = INITIAL_STOCK_LEVEL
        )
    }
}