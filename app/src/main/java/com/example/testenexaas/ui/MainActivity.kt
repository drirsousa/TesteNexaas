package com.example.testenexaas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.example.testenexaas.ui.adapter.ItemCartAdapter
import com.example.testenexaas.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<Item>()
        val adapter = ItemCartAdapter(list)
        val viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        viewModel.getAllMovies()
        viewModel.itemListResult.observe(this, Observer {
            adapter.updateList(it as MutableList<Item>)
        })
    }
}