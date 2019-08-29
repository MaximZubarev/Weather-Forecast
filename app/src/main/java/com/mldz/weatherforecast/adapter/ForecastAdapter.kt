package com.mldz.weatherforecast.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mldz.weatherforecast.R
import com.mldz.weatherforecast.utils.getIcon
import java.text.SimpleDateFormat
import java.util.*
import com.mldz.weatherforecast.utils.model.List as ForecastList

/**
Created by Maxim Zubarev on 2019-08-24.
 */
class ForecastAdapter(private val items: List<ForecastList>):
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_adapter, parent, false))

    override fun getItemCount(): Int = items.size

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var dateTV = itemView.findViewById<TextView>(R.id.date)
        var tempTV = itemView.findViewById<TextView>(R.id.temp)
        var icon = itemView.findViewById<TextView>(R.id.image)
        var desc = itemView.findViewById<TextView>(R.id.description)

        fun bind(list: ForecastList) {
            val date = Date(list.dt * 1000)
            val format = SimpleDateFormat("E\nHH:mm", Locale.ENGLISH)
            dateTV?.text = format.format(date).toUpperCase()

            tempTV?.text = "${list.main.temp.toInt()}°"

            desc.text = list.weather[0].description.capitalize()

            val customFont = Typeface.createFromAsset(itemView.context.assets, "fonts/weather.ttf")
            icon.typeface = customFont
            icon.text = getIcon(
                list.weather[0].id,
                list.dt * 1000,
                list.dt * 1000,
                itemView.context
            )
        }
    }
}