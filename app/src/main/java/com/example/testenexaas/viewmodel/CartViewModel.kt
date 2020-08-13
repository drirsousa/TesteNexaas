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
    val itemListResult: MutableLiveData<List<Item>> = itemList

    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    val loadingResult: LiveData<Boolean> = loading

    fun getAllMovies() {
        repository.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading(true) }
            .doAfterTerminate { setLoading(false) }
            .subscribe({
                setItemList(it)
            }, { throwable ->
                Throwable(throwable)
                logError(throwable.message)
            })
    }

    fun setItemList(it: List<Item>?) {
        itemList.value = it
    }

    fun setLoading(value: Boolean) {
        loading.value = value
    }

    fun logError(message: String?) {
        Log.i("LOG", "erro $message")
    }

    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CartViewModel(ItemRepository()) as T
        }
    }
}