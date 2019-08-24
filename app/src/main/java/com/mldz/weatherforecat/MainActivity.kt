package com.mldz.weatherforecat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mldz.weatherforecat.mvp.model.MainActivityModel
import com.mldz.weatherforecat.mvp.presenter.MainActivityPresenter
import com.mldz.weatherforecat.mvp.view.MainActivityView
import com.mldz.weatherforecat.utils.model.Forecast
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
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

    override fun setData(forecast: Forecast) {
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
