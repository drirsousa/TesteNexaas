package com.example.testenexaas.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testenexaas.api.repository.Repository
import com.example.testenexaas.model.Item
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<Repository>()
    private val onDataLoadedObserver = mockk<Observer<List<Item>>>()

    @Test
    fun onInit_loadProductsByCategory_success() {

        val viewModel = instantiateViewModel()
        val mockedList = List<Item>()

        every { repository.getItems() } returns mockedList

        viewModel.getAllMovies()

        verify { repository.getItems() }
        verify { onDataLoadedObserver.onChanged(mockedList) }
    }

    private fun instantiateViewModel(): CartViewModel {
        val viewModel = CartViewModel(repository)
        viewModel.itemListResult.observeForever(onDataLoadedObserver)
        return viewModel
    }
}
