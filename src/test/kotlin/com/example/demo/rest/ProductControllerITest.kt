package com.example.demo.rest

import com.example.demo.client.ProductClient
import com.example.demo.client.dto.ProductData
import com.example.demo.service.ProductService
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerITest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productService: ProductService

    @MockBean
    lateinit var productClient: ProductClient

    @BeforeEach
    fun beforeEach() {
        productService.clear()
    }

    @Test
    fun `Given no products When fetching by Category Then return empty json array`() {
        doReturn(createEmptyProductDataStub()).`when`(productClient).fetchProducts()
        productService.fetchProductsAndSaveInCache()

        mockMvc.get("/api/v1/products/byCategory?category=Clothing") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { string("[]") }
        }
    }

    @Test
    fun `Given two products When fetching by Category Then both returned`() {
        val jeansName = "Levis jeans"
        val beltName = "Armani belt"
        doReturn(createProductDataStub(jeansName, beltName)).`when`(productClient).fetchProducts()
        productService.fetchProductsAndSaveInCache()

        mockMvc.get("/api/v1/products/byCategory?category=Clothing") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$[*].name", containsInAnyOrder(jeansName, beltName))
        }
    }

    @Test
    fun `Given jeans and belt When fetching by belt name Then jeans returned`() {
        val jeansName = "Levis jeans"
        val beltName = "Armani"
        doReturn(createProductDataStub(beltName, jeansName)).`when`(productClient).fetchProducts()
        productService.fetchProductsAndSaveInCache()

        mockMvc.get("/api/v1/products/byName?name=$beltName") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.name", containsString(beltName))
        }
    }

    private fun createEmptyProductDataStub(): List<ProductData> {
        return emptyList()
    }

    private fun createProductDataStub(vararg names: String): List<ProductData> {
        return names.map { n -> createProductStub(n) }
    }

    private fun createProductStub(name: String): ProductData {
        return ProductData(
            name = name,
            category = "Clothing",
            price = 99.99,
            description = "Some clothes"
        )
    }
}