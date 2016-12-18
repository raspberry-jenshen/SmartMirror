package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.CurrentWeatherWidgetData
import com.jenshen.smartmirror.util.toBaseFormat
import kotlinx.android.synthetic.main.view_weather.view.*

class CurrentWeatherView : CoordinatorLayout, Widget<CurrentWeatherWidgetData> {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(currentWeatherWidgetData: CurrentWeatherWidgetData) {
        Glide.with(context)
                .load(currentWeatherWidgetData.iconUrl)
                .into(this.weatherIcon)

        val response = currentWeatherWidgetData.weatherResponse
        val weather = response.weathersList.iterator().next()
        this.country.text = "${response.name}, ${response.sys.country}"
        this.lastTimeUpdate.text = response.date.toBaseFormat()
        this.pressure.text = context.getString(R.string.widget_weather_pressure) + ": ${response.temperatureConditions.pressure} hPa"
        this.humidity.text = context.getString(R.string.widget_weather_humidity) + ": ${response.temperatureConditions.humidity} %"
        this.description.text = weather.description
        this.temp.text = response.temperatureConditions.temp.toString()
    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_weather, this)
    }
}
