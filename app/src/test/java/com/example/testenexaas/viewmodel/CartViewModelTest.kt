package com.example.tes

import com.example.testenexaas.viewmodel.CartViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testenexaas.api.repository.Repository
import com.example.testenexaas.model.Item
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.mockito.*
import org.mockito.Mockito.*

class CartViewModelTest {

    @Rule
    @JvmField
    val rule : TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repositoryMock: Repository

    @Mock
    lateinit var listItemsMock: List<Item>

    lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline()})
        MockitoAnnotations.initMocks(this)
        viewModel = instantiateViewModel()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repositoryMock, listItemsMock)
    }

    @Test
    fun test_getAllMovies_WhenSuccess() {
        //Given
        val viewModelSpy = spy(viewModel)
        val listItems = Single.just(listOf(Item("", 0, 0, "", 0.0, 0.0, 0.0, "")))
        var items: List<Item> = listOf()
        listItems.subscribe { result -> items = result }

        `when`(repositoryMock.getItems()).thenReturn(listItems)
        doNothing().`when`(viewModelSpy).setItemList(items)
        doNothing().`when`(viewModelSpy).setLoading(ArgumentMatchers.anyBoolean())

        //Act
        viewModelSpy.getAllMovies()

        //Assert
        verify(viewModelSpy, times(1)).getAllMovies()
        verify(viewModelSpy, never()).logError(ArgumentMatchers.anyString())
        verify(repositoryMock, times(1)).getItems()
        verify(viewModelSpy, times(1)).setLoading(true)
        verify(viewModelSpy, times(1)).setLoading(false)
        verify(viewModelSpy, times(1)).setItemList(items)
    }

    @Test
    fun test_getAllMovies_WhenError() {
        //Given
        val errorMessage = "Error Message"
        val throwable = Throwable(errorMessage)
        val viewModelSpy = spy(viewModel)

        `when`(repositoryMock.getItems()).thenReturn(Single.error(throwable))
        doNothing().`when`(viewModelSpy).setLoading(ArgumentMatchers.anyBoolean())
        doNothing().`when`(viewModelSpy).logError(errorMessage)

        //Act
        viewModelSpy.getAllMovies()

        //Assert
        verify(viewModelSpy, times(1)).getAllMovies()
        verify(viewModelSpy, never()).setItemList(ArgumentMatchers.any())
        verify(repositoryMock, times(1)).getItems()
        verify(viewModelSpy, times(1)).setLoading(true)
        verify(viewModelSpy, times(1)).setLoading(false)
        verify(viewModelSpy, times(1)).logError(errorMessage)
    }

    @Test
    fun test_setItemList() {
        //Given

        //Act
        viewModel.setItemList(listItemsMock)

        //Assert
        assertEquals(listItemsMock, viewModel.itemListResult.value)
    }

    @Test
    fun test_setLoading() {
        //Given

        //Act
        viewModel.setLoading(true)

        //Assert
        assertTrue(viewModel.loadingResult.value ?: false)
    }

    private fun instantiateViewModel(): CartViewModel {
        val viewModel = CartViewModel(repositoryMock)
        return viewModel
    }
}
