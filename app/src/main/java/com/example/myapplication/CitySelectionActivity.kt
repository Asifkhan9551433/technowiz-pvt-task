package com.example.myapplication
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class CitySelectionActivity : AppCompatActivity() {
    private lateinit var cityEditText: EditText
    private lateinit var getWeatherButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var progressBar: ProgressBar
    lateinit var temperatureTextView: TextView
    private val allowedCities = listOf("rawalpindi", "islamabad")
    private val apiKey = "YOUR_OPENWEATHERMAP_API_KEY" // Replace with your API Key/////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)
        cityEditText = findViewById(R.id.editTextCity)
        getWeatherButton = findViewById(R.id.buttonGetWeather)
        resultTextView = findViewById(R.id.textViewResult)
        progressBar = findViewById(R.id.progressBar)

        getWeatherButton.setOnClickListener {
            val cityInput = cityEditText.text.toString().trim().toLowerCase()
            if (!allowedCities.contains(cityInput)) {
                Toast.makeText(this, "This city is not allowed.", Toast.LENGTH_SHORT).show()
                resultTextView.text = ""
            } else {
                getTemperature(cityInput)
            }
        }
    }
//
//    private fun fetchWeather(cityName: String) {
//        progressBar.visibility = ProgressBar.VISIBLE
//        resultTextView.text = ""
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response =
//                    URL("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey&units=metric").readText()
//                val jsonObj = JSONObject(response)
//                val temp = jsonObj.getJSONObject("main").getDouble("temp")
//                val city = jsonObj.getString("name")
//
//                withContext(Dispatchers.Main) {
//                    progressBar.visibility = ProgressBar.GONE
//                    resultTextView.text = "City: $city\nTemperature: $temp °C"
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    progressBar.visibility = ProgressBar.GONE
//                    Toast.makeText(
//                        this@CitySelectionActivity,
//                        "Error fetching weather.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }

        private fun getTemperature(city: String) {
            val url = "https://wttr.in/${city}?format=%t"
            val call = RetrofitClient.instance.getTemperature(url)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val temp = response.body()?.trim()
                        Log.i("MainActivity", "Temperature: $temp")
                        resultTextView.text = "Temperature in $city: $temp"
                    } else {
                        resultTextView.text = "Failed: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    resultTextView.text = "Error: ${t.message}"
                }
            })
        }
    }

