package com.example.testenexaas.api.repository

import com.example.testenexaas.api.remote.RetrofitService.Companion.service
import com.example.testenexaas.model.Item
import io.reactivex.Single

class ItemRepository {
    fun getItems(): Single<List<Item>> = service.getAllItems()
}