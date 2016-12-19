package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.CurrentWeatherWidgetData
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

    override fun updateWidget(widgetData: CurrentWeatherWidgetData) {
        val response = widgetData.weatherResponse
        response.weathersList.let {
            val weather = it?.iterator()?.next()
            this.description.text = weather?.description
            Glide.with(context)
                    .load(weather?.iconUrl)
                    .into(this.weatherIcon)
        }
        response.date?.let { this.lastTimeUpdate.text = it.toString() }
        this.country.text = "${response.name}, ${response.sys?.country}"

        response.temperatureConditions?.let {
            this.pressure.text = context.getString(R.string.widget_weather_pressure) + ": ${it.pressure} hPa"
            this.humidity.text = context.getString(R.string.widget_weather_humidity) + ": ${it.humidity} %"
            this.temp.text = it.temp.toString()
        }
    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_weather, this)
    }
}
