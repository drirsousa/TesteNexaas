package com.example.testenexaas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testenexaas.R
import com.example.testenexaas.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class ItemCartAdapter(var itemList: MutableList<Item>, val listener: ClickAdapter) :

    RecyclerView.Adapter<ItemCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.onBind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(item)
        }
    }

    fun updateList(newList: MutableList<Item>) {
        this.itemList.removeAll(itemList)
        this.itemList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Item) {
            Picasso.get().load(item.image_url).into(itemView.imageItemView)
            itemView.nameItemView.text = item.name
            itemView.availabilityItemView.text = item.stock.toString()
            itemView.priceItemView.text = item.price.toString()
        }
    }
}