package com.example.demo.service

import com.example.demo.client.ProductClient
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ProductService(
    val productClient : ProductClient,
    val productCache: ProductCache
) {

    @PostConstruct
    fun fetchProductsAndSaveInCache() {
        ProductClient.logger.info("Starting Products fetch")
        val products = productClient.fetchProducts()
        ProductClient.logger.info("Finished Products fetch [found=${products.size}]")


        products.forEach{p -> if (p.name == null) ProductClient.logger.info("Has null name $p" ) }

        productCache.saveProducts(products)

        ProductClient.logger.info("Finished Products caching")
    }






}