package com.example.testenexaas.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_black)
        supportActionBar!!.title = "Cart"

        getAllMovies()
    }

    fun getAllMovies() {
        initViews()
        setupLoading()
        recyclerView.layoutManager = LinearLayoutManager(this)
        bindViews()

        viewModel.getAllMovies()
        viewModel.itemListResult.observe(this, Observer {
            adapter.updateList(it as MutableList<Item>)
        })
    }

    fun setupLoading() {
        viewModel.loadingResult.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
                cartGroup.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                cartGroup.visibility = View.VISIBLE
            }
        })
    }


    fun initViews() {
        viewModel = ViewModelProviders.of(this, factory).get(CartViewModel::class.java)
        recyclerView.adapter = adapter
    }

    fun bindViews() {
        viewModel.itemListResult.observe(this, Observer {
            var items = it as MutableList<Item>
            getTotals(items)
        })
    }

    fun getTotals(items: MutableList<Item>) {
        var subtotal = 0.0
        var total: Double
        var shippingTotal = 0.0
        var taxTotal = 0.0
        var itemsQuantity = 0

        for (item in items) {
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
        txtItemsQuantity.text =
            resources.getString(R.string.items_quantity, itemsQuantity.toString())
    }

    override fun onItemClicked(item: Item, positionItem: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("item", item)
        startActivityForResult(intent, 1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val item = data?.getSerializableExtra("itemTeste") as Item

            viewModel.itemListResult.observe(this, Observer {
                var items = it as MutableList<Item>
                items.remove(item)
                getTotals(items)
                adapter.notifyDataSetChanged()
            })
        }
    }
}