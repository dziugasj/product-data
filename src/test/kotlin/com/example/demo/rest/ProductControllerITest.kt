package com.example.demo.rest

import com.example.demo.client.ProductClient
import com.example.demo.client.dto.ProductData
import com.fasterxml.jackson.databind.ObjectMapper
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
internal class ProductControllerITest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var productClient: ProductClient

    companion object {
        const val BASE_URL = "/api/v1"
    }

    @Test
    fun `Given no products When `() {

        doReturn(createEmptyProductDataStub()).`when`(productClient).fetchProducts()



        mockMvc.get("$BASE_URL/all-products") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { string("[]") }
        }
    }

    private fun createEmptyProductDataStub(): List<ProductData> {
        return emptyList()
    }


}