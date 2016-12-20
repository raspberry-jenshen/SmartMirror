package com.jenshen.smartmirror.ui.view.widget


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.jenshen.smartmirror.R
import com.jenshen.smartmirror.data.entity.widget.info.WeatherForecastWidgetData
import com.jenshen.smartmirror.util.toDayMonth
import kotlinx.android.synthetic.main.partial_weather_for_day.view.*
import kotlinx.android.synthetic.main.view_forecast.view.*

class WeatherForecastView : CoordinatorLayout, Widget<WeatherForecastWidgetData> {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun updateWidget(widgetData: WeatherForecastWidgetData) {
        val response = widgetData.weatherResponse
        this.country.text = "${response.city?.name}, ${response.city?.country}"

        while ( this.dayContainer.childCount != 0) {
            val view = this.dayContainer.getChildAt(0)
            (view.parent as ViewGroup).removeView(view)
        }

        response.weathersList
                ?.distinctBy { it.date?.day }
                ?.take(5)
                ?.forEach {
                    val weatherView = LayoutInflater.from(context).inflate(R.layout.partial_weather_for_day, null)
                    weatherView.layoutParams = LinearLayout.LayoutParams(0, MATCH_PARENT, 1f)
                    it.weathersList.let {
                        val weather = it?.iterator()?.next()
                        weatherView.description.text = weather?.description
                        Glide.with(context)
                                .load(weather?.iconUrl)
                                .into(weatherView.weatherIcon)
                    }

                    it.date.let { weatherView.lastTimeUpdate.text = it?.toDayMonth() }
                    it.temperatureConditions?.let {
                        weatherView.pressure.text = context.getString(R.string.widget_weather_pressure) + ": ${it.pressure} hPa"
                        weatherView.humidity.text = context.getString(R.string.widget_weather_humidity) + ": ${it.humidity} %"
                        weatherView.temp.text = it.temp.toString()
                    }
                    this.dayContainer.addView(weatherView)
                }
    }

    /* private methods */

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_forecast, this)
    }
}
