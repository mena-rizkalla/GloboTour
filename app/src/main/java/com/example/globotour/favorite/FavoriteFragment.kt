package com.example.globotour.fav

import com.example.globotour.favorite.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.R
import com.example.globotour.city.City
import com.example.globotour.city.VacationSpots
import java.util.*
import kotlin.collections.ArrayList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter :FavoriteAdapter
    private lateinit var favoriteCityList : ArrayList<City>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view: View?) {
        val context = requireContext()
         favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
         favoriteAdapter = FavoriteAdapter(context,favoriteCityList)

        recyclerView = view?.findViewById<RecyclerView>(R.id.favorite_recycler_view)!!
        recyclerView?.adapter=favoriteAdapter

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = linearLayoutManager

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
            or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            var fromPosition = viewHolder.adapterPosition
            var toPostioton = target.adapterPosition

            Collections.swap(favoriteCityList,fromPosition,toPostioton)
            recyclerView.adapter?.notifyItemMoved(fromPosition,toPostioton)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val deletedCity : City = favoriteCityList.get(position)

            deletedItem(position)
            updateCityList(deletedCity)

            Snackbar.make(recyclerView,"deleted",Snackbar.LENGTH_SHORT)
                .setAction("UNDO"){
                    undoDelete(position,deletedCity)
                }
                .show()

        }

    })

    private fun undoDelete(position: Int, deletedCity: City) {
        favoriteCityList.add(position,deletedCity)
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged(position,favoriteCityList.size)

        val cityList = VacationSpots.cityList!!
        val position = cityList.indexOf(deletedCity)
        cityList[position].isFavorite = false

    }

    private fun updateCityList(deletedCity: City) {
        val cityList = VacationSpots.cityList!!
        val position = cityList.indexOf(deletedCity)
        cityList[position].isFavorite = false
    }

    private fun deletedItem(position: Int) {

        favoriteCityList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position,favoriteCityList.size)
    }
}
