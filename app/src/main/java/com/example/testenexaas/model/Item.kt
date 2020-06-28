package com.example.testenexaas.model

import java.io.Serializable

data class Item(
    val name: String,
    val quantity: Int,
    val stock: Int,
    val image_url: String,
    val price: Double,
    val tax: Double,
    val shipping: Double,
    val description: String
) : Serializable