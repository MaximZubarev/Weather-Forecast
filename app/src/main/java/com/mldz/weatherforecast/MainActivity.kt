package com.mldz.weatherforecast

import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mldz.weatherforecast.adapter.ForecastAdapter
import com.mldz.weatherforecast.mvp.model.MainActivityModel
import com.mldz.weatherforecast.mvp.presenter.MainActivityPresenter
import com.mldz.weatherforecast.mvp.view.MainActivityView
import com.mldz.weatherforecast.utils.model.Forecast
import com.mldz.weatherforecast.utils.model.ForecastDays
import com.mldz.weatherforecast.utils.model.FullForecast
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.mldz.weatherforecast.utils.getIcon

class MainActivity : AppCompatActivity(), MainActivityView {
    private var presenter: MainActivityPresenter? = null

    private val location: String = "Moscow,ru"

    private var adapter: ForecastAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0.0f
        showProgressBar()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

        val height: Int = Resources.getSystem().displayMetrics.heightPixels * 2/3
        val lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height)
        forecastNow.layoutParams = lp

        presenter = MainActivityPresenter(MainActivityModel(this), CompositeDisposable())
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

        val customFont = Typeface.createFromAsset(assets, "fonts/weather.ttf")
        image.typeface = customFont
        image.text = getIcon(
            forecast.weather[0].id,
            forecast.sys.sunrise * 1000,
            forecast.sys.sunset * 1000,
            this
        )

        maxTemp.text = "Max temp: ${forecast.main.temp_max.toInt()}°"
        minTemp.text = "Min temp: ${forecast.main.temp_min.toInt()}°"

        description.text = forecast.weather[0].description.capitalize()

        pressure.text = "Pressure: ${forecast.main.pressure}hPa"
        humidity.text = "Humidity: ${forecast.main.humidity}%"

        wind.text = "Wind speed: ${forecast.wind.speed}m/s"
        cloud.text = "Cloudiness: ${forecast.clouds.all}%"

        val sunriseDate = Date(forecast.sys.sunrise * 1000)
        val sunsetDate = Date(forecast.sys.sunset * 1000)
        val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        sunrise.text = "Sunrise: ${format.format(sunriseDate)}"
        sunset.text = "Sunset: ${format.format(sunsetDate)}"
    }

    private fun setDataDays(forecast: ForecastDays) {
        adapter = ForecastAdapter(forecast.list)
        recyclerView.adapter = adapter
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun enableProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.change_city -> {
                val intent = Intent(this, ChangeCity::class.java)
                startActivityForResult(intent, 1)
                return true
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val city: String? = data?.getStringExtra("city")
        if (city != null)
            presenter?.onCreate(city)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showError(text: String) {
        Snackbar.make(mainLayout, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}
