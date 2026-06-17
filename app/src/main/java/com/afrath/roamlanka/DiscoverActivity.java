package com.afrath.roamlanka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverActivity extends AppCompatActivity {

    Spinner spinnerProvince, spinnerDistrict, spinnerCity;
    MaterialButton btnSearch;

    // Main data structure
    // Province -> District -> City List
    Map<String, Map<String, List<String>>> dataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        spinnerProvince = findViewById(R.id.spinnerProvince);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnSearch = findViewById(R.id.btnSearch);

        setupData();   // Load all province/district/city data

        // Disable district and city spinners initially
        spinnerDistrict.setEnabled(false);
        spinnerCity.setEnabled(false);

        setupSpinner(spinnerProvince, getProvinceList(), new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = (String) parent.getItemAtPosition(position);   // Get selected province
                // If user selected a valid province
                if (!selectedProvince.equals("Select Province")) {
                    spinnerDistrict.setEnabled(true);


                    // Load district list for selected province
                    setupSpinner(spinnerDistrict, getDistrictList(selectedProvince), new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> p, View v, int pos, long id1) {
                            String selectedDistrict = (String) p.getItemAtPosition(pos); // Get selected district
                            spinnerCity.setEnabled(true);

                            if (selectedDistrict.equals("All Districts")) {
                                List<String> allCities = getAllCities(selectedProvince);  // Get all cities in selected province
                                allCities.add(0, "All Cities");  // Add "All Cities" at first position
                                setupSpinner(spinnerCity, allCities, null); // Load all cities into city spinner
                            } else {
                                // Load cities only for selected district
                                setupSpinner(spinnerCity, getCityList(selectedProvince, selectedDistrict), null);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Optional: handle if nothing is selected
                        }
                    });

                } else {
                    // If no province selected, disable district and city spinners
                    spinnerDistrict.setEnabled(false);
                    spinnerCity.setEnabled(false);

                    // Clear previous data
                    spinnerDistrict.setAdapter(null);
                    spinnerCity.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: handle if nothing is selected
            }
        });


        btnSearch.setOnClickListener(v -> {
            // Get selected values
            String province = (String) spinnerProvince.getSelectedItem(); // Get selected province
            String district = (String) spinnerDistrict.getSelectedItem();  // Get selected district
            String city = (String) spinnerCity.getSelectedItem();  // Get selected city

            // Check if city is selected
            if (city == null || city.equals("Select City") || city.isEmpty()) {
                Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, AttractionsResultActivity.class);   // Open Attractions Result page
            // Send selected data
            intent.putExtra("selected_province", province);
            intent.putExtra("selected_district", district);
            intent.putExtra("selected_city", city);
            startActivity(intent);
        });
    }


    // Method to setup spinner data
    private void setupSpinner(Spinner spinner, List<String> items, AdapterView.OnItemSelectedListener listener) {
        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_material, items);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_material);  // Set dropdown layout

        spinner.setAdapter(adapter);  // Attach adapter to spinner
        if (listener != null) spinner.setOnItemSelectedListener(listener);  // Attach listener if available
    }

    // Get all provinces
    private List<String> getProvinceList() {
        List<String> provinces = new ArrayList<>(dataMap.keySet());   // Convert map keys into list
        provinces.add(0, "Select Province");   // Add default option
        return provinces;
    }


    // Get districts for selected province
    private List<String> getDistrictList(String province) {
        List<String> districts = new ArrayList<>(dataMap.get(province).keySet());
        districts.add(0, "All Districts");   // Add default option
        return districts;
    }


    // Get cities for selected district
    private List<String> getCityList(String province, String district) {
        List<String> cities = new ArrayList<>(dataMap.get(province).get(district));
        cities.add(0, "All Cities");   // Add default option
        return cities;
    }


    // Get all cities inside a province
    private List<String> getAllCities(String province) {
        List<String> allCities = new ArrayList<>();
        // Loop through every district
        for (List<String> cities : dataMap.get(province).values()) {
            allCities.addAll(cities);  // Add all cities into one list
        }
        return allCities;
    }


    // Method to initialize all province, district, and city data
     private void setupData() {
        //Central Province
        Map<String, List<String>> central = new HashMap<>();
        central.put("Kandy", Arrays.asList("Kandy", "Peradeniya", "Katugastota", "Gampola", "Muruthalawa", "Nawalapitiya", "Digana", "Panwila", "Gelioya"));
        central.put("Matale", Arrays.asList("Matale", "Dambulla", "Rattota", "Ukuwela", "Nalanda", "Laggala"));
        central.put("Nuwara Eliya", Arrays.asList("Nuwara Eliya", "Ambewela", "Horton Plains", "Ramboda", "Talawakele", "Nanu Oya", "Maskeliya", "Ginigathena"));
        dataMap.put("Central", central);

        // Uva Province
        Map<String, List<String>> uva = new HashMap<>();
        uva.put("Badulla", Arrays.asList("Badulla", "Ella", "Bandarawela", "Haputale", "Hali-Ela", "Haldummulla", "Mahiyangana", "Koslanda", "Passara"));
        uva.put("Monaragala", Arrays.asList("Monaragala", "Wellawaya", "Bibile", "Buttala", "Thanamalvila"));
        dataMap.put("Uva", uva);

        // Southern Province
        Map<String, List<String>> southern = new HashMap<>();
        southern.put("Galle", Arrays.asList("Galle", "Unawatuna", "Hikkaduwa", "Talpe", "Koggala", "Ahangama", "Udugama", "Neluwa", "Thawalama", "Mahamodara"));
        southern.put("Matara", Arrays.asList("Matara","Tangalle", "Dondra", "Weligama", "Mirissa", "Talalla", "Nilwella", "Dikwella", "Gandara", "Kudawella", "Hiriketiya", "Thalaramba"));
        southern.put("Hambantota", Arrays.asList("Hambantota", "Tissamaharama", "Kirinda", "Palatupana", "Bundala", "Sooriyawewa", "Ambalantota", "Kalametiya"));
        dataMap.put("Southern", southern);

        // Western Province
        Map<String, List<String>> western = new HashMap<>();
        western.put("Colombo", Arrays.asList("Colombo", "Dehiwala", "Sri Jayawardenepura Kotte", "Ratmalana"));
        western.put("Gampaha", Arrays.asList("Gampaha", "Negombo", "Kelaniya", "Ja-Ela", "Katunayake", "Kirindiwela"));
        western.put("Kalutara", Arrays.asList("Panadura", "Beruwala"));
        dataMap.put("Western", western);

        // Eastern Province
        Map<String, List<String>> eastern = new HashMap<>();
        eastern.put("Trincomalee", Arrays.asList("Trincomalee", "Nilaveli", "Uppuveli", "Kinniya", "Muttur"));
        eastern.put("Batticaloa", Arrays.asList("Batticaloa", "Kalkudah", "Pasikuda", "Eravur", "Valaichenai"));
        eastern.put("Ampara", Arrays.asList("Ampara", "Akkaraipattu", "Arugam Bay", "Pottuvil", "Kalmunai"));
        dataMap.put("Eastern", eastern);

        // Northern Province
        Map<String, List<String>> northern = new HashMap<>();
        northern.put("Jaffna", Arrays.asList("Jaffna", "Nallur", "Chavakachcheri", "Point Pedro", "Karainagar"));
        northern.put("Kilinochchi", Arrays.asList("Kilinochchi", "Paranthan", "Pooneryn"));
        northern.put("Mannar", Arrays.asList("Mannar", "Pesalai", "Thalaimannar"));
        northern.put("Mullaitivu", Arrays.asList("Mullaitivu", "Puthukkudiyiruppu"));
        dataMap.put("Northern", northern);

        // North Central Province
        Map<String, List<String>> northCentral = new HashMap<>();
        northCentral.put("Anuradhapura", Arrays.asList("Anuradhapura", "Mihintale", "Nochchiyagama", "Kekirawa"));
        northCentral.put("Polonnaruwa", Arrays.asList("Polonnaruwa", "Medirigiriya", "Hingurakgoda", "Minneriya", "Kaduruwela"));
        dataMap.put("North Central", northCentral);

        // North Western Province
        Map<String, List<String>> northWestern = new HashMap<>();
        northWestern.put("Kurunegala", Arrays.asList("Kurunegala", "Kuliyapitiya", "Pannala", "Narammala", "Rideegama", "Ibbagamuwa"));
        northWestern.put("Puttalam", Arrays.asList("Puttalam", "Kalpitiya", "Chilaw", "Anamaduwa", "Wennappuwa", "Norochcholai"));
        dataMap.put("North Western", northWestern);

        // Sabaragamuwa Province
        Map<String, List<String>> sabaragamuwa = new HashMap<>();
        sabaragamuwa.put("Ratnapura", Arrays.asList("Ratnapura", "Balangoda", "Kuruwita","Pelmadulla", "Eheliyagoda", "Embilipitiya"));
        sabaragamuwa.put("Kegalle", Arrays.asList("Kegalle", "Mawanella", "Ruwanwella", "Aranayake", "Bulathkohupitiya", "Warakapola"));
        dataMap.put("Sabaragamuwa", sabaragamuwa);
    }

}