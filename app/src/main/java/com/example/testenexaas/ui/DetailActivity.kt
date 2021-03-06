package com.example.testenexaas.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity() : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getSerializableExtra("item") as Item

        if (item != null){
            Picasso.get().load(item.image_url).into(imageViewDetail)
            nameDetail.text = item.name
            availabilityDetail.text = item.stock.toString()
            descriptionDetail.text = item.description
            priceDetail.text = item.price.toString()
        }

        buttonRemove.setOnClickListener {
            val intent = Intent()
            intent.putExtra("itemTeste", item)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }
}
