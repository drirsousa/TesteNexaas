package com.example.testenexaas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.example.testenexaas.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_total_price.*

class MainActivity() : AppCompatActivity(), ClickAdapter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<Item>()
        val adapter = ItemCartAdapter(list, this)
        val viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        viewModel.getAllMovies()
        viewModel.itemListResult.observe(this, Observer {
            adapter.updateList(it as MutableList<Item>)
        })

        viewModel.itemListResult.observe(this, Observer {
            var item = it as MutableList<Item>
            var subtotal = 0.0
            var total = 0.0
            var shippingTotal = 0.0
            var taxTotal = 0.0
            var itemsQuantity = 0

            for( i in item) {
                subtotal += i.price
                shippingTotal += i.shipping
                taxTotal += i.tax
                itemsQuantity += i.quantity
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
}