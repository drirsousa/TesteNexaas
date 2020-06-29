package com.example.testenexaas.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.example.testenexaas.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_total_price.*

class MainActivity() : AppCompatActivity(), ClickAdapter {

    private lateinit var viewModel: CartViewModel
    private val factory = CartViewModel.Factory()
    val list = mutableListOf<Item>()
    val adapter = ItemCartAdapter(list, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getAllMovies()
        viewModel.itemListResult.observe(this, Observer {
            adapter.updateList(it as MutableList<Item>)
        })
        bindViews()
    }

    fun initViews(){
        viewModel = ViewModelProviders.of(this, factory).get(CartViewModel::class.java)
        recyclerView.adapter = adapter
    }

    fun bindViews(){
        viewModel.itemListResult.observe(this, Observer {
            var items = it as MutableList<Item>
            var subtotal = 0.0
            var total : Double
            var shippingTotal = 0.0
            var taxTotal = 0.0
            var itemsQuantity = 0

            for( item in items) {
                subtotal += item.price
                shippingTotal += item.shipping
                taxTotal += item.tax
                itemsQuantity += item.quantity
            }

            total = subtotal + shippingTotal + taxTotal

            subtotalItem.text = subtotal.toString()
            totalItem.text = total.toString()
            shippingItem.text = shippingTotal.toString()
            taxItem.text = taxTotal.toString()
            txtItemsQuantity.text = resources.getString(R.string.items_quantity, itemsQuantity.toString())
        })
    }

    override fun onItemClicked(item: Item) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("item", item)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}