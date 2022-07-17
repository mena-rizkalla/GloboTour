package com.example.globotour.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.R

class CityAdapter(val context : Context,val cityList : ArrayList<City>) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = LayoutInflater.from(context).inflate(R.layout.list_item_city,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var city = cityList[position]
        holder.txt_city_name.text = city.name
        holder.image_city.setImageResource(city.imageId)

        if (city.isFavorite){
            holder.iamge_favorite.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_filled,null))
        }else{
            holder.iamge_favorite.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_bordered,null))
        }

        holder.image_delete.setOnClickListener{
            cityList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cityList.size)
            VacationSpots.favoriteCityList.remove(city!!)
        }


        holder.iamge_favorite.setOnClickListener{
             city?.isFavorite = !(city?.isFavorite!!)
            if (city?.isFavorite!!){
                holder.iamge_favorite.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_filled,null))
                VacationSpots.favoriteCityList.add(city)
            }else{
                holder.iamge_favorite.setImageDrawable(ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_bordered,null))
                VacationSpots.favoriteCityList.remove(city)
            }

        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var txt_city_name = itemView.findViewById<TextView>(R.id.txv_city_name)
         var image_city = itemView.findViewById<ImageView>(R.id.imv_city)
         var image_delete = itemView.findViewById<ImageView>(R.id.imv_delete)
         var iamge_favorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        //private val icFavoriteFilledImage = ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_filled,null)
        //private val icFavoriteBorderedImage = ResourcesCompat.getDrawable(context.resources,R.drawable.ic_favorite_bordered,null)



    }
}