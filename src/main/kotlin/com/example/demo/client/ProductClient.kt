package com.example.demo.client

import com.example.demo.dto.Product
import com.example.demo.service.ProductCache
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct

@Service
class ProductClient(
    val productCache: ProductCache,
    restTemplateBuilder: RestTemplateBuilder
) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    companion object {
        val logger = LoggerFactory.getLogger(ProductClient::class.java)!!
    }

    @PostConstruct
    fun fetchProductsAndSaveInCache() {
        productCache.saveProducts(fetchProducts())
    }

    private fun fetchProducts(): List<Product> {
        return try {
            restTemplate.exchange(
                RequestEntity.get("http://localhost:4001/productdata").build(),
                typeRef<List<Product>>()
            ).body!!
        } catch (exception: RuntimeException) {
            logger.error("Failed to fetch product data. Intentionally failing startup of the application")
            throw exception
        }
    }

    private inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> =
        object : ParameterizedTypeReference<T>() {}

}