package com.mldz.weatherforecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mldz.weatherforecast.mvp.model.MainActivityModel
import com.mldz.weatherforecast.mvp.presenter.MainActivityPresenter
import com.mldz.weatherforecast.mvp.view.MainActivityView
import com.mldz.weatherforecast.utils.model.Forecast
import com.mldz.weatherforecast.utils.model.ForecastDays
import com.mldz.weatherforecast.utils.model.FullForecast
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityView {
    private var presenter: MainActivityPresenter? = null

    private val location: String = "Moscow,ru"
    private val ICON: String = "http://openweathermap.org/img/wn/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0.0f
        showProgressBar()

        presenter = MainActivityPresenter(MainActivityModel(), CompositeDisposable())
        presenter?.bindView(this)
        presenter?.onCreate(location)
    }

    override fun setData(fullForecast: FullForecast) {
        setDataNow(fullForecast.forecast)
        setDataDays(fullForecast.forecastDays)
    }

    private fun setDataNow(forecast: Forecast) {
        city.text = forecast.name
        temp.text = "${forecast.main.temp.toInt()}°"
        Picasso.get().load(ICON + forecast.weather[0].icon + "@2x.png").into(image)

        maxTemp.text = "Max temp: ${forecast.main.temp_max.toInt()}°"
        minTemp.text = "Min temp: ${forecast.main.temp_min.toInt()}°"

        description.text = forecast.weather[0].main

        pressure.text = "Pressure: ${forecast.main.pressure}"
        humidity.text = "Humidity: ${forecast.main.humidity}"

        wind.text = "Wind speed: ${forecast.wind.speed}m/s"
        cloud.text = "Clouds: ${forecast.clouds.all}"

        val sunriseDate = Date(forecast.sys.sunrise * 1000)
        val sunsetDate = Date(forecast.sys.sunset * 1000)
        val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        sunrise.text = "Sunrise: ${format.format(sunriseDate)}"
        sunset.text = "Sunset: ${format.format(sunsetDate)}"
    }

    private fun setDataDays(forecast: ForecastDays) {

    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun enableProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}
