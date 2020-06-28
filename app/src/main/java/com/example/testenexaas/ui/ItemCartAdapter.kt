package com.example.testenexaas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class ItemCartAdapter(var itemList: MutableList<Item>) :

    RecyclerView.Adapter<ItemCartAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = itemList[position]
        holder.onBind(movie)
    }

    fun updateList(newList: MutableList<Item>) {
        this.itemList.removeAll(itemList)
        this.itemList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Item) {
            Picasso.get().load(item.image_url).into(itemView.imageItemView)
            itemView.nameDetail.text = item.name
            itemView.availabilityDetail.text = item.stock.toString()
            itemView.priceItemView.text = item.price.toString()
        }
    }
}