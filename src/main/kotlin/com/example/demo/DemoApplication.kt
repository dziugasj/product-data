package com.example.demo

import com.example.demo.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class DemoApplication(
    val productService: ProductService
) {

    companion object {
        val logger = LoggerFactory.getLogger(DemoApplication::class.java)!!
    }

    @PostConstruct
    fun preStartupActions() {
        try {
            productService.fetchProductsAndSaveInCache()
        } catch (exception: RuntimeException) {
            logger.error("Pre startup actions failed. Intentionally failing startup of the application")
            throw exception
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}