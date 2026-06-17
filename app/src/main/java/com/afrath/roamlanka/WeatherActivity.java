package com.afrath.roamlanka;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    EditText cityInput;
    TextView temperatureText, weatherCondition, locationText, deviceTempText;
    ImageView weatherIcon;
    Button refreshButton;
    SensorManager sensorManager;  // Sensor manager object
    Sensor tempSensor;   // Ambient temperature sensor
    SensorEventListener tempListener;  // Listener for sensor changes


    // Weather API key
    String API_KEY = BuildConfig.WEATHER_API_KEY;   // Stored securely inside BuildConfig
    String CITY_NAME = "Colombo";  // Default city name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        cityInput = findViewById(R.id.cityInput);
        temperatureText = findViewById(R.id.temperatureText);
        weatherCondition = findViewById(R.id.weatherCondition);
        locationText = findViewById(R.id.locationText);
        weatherIcon = findViewById(R.id.weatherIcon);
        refreshButton = findViewById(R.id.refreshButton);
        deviceTempText = findViewById(R.id.deviceTempText);


        // SENSOR MANAGER SETUP
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  // Get sensor service from Android system
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // Get ambient temperature sensor


        // Check if sensor is available
        if (tempSensor == null) {
            deviceTempText.setText("Ambient Temperature Sensor: Not available");
        } else { // Create sensor listener
            tempListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float tempC = event.values[0];  // Get temperature value
                    deviceTempText.setText("Device Temp: " + tempC + " °C");  // Show device temperature
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}  // Empty because accuracy changes are not needed
            };
        }


        refreshButton.setOnClickListener(view -> {
            String input = cityInput.getText().toString().trim(); // Get city name entered by user
            if (!input.isEmpty()) {
                CITY_NAME = input;  // Update city name
                getWeather();  // Automatically fetch weather on activity start
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            }
        });

        // When user presses keyboard "Enter"
        cityInput.setOnEditorActionListener((v, actionId, event) -> {

            String input = cityInput.getText().toString().trim();

            if (!input.isEmpty()) {CITY_NAME = input;getWeather();
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            }

            return true;
        });

        getWeather(); // Load default weather when app starts
    }


    // Start sensor listening when app is visible
    @Override
    protected void onResume() {
        super.onResume();
        // Register temperature sensor listener
        if (tempSensor != null)
            sensorManager.registerListener(tempListener, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // SENSOR LIFECYCLE

    // Stop sensor when app is paused (save battery)
    @Override
    protected void onPause() {
        super.onPause();
        // Stop listening to sensor
        if (tempSensor != null)
            sensorManager.unregisterListener(tempListener);
    }

    // WEATHER API CALL
    private void getWeather() {
        OkHttpClient client = new OkHttpClient();   // Create Http client
        // Build OpenWeather API URL
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + CITY_NAME
                + "&appid="
                + API_KEY
                + "&units=metric";
        // Create the HTTP request
        Request request = new Request.Builder().url(url).build();

        // Run network request in background thread
        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();  // Execute API request
                // Check if response body is empty
                if (response.body() == null) {
                    runOnUiThread(() ->
                            Toast.makeText(WeatherActivity.this, "No response from server", Toast.LENGTH_SHORT).show());
                    return;
                }
                // Convert response to string
                String responseData = response.body().string();

                JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();   // Parse JSON response
                String weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();   // Extract weather condition
                String temp = jsonObject.getAsJsonObject("main").get("temp").getAsString();  // Get temperature value

                // UPDATE UI ON MAIN THREAD
                runOnUiThread(() -> {
                    temperatureText.setText(temp + "°C");  // Show temperature
                    weatherCondition.setText(weather);  // Show weather condition
                    locationText.setText(CITY_NAME);  // Show city name

                    // Change icon based on weather type
                    switch (weather.toLowerCase()) {
                        case "rain":
                        case "drizzle":
                            weatherIcon.setImageResource(R.drawable.ic_rain);   // Rain icon
                            break;
                        case "clouds":
                            weatherIcon.setImageResource(R.drawable.ic_cloud);  // Cloud icon
                            break;
                        case "clear":
                            weatherIcon.setImageResource(R.drawable.ic_sun);  // Sun icon
                            break;
                        default:
                            weatherIcon.setImageResource(R.drawable.ic_cloud);   // Default icon
                            break;
                    }
                });

            } catch (IOException e) {
                e.printStackTrace(); // Print error in Logcat
                runOnUiThread(() ->
                        Toast.makeText(WeatherActivity.this, "Failed to fetch weather", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
