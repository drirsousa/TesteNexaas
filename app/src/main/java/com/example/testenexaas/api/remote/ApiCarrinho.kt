package com.example.testenexaas.api.remote

import com.example.testenexaas.model.Item
import io.reactivex.Single
import retrofit2.http.GET

interface ApiCarrinho {

    @GET("myfreecomm/desafio-mobile-android/master/api/data.json")
    fun getAllItems() : Single<List<Item>>
}