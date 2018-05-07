package com.example.dmitry.weathernew.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.dmitry.weathernew.MyApp
import com.example.dmitry.weathernew.R
import com.example.dmitry.weathernew.network.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers

class CityAutoCompleteAdapter(private val context: Context) : BaseAdapter(), Filterable {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val resultList = ArrayList<String>()

    override fun getItem(position: Int) = resultList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = resultList.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_city_auto_complete, parent, false)
            vh = ListRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = resultList.get(index = position)
        return view
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.itemTextViewCity) as TextView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null) {
                    resultList.clear()
                    resultList.addAll(searchCityByName(constraint.toString()))
                    filterResults.values = resultList
                    filterResults.count = resultList.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) = when {
                results?.count ?: -1 > 0 -> notifyDataSetChanged()
                else -> notifyDataSetInvalidated()
            }
        }
    }

    private fun searchCityByName(cityName: String): List<String> {
        val cityList = ArrayList<String>()
        WeatherApiService.create().getWeatherDataByCityName(cityName, context.getString(R.string.open_weather_api_key))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    cityList.add(result.name + "," + result.sys.country)
                    MyApp.currentWeatherData = result
                }, { error ->
                    error.printStackTrace()
                })
        return cityList
    }
}
