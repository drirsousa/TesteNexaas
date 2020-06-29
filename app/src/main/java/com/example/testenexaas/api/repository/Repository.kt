package com.example.testenexaas.api.repository

import com.example.testenexaas.model.Item
import io.reactivex.Single

/**
 * Created by arieloliveira on 28/06/20 for TesteNexaas.
 */
interface Repository {
    fun getItems(): Single<List<Item>>
}