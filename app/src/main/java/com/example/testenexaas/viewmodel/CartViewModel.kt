package com.example.testenexaas.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testenexaas.api.repository.ItemRepository
import com.example.testenexaas.api.repository.Repository
import com.example.testenexaas.model.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartViewModel(private val repository: Repository) : ViewModel() {

    private val itemList: MutableLiveData<List<Item>> = MutableLiveData()
    val itemListResult: LiveData<List<Item>> = itemList

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
    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CartViewModel(ItemRepository()) as T
        }
    }

}