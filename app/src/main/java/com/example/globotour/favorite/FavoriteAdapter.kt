package com.example.globotour.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.city.City
import com.example.globotour.R

class FavoriteAdapter(val context: Context,val cityList : List<City>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_item_favorite,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var city = cityList[position]
        holder.txt_city_name.text = city.name
        holder.image_city.setImageResource(city.imageId)
    }

    override fun getItemCount(): Int {
       return cityList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txt_city_name = itemView.findViewById<TextView>(R.id.txv_city_name)
        var image_city = itemView.findViewById<ImageView>(R.id.imv_city)
    }

}