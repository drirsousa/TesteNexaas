package com.example.testenexaas.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testenexaas.api.repository.ItemRepository
import com.example.testenexaas.model.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartViewModel : ViewModel() {

    private val itemList: MutableLiveData<List<Item>> = MutableLiveData()
    private val repository = ItemRepository()

    val itemListResult: MutableLiveData<List<Item>> = itemList

    fun getAllMovies() {
        repository.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itemList.value = it
            }, { throwable ->
                Log.i("LOG", "erro" + throwable.message)
            })
    }
}