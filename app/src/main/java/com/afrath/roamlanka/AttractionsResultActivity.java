package com.afrath.roamlanka;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttractionsResultActivity extends AppCompatActivity {

    TextView textHeader;
    Spinner spinnerCityDropdown;
    RecyclerView recyclerAttractions;

    String selectedProvince;
    String selectedDistrict;

    Map<String, List<Attraction>> cityAttractionsMap;  // Map for storing attractions by city
    Map<String, Map<String, List<String>>> dataMap;   // Stores province → district → city hierarchy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_result);

        textHeader = findViewById(R.id.textHeader);
        spinnerCityDropdown = findViewById(R.id.spinnerCityDropdown);
        recyclerAttractions = findViewById(R.id.recyclerAttractions);
        // Display RecyclerView items vertically
        recyclerAttractions.setLayoutManager(new LinearLayoutManager(this));


        dataMap = DataProvider.getDataMap();     // Load province, district and city data
        cityAttractionsMap = DataProvider.getCityAttractionsMap();    // Load attraction data for cities

        // Receive selected data from DiscoverActivity
        selectedProvince = getIntent().getStringExtra("selected_province");
        selectedDistrict = getIntent().getStringExtra("selected_district");
        String selectedCity = getIntent().getStringExtra("selected_city");

        List<String> citiesToShow = new ArrayList<>();    // Store cities to show in spinner


        // Get districts for selected province
        Map<String, List<String>> districts = dataMap.get(selectedProvince);
        // Check if province exists
        if (districts != null) {
            // If user selected all districts
            if ("All Districts".equals(selectedDistrict)) {
                // Add cities from every district
                for (List<String> cityList : districts.values()) {
                    citiesToShow.addAll(cityList);  // Add all cities into list
                }
            }
            // If specific district selected
            else if (districts.containsKey(selectedDistrict)) {
                citiesToShow.addAll(districts.get(selectedDistrict));  // Add cities from selected district
            }
        }

        // Add "All Cities" option at top
        if (!citiesToShow.contains("All Cities")) {
            citiesToShow.add(0, "All Cities");
        }

        // Set page header text
        if ("All Districts".equals(selectedDistrict) && "All Cities".equals(selectedCity)) {
            textHeader.setText("Attractions in " + selectedProvince + " Province");    // Province level heading

        } else if (!"All Districts".equals(selectedDistrict) && "All Cities".equals(selectedCity)) {
            textHeader.setText("Attractions in " + selectedDistrict + " District");   // District level heading

        } else {
            textHeader.setText("Attractions in " + selectedCity);  // City level heading
        }


        // If city list is available
        if (!citiesToShow.isEmpty()) {
            // Create spinner adapter
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                    this, R.layout.spinner_selected_item, citiesToShow
            );
            // Set dropdown layout
            cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinnerCityDropdown.setAdapter(cityAdapter); // Attach adapter to spinner

            // If selected city exists in list
            if (selectedCity != null && citiesToShow.contains(selectedCity)) {
                spinnerCityDropdown.setSelection(citiesToShow.indexOf(selectedCity));  // Select current city
                updateAttractions(selectedCity);  // Load attractions for selected city
            } else {
                spinnerCityDropdown.setSelection(0);   // Default to "All Cities"
                updateAttractions("All Cities");   // Load all attractions
            }


            // Listen for city selection changes
            spinnerCityDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected = citiesToShow.get(position);  // Get selected city
                    //  Update page title
                    if ("All Districts".equals(selectedDistrict) && "All Cities".equals(selected)) {
                        textHeader.setText("Attractions in " + selectedProvince + " Province");
                    } else if (!"All Districts".equals(selectedDistrict) && "All Cities".equals(selected)) {
                        textHeader.setText("Attractions in " + selectedDistrict + " District");
                    } else {
                        textHeader.setText("Attractions in " + selected);
                    }
                    updateAttractions(selected);  // Update attraction list
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    recyclerAttractions.setAdapter(null);   // Remove RecyclerView adapter
                }
            });

        } else {
            textHeader.setText("No cities available.");   // If no cities available
            recyclerAttractions.setAdapter(null);      // Clear RecyclerView
        }
    }


    // Loads attractions into RecyclerView
    private void updateAttractions(String city) {
        List<Attraction> attractions = new ArrayList<>();  // List to store attractions

        // If "All Cities" selected
        if ("All Cities".equals(city)) {
            // Get districts in selected province
            Map<String, List<String>> districtsInProvince = dataMap.get(selectedProvince);
            // Check if province exists
            if (districtsInProvince != null) {
                // If all districts selected
                if ("All Districts".equals(selectedDistrict)) {
                    // Loop through every district
                    for (List<String> cities : districtsInProvince.values()) {
                        // Loop through every city
                        for (String cityName : cities) {
                            List<Attraction> cityAttr = cityAttractionsMap.get(cityName);  // Get attractions for city
                            if (cityAttr != null) {
                                attractions.addAll(cityAttr);  // Add all attractions
                            }
                        }
                    }
                }
                // District-level selection
                else {
                    List<String> cities = districtsInProvince.get(selectedDistrict);   // Get cities in selected district
                    if (cities != null) {
                        // Loop through district cities
                        for (String cityName : cities) {
                            List<Attraction> cityAttr = cityAttractionsMap.get(cityName); // Get attractions
                            // Add attractions
                            if (cityAttr != null) {
                                attractions.addAll(cityAttr);
                            }
                        }
                    }
                }
            }
        }
        // If single city selected
        else {
            // Get attractions for selected city
            List<Attraction> singleCityAttractions = cityAttractionsMap.get(city);
            // Add attractions if available
            if (singleCityAttractions != null) {
                attractions.addAll(singleCityAttractions);
            }
        }

        AttractionAdapter adapter = new AttractionAdapter(this, attractions);   // Create RecyclerView adapter
        recyclerAttractions.setAdapter(adapter);  // Display attractions in RecyclerView
    }
}
