package com.example.demo.dto

data class Product(
    val name: String,
    val category: String?,
    val price: Double?,
    val description: String?,
    val stockLevel: Long
)

