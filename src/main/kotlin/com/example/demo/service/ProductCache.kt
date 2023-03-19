package com.example.demo.service

import com.example.demo.dto.Product
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class ProductCache {

    private val map: MutableMap<String, Product> = ConcurrentHashMap()



    fun saveProducts(products: List<Product>) {



//        products.forEach{p -> map.put(p.name, p)}


    }


}