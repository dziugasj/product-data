package com.example.demo.client

import com.example.demo.dto.Product
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ProductClient(
    restTemplateBuilder: RestTemplateBuilder
) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    companion object {
        val logger = LoggerFactory.getLogger(ProductClient::class.java)!!
    }

    fun fetchProducts(): List<Product> {
        return try {
            logger.info("Starting Products fetch")

            val products = restTemplate.exchange(
                RequestEntity.get("http://localhost:4001/productdata").build(),
                typeRef<List<Product>>()
            ).body!!

            logger.info("Finished Products fetch [found=${products.size}]")
            products
        } catch (exception: RuntimeException) {
            logger.error("Failed to fetch product data")
            throw exception
        }
    }

    private inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> =
        object : ParameterizedTypeReference<T>() {}
}