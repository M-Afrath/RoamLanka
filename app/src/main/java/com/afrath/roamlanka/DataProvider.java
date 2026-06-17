package com.afrath.roamlanka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {

    // Province -> District -> Cities
    public static Map<String, Map<String, List<String>>> getDataMap() {
        Map<String, Map<String, List<String>>> dataMap = new HashMap<>();  // Create main map to store all province data

        // Central Province
        Map<String, List<String>> central = new HashMap<>();
        // Add districts and their cities
        central.put("Kandy", Arrays.asList("Kandy", "Peradeniya", "Katugastota", "Gampola", "Muruthalawa", "Nawalapitiya", "Digana", "Panwila", "Gelioya"));
        central.put("Matale", Arrays.asList("Matale", "Dambulla", "Rattota", "Ukuwela", "Nalanda", "Laggala"));
        central.put("Nuwara Eliya", Arrays.asList("Nuwara Eliya", "Ambewela", "Horton Plains", "Ramboda", "Talawakele", "Nanu Oya", "Maskeliya", "Ginigathena"));
        dataMap.put("Central", central);  // Add Central Province into main map

        // Uva Province
        Map<String, List<String>> uva = new HashMap<>();
        uva.put("Badulla", Arrays.asList("Badulla", "Ella", "Bandarawela", "Haputale", "Hali-Ela", "Haldummulla", "Mahiyangana", "Koslanda", "Passara"));
        uva.put("Monaragala", Arrays.asList("Monaragala", "Wellawaya", "Bibile", "Buttala", "Thanamalvila"));
        dataMap.put("Uva", uva);

        // Southern Province
        Map<String, List<String>> southern = new HashMap<>();
        southern.put("Galle", Arrays.asList("Galle", "Unawatuna", "Hikkaduwa", "Talpe", "Koggala", "Ahangama", "Udugama", "Neluwa", "Thawalama", "Mahamodara"));
        southern.put("Matara", Arrays.asList("Matara", "Tangalle", "Dondra", "Weligama", "Mirissa", "Talalla", "Nilwella", "Dikwella", "Gandara", "Kudawella", "Hiriketiya", "Thalaramba"));
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

        return dataMap;
    }

    // City -> List of Attractions
    public static Map<String, List<Attraction>> getCityAttractionsMap() {
        Map<String, List<Attraction>> attractionsMap = new HashMap<>();

        // Central Province
        // ---- Attractions for Kandy District ----
        attractionsMap.put("Kandy", Arrays.asList(
                new Attraction("Temple of the Tooth", "Famous Buddhist temple in Kandy.",
                        Arrays.asList(R.drawable.temple_of_the_tooth, R.drawable.temple_of_the_tooth_1, R.drawable.temple_of_the_tooth_2), 7.2936, 80.6414),
                new Attraction("Kandy Lake", "Beautiful lake in the heart of Kandy, perfect for evening walks.",
                        Arrays.asList(R.drawable.kandy_lake, R.drawable.kandy_lake_1), 7.292108, 80.640585),
                new Attraction("Queen's Hotel", "Located facing the Kandy Lake, it is famous for its beautiful architecture and location.",
                        Arrays.asList(R.drawable.queens_hote, R.drawable.queens_hotel_1), 7.2932, 80.6408),
                new Attraction("Kandy City Center (KCC)", "Modern shopping mall with restaurants, fashion outlets, and entertainment.",
                        Arrays.asList(R.drawable.kandy_city_center, R.drawable.kandy_city_center_1), 7.2937, 80.6407),
                new Attraction("Udawattakele Sanctuary", "A forest reserve ideal for nature walks and bird watching.",
                        Arrays.asList(R.drawable.udawattakele_1, R.drawable.udawattakele, R.drawable.udawattakele_2), 7.2989, 80.6429),
                new Attraction("National Museum of Kandy", "Museum showcasing Kandyan history and artifacts.",
                        Arrays.asList(R.drawable.national_museum, R.drawable.national_museum_1, R.drawable.national_museum_2, R.drawable.national_museum_3, R.drawable.national_museum_4), 7.29472, 80.64083),
                new Attraction("Natha Devalaya", "Historic shrine beside the Temple of the Tooth.",
                        Arrays.asList(R.drawable.natha_devalaya, R.drawable.natha_devalaya_1, R.drawable.natha_devalaya_2), 7.293827, 80.640424),
                new Attraction("Hantana International Bird Park", "Family-friendly bird sanctuary with walk-through aviaries.",
                        Arrays.asList(R.drawable.bird_park, R.drawable.bird_park_1, R.drawable.bird_park_2, R.drawable.bird_park_3), 7.2556, 80.6221)
        ));
        attractionsMap.put("Peradeniya", Arrays.asList(
                new Attraction("Royal Botanical Gardens", "Largest botanical garden in Sri Lanka, known for orchids and royal palms.",
                        Arrays.asList(R.drawable.botanical_gardens, R.drawable.botanical_gardens_1), 7.271111, 80.595556),
                new Attraction("University of Peradeniya", "One of the most scenic university campuses in Asia.",
                        Arrays.asList(R.drawable.uni_of_per, R.drawable.uni_of_per_1, R.drawable.uni_of_per_2, R.drawable.uni_of_per_3), 7.2539, 80.5918),
                new Attraction("Ceylon Tea Museum (Hanthana)", "Learn about the history of Sri Lankan tea with antique machinery and tastings.",
                        Arrays.asList(R.drawable.ceylon_tea_museum, R.drawable.ceylon_tea_museum_1, R.drawable.ceylon_tea_museum_2), 7.2925, 80.6060),
                new Attraction("Gannoruwa Agro Technology Park", "A modern agricultural exhibit ideal for learning and sightseeing.",
                        Arrays.asList(R.drawable.gannoruwa, R.drawable.gannoruwa_1, R.drawable.gannoruwa_2, R.drawable.gannoruwa_3), 7.2660, 80.5980),
                new Attraction("Hanthana Mountain Range", "A misty hiking spot with breathtaking views.",
                        Arrays.asList(R.drawable.hanthana_mountain, R.drawable.hanthana_mountain_1), 7.2940, 80.6070)
        ));
        attractionsMap.put("Katugastota", Arrays.asList(
                new Attraction("Katugastota Bridge", "Historic bridge over Mahaweli River, connecting to northern parts of Kandy.",
                        Arrays.asList(R.drawable.katugastota), 7.333333, 80.616667)
        ));
        attractionsMap.put("Gampola", Arrays.asList(
                new Attraction("Ambuluwawa Tower", "Unique biodiversity complex with a tall spiral tower and 360° views.",
                        Arrays.asList(R.drawable.ambuluwawa, R.drawable.ambuluwawa_1, R.drawable.ambuluwawa_2), 7.16152, 80.54736)
        ));
        attractionsMap.put("Muruthalawa", Arrays.asList(
                new Attraction("Nelligala International Buddhist Center", "A serene hilltop temple with panoramic views...",
                        Arrays.asList(R.drawable.nelligala, R.drawable.nelligala_1, R.drawable.nelligala_2, R.drawable.nelligala_3), 7.3023, 80.56013)
        ));
       attractionsMap.put("Nawalapitiya", Arrays.asList(
                new Attraction("Galboda Waterfall", "Hidden waterfalls accessible by hike or train.",
                        Arrays.asList(R.drawable.galboda, R.drawable.galboda_1), 6.7844, 80.7061),
                new Attraction("Tea Estates", "Surrounded by lush green tea estates and scenic train rides.",
                        Arrays.asList(R.drawable.tea_nawalapitiya, R.drawable.tea_nawalapitiya_1), 6.8000, 80.7000)
        ));
        attractionsMap.put("Digana", Arrays.asList(
                new Attraction("Victoria Dam", "Amazing lake views, best at sunrise or sunset.",
                        Arrays.asList(R.drawable.victoria_reservoir, R.drawable.victoria_reservoir_1), 7.2418, 80.7850),
                new Attraction("Victoria Golf & Country Resort", "A luxury escape with golf, villas, and scenic mountain views.",
                        Arrays.asList(R.drawable.victoria_golf, R.drawable.victoria_golf_1), 7.2418, 80.7850)
        ));
        attractionsMap.put("Panwila", Arrays.asList(
                new Attraction("Hulu Ganga Waterfall", "Scenic and peaceful, ideal for nature lovers.",
                        Arrays.asList(R.drawable.hulu_ganga, R.drawable.hulu_ganga_1), 7.40107, 80.74215)
        ));
        attractionsMap.put("Gelioya", Arrays.asList(
                new Attraction("Embekka Devalaya", "Famous for intricate wooden carvings and culture.",
                        Arrays.asList(R.drawable.embekka, R.drawable.embekka_1, R.drawable.embekka_2, R.drawable.embekka_3), 7.217917, 80.567722),
                new Attraction("Lankatilaka Viharaya", "Ancient temple with stunning views and wall paintings.",
                        Arrays.asList(R.drawable.lankatilaka_viharaya, R.drawable.lankatilaka_viharaya_1, R.drawable.lankatilaka_viharaya_2, R.drawable.lankatilaka_viharaya_3), 7.233795, 80.565321)
        ));


        // ---- Attractions for Matale District ----
        attractionsMap.put("Matale", Arrays.asList(
                new Attraction("Aluvihare Rock Temple", "Ancient cave temple where the Pāli Canon was first written on ola leaves",
                        Arrays.asList(R.drawable.aluvihare_rock_temple, R.drawable.aluvihare_rock_temple_1, R.drawable.aluvihare_rock_temple_2, R.drawable.aluvihare_rock_temple_3), 7.4977, 80.6225),
                new Attraction("Sri Muthumariamman Temple", "Vibrant Hindu temple famed for its 32m gopuram and colourful chariot festival",
                        Arrays.asList(R.drawable.sri_muthumariamman, R.drawable.sri_muthumariamman_1), 7.4682, 80.6259),
                new Attraction("Matale Spice Gardens", "20 ha of spice cultivation…, offering guided tours and cooking demos",
                        Arrays.asList(R.drawable.matale_spice_gardens, R.drawable.matale_spice_gardens_1, R.drawable.matale_spice_gardens_2, R.drawable.matale_spice_gardens_3), 7.4610, 80.6300),
                new Attraction("Padiwita Ambalama", "A historic wayside rest … used by ancient travelers in the Matale region.",
                        Arrays.asList(R.drawable.padiwita_ambalama, R.drawable.padiwita_ambalama_1, R.drawable.padiwita_ambalama_2, R.drawable.padiwita_ambalama_3), 7.4720, 80.6220)
        ));
        attractionsMap.put("Dambulla", Arrays.asList(
                new Attraction("Sigiriya Rock", "Ancient Lion Rock palace fortress, another UNESCO site",
                        Arrays.asList(R.drawable.sigiriya_rock, R.drawable.sigiriya_rock_1, R.drawable.sigiriya_rock_2, R.drawable.sigiriya_rock_3, R.drawable.sigiriya_rock_4), 7.956944, 80.759720),
                new Attraction("Dambulla Cave Temple", "UNESCO … five cave temples with 150+ Buddha statues",
                        Arrays.asList(R.drawable.dambulla_cave_temple, R.drawable.dambulla_cave_temple_1, R.drawable.dambulla_cave_temple_2, R.drawable.dambulla_cave_temple_3), 7.8749, 80.6496),
                new Attraction("Pidurangala Vihara", "Rock temple offering a sunrise climb and sweeping views over Sigiriya",
                        Arrays.asList(R.drawable.pidurangala_vihara, R.drawable.pidurangala_1, R.drawable.pidurangala_vihara_2), 7.9658, 80.7615)
        ));
       attractionsMap.put("Rattota", Arrays.asList(
                new Attraction("Riverstone (Mini World’s End)", "…mountain area with beautiful views, hiking trails…",
                        Arrays.asList(R.drawable.riverstone, R.drawable.riverstone_1, R.drawable.riverston_2), 7.3950, 80.7430),
                new Attraction("Bambarakiri Ella", "Small but charming waterfall …",
                        Arrays.asList(R.drawable.bambarakiri_ella, R.drawable.bambarakiri_ella_1, R.drawable.bambarakiri_ella_2), 7.4030, 80.7550),
                new Attraction("Pitawala Pathana", "Grass plain on a cliff edge with spectacular views…",
                        Arrays.asList(R.drawable.pitawala_pathana, R.drawable.pitawala_pathana_1, R.drawable.pitawala_pathana_2), 7.4015, 80.7448),
                new Attraction("Sera Ella Waterfall", "A scenic twin waterfall with a hidden cave…",
                        Arrays.asList(R.drawable.sera_ella_waterfall, R.drawable.sera_ella_waterfall_1, R.drawable.sera_ella_waterfall_2, R.drawable.sera_ella_waterfall_3), 7.4100, 80.7500),
                new Attraction("Hulangala Mountain", "A peaceful mountain top near Rattota…",
                        Arrays.asList(R.drawable.hulangala, R.drawable.hulangala_1), 7.4070, 80.7450)
        ));
        attractionsMap.put("Ukuwela", Arrays.asList(
                new Attraction("Hunnasgiriya Waterfall", "Hidden fall in a tea estate…",
                        Arrays.asList(R.drawable.hunnasgiriya_waterfall, R.drawable.hunnasgiriya_waterfall_1, R.drawable.hunnasgiriya_waterfall_2), 7.3040, 80.6830),
                new Attraction("Knuckles Mountain Range", "A misty mountain range…",
                        Arrays.asList(R.drawable.knuckles_mountain, R.drawable.knuckles_mountain1, R.drawable.knuckles_mountain_2), 7.4670, 80.7310),
                new Attraction("Sembuwatta Lake", "A scenic man‑made lake in the hills…",
                        Arrays.asList(R.drawable.sembuwatta_lake, R.drawable.sembuwatta_lake_1, R.drawable.sembuwatta_lake_2), 7.3250, 80.6860),
                new Attraction("Dumbara Ella Trail", "A scenic forest trail … leading to a hidden waterfall…",
                        Arrays.asList(R.drawable.dumbara_ella_trail, R.drawable.dumbara_ella_trail_1, R.drawable.dumbara_ella_trail_2, R.drawable.dumbara_ella_trail_3), 7.3230, 80.6850)
        ));
        attractionsMap.put("Nalanda", Arrays.asList(
                new Attraction("Nalanda Gedige", "Ancient mysterious stone temple…",
                        Arrays.asList(R.drawable.nalanda_gedige, R.drawable.nalanda_gedige_1, R.drawable.nalanda_gedige_2, R.drawable.nalanda_gedige_3), 7.66974, 80.64593)
        ));
       attractionsMap.put("Laggala", Arrays.asList(
                new Attraction("Wasgamuwa National Park", "Wildlife park known for elephants, leopards, and birdwatching.",
                        Arrays.asList(R.drawable.wasgamuwa, R.drawable.wasgamuwa_1, R.drawable.wasgamuwa_2), 7.7780, 80.7540),
                new Attraction("Meemure Village", "A remote, scenic village surrounded by mountains…",
                        Arrays.asList(R.drawable.meemure, R.drawable.meemure_1, R.drawable.meemure_2), 7.4390, 80.8010)
        ));


        // ---- Attractions for Nuwara Eliya District ----
        attractionsMap.put("Nuwara Eliya", Arrays.asList(
                new Attraction("Gregory Lake", "A beautiful lake ideal for boat rides, cycling, food stalls, and family picnics.",
                        Arrays.asList(R.drawable.gregory_lake, R.drawable.gregory_lake_1, R.drawable.gregory_lake_2, R.drawable.gregory_lake_3), 6.9740, 80.7825),
                new Attraction("Victoria Park", "Well-maintained flower garden with birds, streams, and walking paths.",
                        Arrays.asList(R.drawable.victoria_park, R.drawable.victoria_park_1, R.drawable.victoria_park_2), 6.9705, 80.7814),
                new Attraction("Hakgala Botanical Garden", "One of the oldest botanical gardens, famous for orchids, roses, and cool climate flora.",
                        Arrays.asList(R.drawable.hakgala_botanical_garden, R.drawable.hakgala_botanical_garden_1, R.drawable.hakgala_botanical_garden_2, R.drawable.hakgala_botanical_garden_3, R.drawable.hakgala_botanical_garden_4), 6.9033, 80.8127),
                new Attraction("Nuwara Eliya Post Office", "Historic Tudor-style colonial post office in the heart of the town.",
                        Arrays.asList(R.drawable.nuwara_eliya_post_office, R.drawable.nuwara_eliya_post_office_1, R.drawable.nuwara_eliya_post_office_2), 6.9749, 80.7895),
                new Attraction("Strawberry Gardens", "Visit to pluck and taste fresh strawberries; ideal for families.",
                        Arrays.asList(R.drawable.strawberry_gardens, R.drawable.strawberry_gardens_1, R.drawable.strawberry_gardens_2), 6.9780, 80.7830),
                new Attraction("The Eagle’s View Point", "Offers stunning panoramic views of Nuwara Eliya and the surrounding hills.",
                        Arrays.asList(R.drawable.egles_view_point, R.drawable.egles_view_point_1, R.drawable.egles_view_point_2), 6.9712, 80.8015),
                new Attraction("Lover's Leap Waterfall", "A romantic and scenic waterfall located behind Pedro Tea Estate.",
                        Arrays.asList(R.drawable.lovers_leap_waterfall, R.drawable.lovers_leap_waterfall_1, R.drawable.lovers_leap_waterfall_2), 6.9628, 80.7914),
                new Attraction("Pedro Tea Estate & Factory", "Learn about tea-making and enjoy views over endless tea fields.",
                        Arrays.asList(R.drawable.pedro_tea, R.drawable.pedro_tea_1, R.drawable.pedro_tea_2, R.drawable.pedro_tea_3), 6.9653, 80.7902),
                new Attraction("Moon Plains (Sandathenna)", "Mini “World’s End” with 360° views of 9 mountain peaks.",
                        Arrays.asList(R.drawable.moon_plains, R.drawable.moon_plains_1, R.drawable.moon_plains_2, R.drawable.moon_plains_3), 6.9611, 80.7800),
                new Attraction("Bomburu Ella", "The widest waterfall in Sri Lanka; a scenic and peaceful place to trek.",
                        Arrays.asList(R.drawable.bomburu_ella, R.drawable.bomburu_ella_1, R.drawable.bomburu_ella_2, R.drawable.bomburu_ella_3),
                        6.9427, 80.7893),
                new Attraction("Holy Trinity Church", "Built in 1845, this colonial-era church is peaceful and architecturally charming.",
                        Arrays.asList(R.drawable.holy_trinity_church, R.drawable.holy_trinity_church_1, R.drawable.holy_trinity_church_2), 6.9760, 80.7831),
                new Attraction("Nuwara Eliya Golf Club", "One of Asia’s oldest golf clubs, open to visitors and surrounded by hills.",
                        Arrays.asList(R.drawable.nuwara_eliya_golf_club, R.drawable.nuwara_eliya_golf_club_1), 6.9791, 80.7805),
                new Attraction("The Hill Club", "A colonial-style gentlemen’s club and hotel with a vintage vibe and formal dining.",
                        Arrays.asList(R.drawable.the_hill_club, R.drawable.the_hill_club_1, R.drawable.the_hill_club_2), 6.9783, 80.7809),
                new Attraction("Royal Turf Club (Race Course)", "Historic horse racing venue; season events draw crowds.",
                        Arrays.asList(R.drawable.royal_turf_club, R.drawable.royal_turf_club_1, R.drawable.royal_turf_club_2), 6.9759, 80.7800),
                new Attraction("Seetha Amman Temple – Sita Eliya", "Hindu temple linked to the Ramayana legend, with nearby river and statues.",
                        Arrays.asList(R.drawable.seetha_amman, R.drawable.seetha_amman_1), 6.9710, 80.7908),
                new Attraction("Galway’s Land National Park", "A tiny forest reserve rich in endemic birds and wildlife.",
                        Arrays.asList(R.drawable.galways_land, R.drawable.galways_land_1, R.drawable.galways_land_2), 6.9786, 80.7791),
                new Attraction("Pidurutalagala", "Sri Lanka’s tallest mountain (2,524m)",
                        Arrays.asList(R.drawable.pidurutalagala, R.drawable.pidurutalagala_1), 6.9687, 80.7921)
        ));
        attractionsMap.put("Ambewela", Arrays.asList(
                new Attraction("Ambewela Farm (Little New Zealand)",
                        "Dairy farm with cows, sheep, and a cool climate; kids love it.",
                        Arrays.asList(R.drawable.ambewela_farm, R.drawable.ambewela_farm_1), 6.9260, 80.7410),
                new Attraction("New Zealand Farm",
                        "Another scenic dairy farm with fresh milk production and grazing animals.",
                        Arrays.asList(R.drawable.new_zealand_farm, R.drawable.new_zealand_farm_1, R.drawable.new_zealand_farm_2), 6.9285, 80.7430)
        ));
        attractionsMap.put("Horton Plains", Arrays.asList(
                new Attraction("World’s End",
                        "A famous cliff with a sheer drop; breathtaking view at sunrise.",
                        Arrays.asList(R.drawable.worlds_end, R.drawable.worlds_end_1, R.drawable.worlds_end_2), 6.7969, 80.7964),
                new Attraction("Baker’s Falls",
                        "A misty waterfall surrounded by thick forest on the Horton Plains trail.",
                        Arrays.asList(R.drawable.bakers_falls, R.drawable.bakers_falls_1, R.drawable.bakers_falls_2, R.drawable.bakers_falls_3), 6.7989, 80.7907),
                new Attraction("Horton Plains Nature Trails",
                        "Circular hiking trail (9km) covering forests, plains, and viewpoints.",
                        Arrays.asList(R.drawable.horton_plains_nature_trails, R.drawable.horton_plains_nature_trails_1, R.drawable.horton_plains_nature_trails_2, R.drawable.horton_plains_nature_trails_3, R.drawable.horton_plains_natire_n), 6.8000, 80.7900),
                new Attraction("Kirigalpotta",
                        "2nd highest mountain in Sri Lanka; requires a hike through Horton Plains.",
                        Arrays.asList(R.drawable.kirigalpotta, R.drawable.kirigalpotta_1), 6.8213, 80.8113),
                new Attraction("Thotupola Kanda",
                        "3rd highest peak; easier hike than Kirigalpotta, great for beginners.",
                        Arrays.asList(R.drawable.thotupola_kanda, R.drawable.thotupola_kanda_1, R.drawable.thotupola_kanda_2, R.drawable.thotupola_kanda_3), 6.8045, 80.8116),
                new Attraction("Pattipola Railway Station",
                        "Sri Lanka’s highest station, near Horton Plains, known for its cool climate and scenic views.",
                        Arrays.asList(R.drawable.pattipola_railway_station, R.drawable.pattipola_railway_station_1, R.drawable.pattipola_railway_station_2), 6.8236, 80.7965),
                new Attraction("Beker's Bend",
                        "A sharp curve on the A5 road in Nuwara Eliya District, offering stunning mountain views. It's named after British explorer Sir Samuel Baker.",
                        Arrays.asList(R.drawable.bekers_bend, R.drawable.bekers_bend_1), 6.9000, 80.7660)
        ));
        attractionsMap.put("Ramboda", Arrays.asList(
                new Attraction("Ramboda Falls", "One of the tallest waterfalls in Sri Lanka, visible from the A5 road.",
                        Arrays.asList(R.drawable.ramboda_falls, R.drawable.ramboda_falls_1, R.drawable.ramboda_falls_2), 7.0953, 80.6952),
                new Attraction("Ramboda Tunnel & Road Pass", "Scenic drive through hill tunnels with sharp bends and tea fields.",
                        Arrays.asList(R.drawable.road_pass, R.drawable.road_pass_1), 7.0957, 80.6975),
                new Attraction("Dunsinane Falls", "Located between Dunsinane and Sheen tea estates; twin-stream cascade.",
                        Arrays.asList(R.drawable.dunsinane_falls, R.drawable.dunsinane_falls_1, R.drawable.dunsinane_falls_2), 7.0965, 80.6864),
                new Attraction("Labookellie Tea Estate", "Home to Mackwoods Tea Centre; scenic stop with factory tours.",
                        Arrays.asList(R.drawable.labookellie_tea_estate, R.drawable.labookellie_tea_estate_1, R.drawable.labookellie_tea_estate_2), 7.0862, 80.6821)
        ));
        attractionsMap.put("Talawakele", Arrays.asList(
                new Attraction("Devon Falls", "“Veil of the Valley” waterfall, 97m high, with a stunning viewpoint nearby.",
                        Arrays.asList(R.drawable.devon_falls, R.drawable.devon_falls_1, R.drawable.devon_falls_2), 7.0278, 80.6406),
                new Attraction("St. Clair's Falls", "One of the widest waterfalls in Sri Lanka, often called the “Little Niagara.”",
                        Arrays.asList(R.drawable.st_clairs_falls, R.drawable.st_clairs_falls_1, R.drawable.st_clairs_falls_2), 7.0303, 80.6450),
                new Attraction("Talawakele Tea Estates", "Expansive tea gardens; base for hiking and photography.",
                        Arrays.asList(R.drawable.talawakele_tea_estates, R.drawable.talawakele_tea_estates_1), 7.0335, 80.6418),
                new Attraction("Upper Kotmale Dam Viewpoint", "A scenic viewpoint near Talawakele with views of a big dam and valley.",
                        Arrays.asList(R.drawable.kotmale_dam_viewpoint, R.drawable.kotmale_dam_viewpoint_1), 7.0371, 80.6537)
        ));
        attractionsMap.put("Nanu Oya", Arrays.asList(
                new Attraction("Nanu Oya Railway Station", "A scenic hill-country train station that serves Nuwara Eliya, known for its colonial architecture and connection to Sri Lanka’s iconic blue train journey.",
                        Arrays.asList(R.drawable.nanu_oya_railway_station, R.drawable.nanu_oya_railway_station_1, R.drawable.nanu_oya_railway_station_2), 6.9441, 80.7833),
                new Attraction("Scenic rail photography spots", "Great place to capture the famous blue train winding through hills.",
                        Arrays.asList(R.drawable.scenic_rail_photography_spots, R.drawable.scenic_rail_photography_spots_1), 6.9450, 80.7850)
        ));
        attractionsMap.put("Maskeliya", Arrays.asList(
                new Attraction("Gartmore Falls", "Tall, tiered waterfall near Gartmore Estate; hike required.",
                        Arrays.asList(R.drawable.gartmore_falls, R.drawable.gartmore_falls_1, R.drawable.gartmore_falls_2, R.drawable.gartmore_falls_3), 6.9482, 80.5557),
                new Attraction("Moray Falls", "Lesser-known falls with a beautiful drop into a jungle pool.",
                        Arrays.asList(R.drawable.moray_falls, R.drawable.moray_falls_1, R.drawable.moray_falls_2), 6.9525, 80.5563),
                new Attraction("Laxapana Falls", "One of the tallest and most beautiful falls in Sri Lanka; linked to Lord Buddha legends.",
                        Arrays.asList(R.drawable.laxapana_falls, R.drawable.laxapana_falls_1, R.drawable.laxapana_falls_2, R.drawable.laxapana_falls_3), 6.9683, 80.5679),
                new Attraction("Adam’s Peak (Sri Pada)", "A sacred mountain climbed by pilgrims for sunrise views and the holy footprint at the summit.",
                        Arrays.asList(R.drawable.sri_pada, R.drawable.sri_pada_1, R.drawable.sri_pada_2, R.drawable.sri_pada_3), 6.8000, 80.4980),
                new Attraction("Maussakelle Reservoir", "A calm lake in Maskeliya surrounded by tea estates and mountains.",
                        Arrays.asList(R.drawable.maussakelle_reservoir, R.drawable.maussakelle_reservoir_1, R.drawable.maussakelle_reservoir_2, R.drawable.maussakelle_reservoir_3), 6.9447, 80.5601)
        ));
        attractionsMap.put("Ginigathena", Arrays.asList(
                new Attraction("Aberdeen Falls", "Hidden waterfall surrounded by lush jungle; great for adventurers.",
                        Arrays.asList(R.drawable.aberdeen_falls, R.drawable.aberdeen_falls_1, R.drawable.aberdeen_falls_2, R.drawable.aberdeen_falls_3), 7.0208, 80.6832)
        ));



        // Uva Province
        // ---- Attractions for Badulla District ----
        attractionsMap.put("Badulla", Arrays.asList(
                new Attraction("Muthiyangana Raja Maha Viharaya", "Ancient sacred Buddhist temple visited by Lord Buddha.",
                        Arrays.asList(R.drawable.muthiyangana, R.drawable.muthiyangana_1, R.drawable.muthiyangana_2), 6.9891, 81.0568 ),
                new Attraction("Dunhinda Falls", "Misty 64-meter waterfall near Badulla city, great for photos.",
                        Arrays.asList(R.drawable.dunhinda_falls, R.drawable.dunhinda_falls_1), 6.9938, 81.0516),
                new Attraction("Old Welekade Market", "Colonial-era market building built in 1889.",
                        Arrays.asList(R.drawable.old_welekade_market, R.drawable.old_welekade_market_1), 6.9899, 81.0542),
                new Attraction("Badulla Railway Station", "Historic station with classic colonial design.",
                        Arrays.asList(R.drawable.badulla_railway_station, R.drawable.badulla_railway_station_1), 6.9895, 81.0540),
                new Attraction("Kurullangala Rock", "A granite outcrop featuring ancient paintings (peacocks, patterns), a local archaeological curiosity.",
                        Arrays.asList(R.drawable.kurullangala_rock, R.drawable.kurullangala_rock_1, R.drawable.kurullangala_rock_2), 6.9726, 81.0924)
        ));
        attractionsMap.put("Ella", Arrays.asList(
                new Attraction("Ella Rock", "Popular hike with panoramic views of valleys and tea plantations.",
                        Arrays.asList(R.drawable.ella_rock, R.drawable.ella_rock_1, R.drawable.ella_rock_2), 6.8578, 81.0606),
                new Attraction("Nine Arches Bridge", "Famous railway bridge set in lush greenery, perfect for photos.",
                        Arrays.asList(R.drawable.nine_arches_bridge, R.drawable.nine_arches_bridge_1, R.drawable.nine_arches_bridge_2, R.drawable.nine_arches_bridge_3), 6.8761, 81.0570),
                new Attraction("Little Adam’s Peak", "Easy hike with rewarding scenic views.",
                        Arrays.asList(R.drawable.little_adams_peak, R.drawable.little_adams_peak_1, R.drawable.little_adams_peak_2), 6.8667, 81.0601),
                new Attraction("Ravana Falls", "82-foot waterfall associated with local legend.",
                        Arrays.asList(R.drawable.ravana_falls, R.drawable.ravana_falls_1, R.drawable.ravana_falls_2), 6.8416, 81.0453),
                new Attraction("Ravana Cave", "Mythological cave near Ravana Falls.",
                        Arrays.asList(R.drawable.ravana_cave, R.drawable.ravana_cave_1, R.drawable.ravana_cave_2), 6.8432, 81.0486),
                new Attraction("Ella Spice Garden", "Tour spice plantations and taste local spices.",
                        Arrays.asList(R.drawable.ella_spice_garden, R.drawable.ella_spice_garden_1, R.drawable.ella_spice_garden_2, R.drawable.ella_spice_garden_3), 6.8753, 81.0461),
                new Attraction("Dowa Rock Temple", "Cave temple with unfinished 38-foot Buddha statue and ancient paintings.",
                        Arrays.asList(R.drawable.dowa_rock_temple, R.drawable.dowa_rock_temple_1, R.drawable.dowa_rock_temple_2), 6.8993, 81.0444),
                new Attraction("Flying Ravana Mega Zipline", "Adventure + scenic thrill.",
                        Arrays.asList(R.drawable.flying_ravana, R.drawable.flying_ravana_1, R.drawable.flying_ravana_2), 6.8662, 81.0607),
                new Attraction("Demodara Loop & Railway Station", "Unique railway spiral track.",
                        Arrays.asList(R.drawable.demodara_loop, R.drawable.demodara_loop_1, R.drawable.demodara_loop_2, R.drawable.demodara_loop_3), 6.9011, 81.0522),
                new Attraction("Bambaragala Pathana", "Hidden viewpoint and grassy plain near Ella.",
                        Arrays.asList(R.drawable.bambaragala_pathana, R.drawable.bambaragala_pathana_1, R.drawable.bambaragala_pathana_2), 6.8537, 81.0482),
                new Attraction("Kithal Ella Falls", "Small but beautiful and calm waterfall.",
                        Arrays.asList(R.drawable.kithal_ella, R.drawable.kithal_ella_1), 6.8857, 81.0550),
                new Attraction("Ella Wala Falls", "A hidden forest waterfall that’s calm and photographed by few.",
                        Arrays.asList(R.drawable.ella_wala_falls, R.drawable.ella_wala_falls_1, R.drawable.ella_wala_falls_2), 6.7615, 81.0215)
        ));
        attractionsMap.put("Bandarawela", Arrays.asList(
                new Attraction("Porawagala Viewpoint", "Hilltop view over valleys and tea fields.",
                        Arrays.asList(R.drawable.porawagala_viewpoint, R.drawable.porawagala_viewpoint_1, R.drawable.porawagala_viewpoint_2, R.drawable.porawagala_viewpoint_3), 6.8268, 80.9932),
                new Attraction("Dambetenna Tea Factory", "Historic tea factory by Lipton, scenic area.",
                        Arrays.asList(R.drawable.dambetenna_tea_factory, R.drawable.dambetenna_tea_factory_1, R.drawable.dambetenna_tea_factory_2, R.drawable.dambetenna_tea_factory_3), 6.8804, 81.1078)
        ));
        attractionsMap.put("Haputale", Arrays.asList(
                new Attraction("Lipton’s Seat", "Famous viewpoint used by Sir Lipton.",
                        Arrays.asList(R.drawable.lipton_seat, R.drawable.lipton_seat_1, R.drawable.lipton_seat_2, R.drawable.lipton_seat_3), 6.8485, 81.1109),
                new Attraction("Adisham Bungalow", "English-style old bungalow with garden.",
                        Arrays.asList(R.drawable.adisham_bungalow, R.drawable.adisham_bungalow_1, R.drawable.adisham_bungalow_2), 6.7671, 80.9639),
                new Attraction("Thangamale Sanctuary", "Small forest with birds and walking trails.",
                        Arrays.asList(R.drawable.thangamale_sanctuary, R.drawable.thangamale_sanctuary_1, R.drawable.thangamale_sanctuary_2), 6.7689, 80.9676)
        ));
        attractionsMap.put("Hali-Ela", Arrays.asList(
                new Attraction("Bogoda Wooden Bridge", "Oldest wooden bridge in Sri Lanka.",
                        Arrays.asList(R.drawable.bogoda_wooden_bridge, R.drawable.bogoda_wooden_bridge_1, R.drawable.bogoda_wooden_bridge_2), 7.0073, 81.0454)
        ));
       attractionsMap.put("Haldummulla", Arrays.asList(
                new Attraction("Bambarakanda Falls", "Tallest waterfall in Sri Lanka (263 m), a must-visit.",
                        Arrays.asList(R.drawable.bambarakanda_falls, R.drawable.bambarakanda_falls_1, R.drawable.bambarakanda_falls_2, R.drawable.bambarakanda_falls_3), 6.7652, 80.8613),
                new Attraction("Haldummulla Fort", "Portuguese-era fort ruins with panoramic views.",
                        Arrays.asList(R.drawable.haldummulla_fort), 6.7658, 80.9023),
                new Attraction("Wangedigala Mountain", "Also known as Vangedigala (“pestle rock”), popular for trekking with stunning views over Bambarakanda Falls.",
                        Arrays.asList(R.drawable.wangedigala_mountain, R.drawable.wangedigala_mountain_1, R.drawable.wangedigala_mountain_2), 6.7634, 80.8736),
                new Attraction("Hunugalagala Limestone Cave",
                        "An archaeological cave in Haldummulla with prehistoric artifacts dating back thousands of years.", Arrays.asList(R.drawable.hunugalagala_limestone_cave, R.drawable.hunugalagala_limestone_cave_1, R.drawable.hunugalagala_limestone_cave_2), 6.7703, 80.9059)
        ));
        attractionsMap.put("Mahiyangana", Arrays.asList(
                new Attraction("Mahiyangana Raja Maha Viharaya", "Ancient temple believed to have been visited by Lord Buddha.",
                        Arrays.asList(R.drawable.mahiyangana_raja_maha_viharaya, R.drawable.mahiyangana_raja_maha_viharaya_1, R.drawable.mahiyangana_raja_maha_viharaya_2), 7.3302, 81.0031),
                new Attraction("Sorabora Wewa", "Ancient reservoir built during King Dutugemunu’s reign.",
                        Arrays.asList(R.drawable.sorabora_wewa, R.drawable.sorabora_wewa_1, R.drawable.sorabora_wewa_2), 7.3191, 81.0070),
                new Attraction("Dambana Tribal Village", "A traditional Vedda (indigenous) village where you can learn about Sri Lanka's aboriginal culture, hunting methods, and lifestyle.",
                        Arrays.asList(R.drawable.dambana_tribal_village, R.drawable.dambana_tribal_village_1, R.drawable.dambana_tribal_village_2, R.drawable.dambana_tribal_village_3, R.drawable.dambana_tribal_village_4), 7.3662, 81.2153),
                new Attraction("18 Bends", "A famous winding mountain road section with 18 sharp hairpin bends on the Mahiyangana–Kandy road (A26).",
                        Arrays.asList(R.drawable.bends, R.drawable.bends_1, R.drawable.bends_2), 7.2483, 80.9671)
        ));
        attractionsMap.put("Koslanda", Arrays.asList(
                new Attraction("Diyaluma Falls","One of Sri Lanka’s highest waterfalls, near Ella.",
                        Arrays.asList(R.drawable.diyaluma_falls, R.drawable.diyaluma_falls_1, R.drawable.diyaluma_falls_2, R.drawable.diyaluma_falls_3), 6.7657, 81.0064)
        ));
        attractionsMap.put("Passara", Arrays.asList(
                new Attraction("Peessa Ella Falls", "Hidden waterfall in a quiet village.",
                        Arrays.asList(R.drawable.peessa_ella_falls, R.drawable.peessa_ella_falls_1, R.drawable.peessa_ella_falls_2), 6.9595, 81.2502),
                new Attraction("Madolsima", "Known for steep slopes and foggy vistas, a rising favorite for camping and off‑beat exploration.",
                        Arrays.asList(R.drawable.madolsima, R.drawable.madolsima_1, R.drawable.madolsima_2, R.drawable.madolsima_3), 6.9932, 81.2286)
        ));


        // ---- Attractions for Monaragala District ----
        attractionsMap.put("Monaragala", Arrays.asList(
                new Attraction("Yudaganawa Raja Maha Viharaya", "Massive ancient stupa where King Dutugemunu’s army is believed to have camped.",
                        Arrays.asList(R.drawable.yudaganawa_raja_maha_viharaya, R.drawable.yudaganawa_raja_maha_viharaya_1, R.drawable.yudaganawa_raja_maha_viharaya_2), 6.8292, 81.2454)
        ));
        attractionsMap.put("Wellawaya", Arrays.asList(
                new Attraction("Buduruwagala Temple", "A rock-face temple with 7 carved figures (~10th century), including a 15m Buddha and Bodhisattvas.",
                        Arrays.asList(R.drawable.buduruwagala_temple, R.drawable.buduruwagala_temple_1, R.drawable.buduruwagala_temple_2), 6.6564, 81.0088),
                new Attraction("Namal Oya Reservoir", "Popular for evening relaxation and calm waters with forest backdrop.",
                        Arrays.asList(R.drawable.namal_oya_reservoir, R.drawable.namal_oya_reservoir_1), 6.7536, 81.3041)
        ));
        attractionsMap.put("Bibile", Arrays.asList(
                new Attraction("Kanabiso Pokuna Raja Maha Viharaya", "An ancient temple in Bibile with a rock-cut pond and old stone ruins. A peaceful and sacred place to visit.",
                        Arrays.asList(R.drawable.kanabiso_pokuna, R.drawable.kanabiso_pokuna_1, R.drawable.kanabiso_pokuna_2), 7.1286, 81.2189)
        ));
        attractionsMap.put("Buttala", Arrays.asList(
                new Attraction("Maligawila Buddha Statue", "Giant standing Buddha statue carved from a single stone, 11m tall.",
                        Arrays.asList(R.drawable.maligawila_buddha_statue, R.drawable.maligawila_buddha_statue_1), 6.6489, 81.2678),
                new Attraction("Dematamal Viharaya", "Historical site where Prince Saddatissa took refuge.",
                        Arrays.asList(R.drawable.dematamal_viharaya, R.drawable.dematamal_viharaya_1, R.drawable.dematamal_viharaya_2, R.drawable.dematamal_viharaya_3), 6.7637, 81.2367),
                new Attraction("Biso Pokuna (Queen’s Pond)", "Ancient bathing pool used by queens in Anuradhapura times.",
                        Arrays.asList(R.drawable.biso_pokuna, R.drawable.biso_pokuna_1, R.drawable.biso_pokuna_2), 6.6485, 81.2686)
        ));
       attractionsMap.put("Thanamalvila", Arrays.asList(
                new Attraction("Thanamalvila Reservoir", "Good fishing, birdwatching, and sunset photography.",
                        Arrays.asList(R.drawable.thanamalvila_reservoir, R.drawable.thanamalvila_reservoir_2), 6.4741, 80.9962),
                new Attraction("Kumbukkan Oya (River)", "Beautiful jungle river with crossing points, great for explorers.",
                        Arrays.asList(R.drawable.kumbukkan_oya, R.drawable.kumbukkan_oya_1), 6.6013, 81.3955)
        ));




        // Southern Province
        // ---- Attractions for Galle District ----
        attractionsMap.put("Galle", Arrays.asList(
                new Attraction("Galle Fort", "UNESCO World Heritage Site – Colonial-era fortress, iconic for photo walks, sunsets, and Dutch architecture.",
                        Arrays.asList(R.drawable.galle_fort_1, R.drawable.galle_fort_2), 6.0320, 80.2160),
                new Attraction("Galle Lighthouse", "Beautiful white lighthouse inside the Fort with scenic sea views.",
                        Arrays.asList(R.drawable.galle_lighthouse_1, R.drawable.galle_lighthouse_2, R.drawable.galle_lighthouse_3), 6.0312, 80.2167),
                new Attraction("Dutch Reformed Church", "One of Sri Lanka’s oldest Protestant churches with Dutch colonial architecture.",
                        Arrays.asList(R.drawable.dutch_reformed_church_1, R.drawable.dutch_reformed_church_2), 6.0327, 80.2171),
                new Attraction("National Maritime Museum", "Historic building with exhibits on marine archaeology and maritime history.",
                        Arrays.asList(R.drawable.maritime_museum_1, R.drawable.maritime_museum_2), 6.0315, 80.2158),
                new Attraction("All Saints' Church", "19th-century Anglican church in Gothic Revival style with stained glass.",
                        Arrays.asList(R.drawable.all_saints_church_1, R.drawable.all_saints_church_2), 6.0324, 80.2182),
                new Attraction("Old Dutch Hospital Shopping Precinct", "Stylish shopping & dining area housed in a colonial-era hospital.",
                        Arrays.asList(R.drawable.old_dutch_hospital_1, R.drawable.old_dutch_hospital_2), 6.0318, 80.2175),
                new Attraction("Wakwella Bridge", "Longest bridge in Sri Lanka across the scenic Gin River, great riverside views.",
                        Arrays.asList(R.drawable.wakwella_bridge_1), 6.1239, 80.2052),
                new Attraction("Dewata Beach", "Beginner-friendly surfing beach popular with both locals and travelers.",
                        Arrays.asList(R.drawable.dewata_beach_1, R.drawable.dewata_beach_2, R.drawable.dewata_beach_3), 6.0214, 80.2286)
        ));
        attractionsMap.put("Unawatuna", Arrays.asList(
                new Attraction("Unawatuna Beach", "Popular beach with calm turquoise waters, coral reefs, and golden sands.",
                        Arrays.asList(R.drawable.unawatuna_beach_1, R.drawable.unawatuna_beach_2), 6.0099, 80.2494),
                new Attraction("Japanese Peace Pagoda", "White stupa on a forested hill with panoramic sea views.",
                        Arrays.asList(R.drawable.japanese_peace_pagoda_1, R.drawable.japanese_peace_pagoda_2, R.drawable.japanese_peace_pagoda_3), 6.0103, 80.2435),
                new Attraction("Jungle Beach", "Secluded beach cove ideal for snorkeling and peaceful relaxation.",
                        Arrays.asList(R.drawable.jungle_beach_1, R.drawable.jungle_beach_2, R.drawable.jungle_beach_3), 6.0096, 80.2440),
                new Attraction("Yatagala Raja Maha Viharaya", "Ancient rock temple with panoramic views and a peaceful setting.",
                        Arrays.asList(R.drawable.yatagala_temple_1, R.drawable.yatagala_temple_2), 6.0424, 80.2492),
                new Attraction("Rumassala Sanctuary", "Biodiverse forest hill linked to the Ramayana epic, great for nature walks and views.",
                        Arrays.asList(R.drawable.rumassala_1), 6.0091, 80.2449)
        ));
        attractionsMap.put("Hikkaduwa", Arrays.asList(
                new Attraction("Hikkaduwa Beach", "Famous for surfing, snorkeling, vibrant coral reefs, and beach parties.",
                        Arrays.asList(R.drawable.hikkaduwa_beach_1, R.drawable.hikkaduwa_beach_2), 6.1403, 80.1015),
                new Attraction("Hikkaduwa Coral Sanctuary", "Protected marine area with beautiful coral and colorful fish.",
                        Arrays.asList(R.drawable.hikkaduwa_coral_1, R.drawable.hikkaduwa_coral_2, R.drawable.hikkaduwa_coral_3), 6.1395, 80.1010),
                new Attraction("Tsunami Photo Museum", "Small but moving museum about the 2004 tsunami's local impact.",
                        Arrays.asList(R.drawable.tsunami_museum_1, R.drawable.tsunami_museum_2), 6.1650, 80.1082),
                new Attraction("Seenigama Muhudu Viharaya", "Iconic temple on a tiny offshore island, known for spiritual rituals and sea views.",
                        Arrays.asList(R.drawable.seenigama_temple_1, R.drawable.seenigama_temple_2), 6.1416, 80.0965)
        ));
        attractionsMap.put("Talpe", Arrays.asList(
                new Attraction("Talpe Rock Pools", "Popular Instagram photo spot with man-made saltwater pools on the coast.",
                        Arrays.asList(R.drawable.talpe_pools_1, R.drawable.talpe_pools_2), 5.9986, 80.2820)
        ));
        attractionsMap.put("Koggala", Arrays.asList(
                new Attraction("Koggala Beach", "Quiet and less commercial beach, ideal for a peaceful walk.",
                        Arrays.asList(R.drawable.koggala_beach_1, R.drawable.koggala_beach_2), 5.9907, 80.3354),
                new Attraction("Koggala Lake", "Scenic lake with boat rides to cinnamon island and birdwatching.",
                        Arrays.asList(R.drawable.koggala_lake_1, R.drawable.koggala_lake_2), 5.9956, 80.3488),
                new Attraction("Martin Wickramasinghe Folk Museum", "Dedicated to the life and works of the famed Sri Lankan author.",
                        Arrays.asList(R.drawable.martin_museum_1, R.drawable.martin_museum_2, R.drawable.martin_museum_3), 5.9937, 80.3452)
        ));
        attractionsMap.put("Ahangama", Arrays.asList(
                new Attraction("Ahangama Beach", "Laid-back beach area known for surfing and stilt fishermen.",
                        Arrays.asList(R.drawable.ahangama_beach_1, R.drawable.ahangama_beach_2), 5.9721, 80.3817),
                new Attraction("Stilt Fishermen Viewpoint", "Famous traditional fishing method seen on the coast, great for photography.",
                        Arrays.asList(R.drawable.stilt_fishermen_1, R.drawable.stilt_fishermen_2), 5.9650, 80.3630),
                new Attraction("Kataluwa Purwarama Temple", "Historical temple near Ahangama, known for old murals and peaceful surroundings.",
                        Arrays.asList(R.drawable.kataluwa_temple_1, R.drawable.kataluwa_temple_2), 5.9747, 80.3964)
        ));
        attractionsMap.put("Udugama", Arrays.asList(
                new Attraction("Kottawa Arboretum", "A peaceful forest reserve with nature trails and educational signs, ideal for hikers and school groups.",
                        Arrays.asList(R.drawable.kottawa_arboretum_1, R.drawable.kottawa_arboretum_2), 6.1333, 80.3833)
        ));
        attractionsMap.put("Neluwa", Arrays.asList(
                new Attraction("Lankagama Sinharaja Entrance", "One of the most scenic gateways to Sinharaja Rainforest with streams, waterfalls, and dense jungle.",
                        Arrays.asList(R.drawable.lankagama_1, R.drawable.lankagama_2, R.drawable.lankagama_3, R.drawable.lankagama_4), 6.4134, 80.4870)
        ));
        attractionsMap.put("Thawalama", Arrays.asList(
                new Attraction("Thawalama Waterfall (Ahas Bokkuwa)", "A hidden waterfall near Thawalama village, popular among locals for its natural beauty and peaceful vibe.",
                        Arrays.asList(R.drawable.thawalama_waterfall_1, R.drawable.thawalama_waterfall_2), 6.2455, 80.4701)
        ));
        attractionsMap.put("Mahamodara", Arrays.asList(
                new Attraction("Mahamodara Sea Turtle Hatchery", "A turtle hatchery supporting marine conservation, located closer to Galle than the Hikkaduwa ones.",
                        Arrays.asList(R.drawable.mahamodara_turtle_1, R.drawable.mahamodara_turtle_2, R.drawable.mahamodara_turtle_3), 6.0417, 80.2042)
        ));



       // ---- Attractions for Matara District ----
        attractionsMap.put("Matara", Arrays.asList(
                new Attraction("Matara Fort (Dutch Fort)", "Main Dutch fort near Matara town representing Dutch and British colonial history",
                        Arrays.asList(R.drawable.matara_fort_1, R.drawable.matara_fort_2), 5.9480, 80.5405),
                new Attraction("Star Fort", "Small star-shaped Dutch fort built in 1765 with visible circular design and ramparts, surrounded by a moat and a small guard room",
                        Arrays.asList(R.drawable.star_fort_1, R.drawable.star_fort_2), 5.9499, 80.5455),
                new Attraction("Paravi Duwa Temple", "Small island temple near Matara harbor connected by a new stone bridge, a historic site and popular tourist spot",
                        Arrays.asList(R.drawable.paravi_duwa_1, R.drawable.paravi_duwa_2, R.drawable.paravi_duwa_3), 5.9427, 80.5479),
                new Attraction("Polhena Beach", "Calm coral-protected beach ideal for snorkeling and safe for children, with opportunities to spot sea turtles",
                        Arrays.asList(R.drawable.polhena_beach_1, R.drawable.polhena_beach_2), 5.9379, 80.5404),
                new Attraction("Matara Bodhiya", "Famous Bo tree revered as a significant religious and cultural landmark",
                        Arrays.asList(R.drawable.matara_bodhiya_1, R.drawable.matara_bodhiya_2), 5.9460, 80.5440),
                new Attraction("Weherahena Poorwarama Temple", "Largest tunnel temple in Sri Lanka with intricate murals and statues inside the cave shrine",
                        Arrays.asList(R.drawable.weherahena_temple_1, R.drawable.weherahena_temple_2, R.drawable.weherahena_temple_3), 5.9336, 80.5685),
                new Attraction("Uthpalawanna Sri Vishnu Devalaya", "Ancient Vishnu temple known for its religious significance and traditional swimming area",
                        Arrays.asList(R.drawable.uthpalawanna_vishnu_1, R.drawable.uthpalawanna_vishnu_2), 5.9400, 80.5500)
        ));
        attractionsMap.put("Tangalle", Arrays.asList(
                new Attraction("Tangalle Beach", "Long sandy beach with turquoise waters and a peaceful atmosphere, ideal for sunset watching and relaxing.",
                        Arrays.asList(R.drawable.tangalle_beach_1, R.drawable.tangalle_beach_2, R.drawable.tangalle_beach_3), 6.0222, 80.7912),
                new Attraction("Goyambokka Beach", "A scenic, less crowded beach with palm trees and calm waves, great for swimming and sunbathing.",
                        Arrays.asList(R.drawable.goyambokka_beach_1, R.drawable.goyambokka_beach_2), 6.0351, 80.7910),
                new Attraction("Silent Beach (Marakolliya)", "Secluded beach south of Tangalle, popular among couples and solo travelers looking for tranquility.",
                        Arrays.asList(R.drawable.silent_beach_1, R.drawable.silent_beach_2, R.drawable.silent_beach_3), 6.0105, 80.8040),
                new Attraction("Rekawa Turtle Watch", "Famous turtle nesting site where you can witness turtles laying eggs on the beach (mostly at night).",
                        Arrays.asList(R.drawable.rekawa_turtle_watch_1, R.drawable.rekawa_turtle_watch_2), 6.0364, 80.8737),
                new Attraction("Mulkirigala Raja Maha Viharaya", "Ancient rock temple located slightly inland from Tangalle, featuring cave shrines and paintings.",
                        Arrays.asList(R.drawable.mulkirigala_temple_1, R.drawable.mulkirigala_temple_2, R.drawable.mulkirigala_temple_3), 6.1531, 80.7296)
        ));
        attractionsMap.put("Dondra", Arrays.asList(
                new Attraction("Dondra Head Lighthouse", "Sri Lanka’s southernmost point with ocean views and a tall lighthouse",
                        Arrays.asList(R.drawable.dondra_head_lighthouse_1, R.drawable.dondra_head_lighthouse_3), 5.9186, 80.5925)
        ));
        attractionsMap.put("Weligama", Arrays.asList(
                new Attraction("Weligama Bay", "Popular surfing and stilt fishing area with a laid-back vibe and tourist-friendly cafés",
                        Arrays.asList(R.drawable.weligama_bay_1, R.drawable.weligama_bay_2, R.drawable.weligama_bay_3), 5.9726, 80.4258),
                new Attraction("Kushtarajagala Statue", "Ancient Bodhisattva carving from the 7th century hidden in Weligama town",
                        Arrays.asList(R.drawable.kushtarajagala_statue_1, R.drawable.kushtarajagala_statue_2), 5.9751, 80.4338)
        ));
        attractionsMap.put("Mirissa", Arrays.asList(
                new Attraction("Mirissa Beach", "Famous crescent-shaped beach known for whale watching and nightlife",
                        Arrays.asList(R.drawable.mirissa_beach_1, R.drawable.mirissa_beach_2, R.drawable.mirissa_beach_3, R.drawable.mirissa_beach_4), 5.9436, 80.4563),
                new Attraction("Secret Beach Mirissa", "Hidden bay ideal for photos and peaceful swimming, accessible by a short hike",
                        Arrays.asList(R.drawable.secret_beach_mirissa_1, R.drawable.secret_beach_mirissa_2, R.drawable.secret_beach_mirissa_3), 5.9429, 80.4514)
        ));
        attractionsMap.put("Talalla", Arrays.asList(
                new Attraction("Talalla Beach", "Quiet and less crowded beach with natural beauty",
                        Arrays.asList(R.drawable.talalla_beach_1, R.drawable.talalla_beach_2, R.drawable.talalla_beach_3), 5.9770, 80.5760)
        ));
        attractionsMap.put("Nilwella", Arrays.asList(
                new Attraction("Nilwella Beach", "Beautiful beach with cliffs and calm waters",
                        Arrays.asList(R.drawable.nilwella_beach_1, R.drawable.nilwella_beach_2, R.drawable.nilwella_beach_3), 5.9700, 80.5600)
        ));
        attractionsMap.put("Dickwella", Arrays.asList(
                new Attraction("Dickwella Beach", "Popular beach with calm waters and local amenities",
                        Arrays.asList(R.drawable.dickwella_beach_1, R.drawable.dickwella_beach_2), 6.0100, 80.6000),
                new Attraction("Wewurukannala Vihara", "Giant seated Buddha statue and religious complex",
                        Arrays.asList(R.drawable.wewurukannala_vihara_1, R.drawable.wewurukannala_vihara_2), 6.0050, 80.5950),
                new Attraction("Hummanaya Blowhole", "Second largest blowhole in the world, natural marine phenomenon",
                        Arrays.asList(R.drawable.hummanaya_blowhole_1, R.drawable.hummanaya_blowhole_2), 6.0000, 80.5900)
        ));
        attractionsMap.put("Gandara", Arrays.asList(
                new Attraction("Gandara Beach", "Beautiful beach popular among locals and tourists",
                        Arrays.asList(R.drawable.gandara_beach_1, R.drawable.gandara_beach_2), 5.9600, 80.5650)
        ));
        attractionsMap.put("Kudawella", Arrays.asList(
                new Attraction("Kudawella Beach", "Quiet and scenic beach with rocky outcrops",
                        Arrays.asList(R.drawable.kudawella_beach_1, R.drawable.kudawella_beach_2, R.drawable.kudawella_beach_3), 5.9850, 80.5800)
        ));
        attractionsMap.put("Hiriketiya", Arrays.asList(
                new Attraction("Hiriketiya Beach", "Popular surfing beach with good waves and relaxed atmosphere",
                        Arrays.asList(R.drawable.hiriketiya_beach_1, R.drawable.hiriketiya_beach_2, R.drawable.hiriketiya_beach_3), 6.0030, 80.5940)
        ));
        attractionsMap.put("Thalaramba", Arrays.asList(
                new Attraction("Thalaramba Beach", "Less known beach perfect for peaceful walks and photography",
                        Arrays.asList(R.drawable.thalaramba_beach_1, R.drawable.thalaramba_beach_2), 5.9450, 80.5500)
        ));



        // ---- Attractions for Hambantota District ----
        attractionsMap.put("Hambantota", Arrays.asList(
                new Attraction("Dry Zone Botanical Garden", "A beautifully landscaped garden showcasing dry zone flora.",
                        Arrays.asList(R.drawable.dry_zone_botanical_garden, R.drawable.dry_zone_botanical_garden_1, R.drawable.dry_zone_botanical_garden_2, R.drawable.dry_zone_botanical_garden_3, R.drawable.dry_zone_botanical_garden_4), 6.1242, 81.1183),
                new Attraction("Birds Research Center and Resort", "A unique bird park and research center.",
                        Arrays.asList(R.drawable.birds_research_center_1, R.drawable.birds_research_center, R.drawable.birds_research_center_2, R.drawable.birds_research_center_3), 6.1333, 81.1167),
                new Attraction("Hambantota Martello Tower", "A colonial defense tower with ocean views.",
                        Arrays.asList(R.drawable.hambantota_martello_tower, R.drawable.hambantota_martello_tower_1, R.drawable.hambantota_martello_tower_2), 6.1220, 81.1178),
                new Attraction("Ridiyagama Safari Park", "Sri Lanka’s first open-air safari park with various animals.",
                        Arrays.asList(R.drawable.ridiyagama_safari_park, R.drawable.ridiyagama_safari_park_1, R.drawable.ridiyagama_safari_park_2, R.drawable.ridiyagama_safari_park_3), 6.1567, 81.1513)
        ));
        attractionsMap.put("Tissamaharama", Arrays.asList(
                new Attraction("Tissamaharama Raja Maha Vihara", "An ancient Buddhist monastery with historic significance.",
                        Arrays.asList(R.drawable.tissamaharama_temple, R.drawable.tissamaharama_temple_1, R.drawable.tissamaharama_temple_2), 6.2794, 81.2750),
                new Attraction("Yatala Wehera", "An ancient stupa surrounded by lotus ponds and ruins.",
                        Arrays.asList(R.drawable.yatala_wehera, R.drawable.yatala_wehera_1), 6.2742, 81.2715)
        ));
        attractionsMap.put("Kirinda", Arrays.asList(
                new Attraction("Kirinda Beach & Temple", "A beautiful beach and hilltop Buddhist temple with panoramic ocean views.",
                        Arrays.asList(R.drawable.kirinda_beach_temple, R.drawable.kirinda_beach_temple_1, R.drawable.kirinda_beach_temple_2), 6.2052, 81.3192),
                new Attraction("Kirinda Fishing Harbour", "Small harbor with local fishing boats and sea breeze.",
                        Arrays.asList(R.drawable.kirinda_harbour, R.drawable.kirinda_harbour_1), 6.2088, 81.3184)
        ));
        attractionsMap.put("Palatupana", Arrays.asList(
                new Attraction("Yala National Park (Palatupana Entrance)", "Sri Lanka’s premier national park with leopards, elephants, and scenic landscapes.",
                        Arrays.asList(R.drawable.yala_national_park, R.drawable.yala_national_park_1), 6.3634, 81.4026),
                new Attraction("Palatupana Salt Pans", "Vast salt flats with unique birdlife and photo opportunities.",
                        Arrays.asList(R.drawable.palatupana_salt_pans), 6.3378, 81.3820),
                new Attraction("Patanangala Beach", "A wild beach inside Yala Park near the coastline.",
                        Arrays.asList(R.drawable.patanangala_beach, R.drawable.patanangala_beach_1, R.drawable.patanangala_beach_2), 6.3937, 81.4410)
        ));
        attractionsMap.put("Bundala", Arrays.asList(
                new Attraction("Bundala National Park", "A UNESCO biosphere reserve famous for birdwatching and wetland habitats.",
                        Arrays.asList(R.drawable.bundala_national_park, R.drawable.bundala_national_park_1, R.drawable.bundala_national_park_2, R.drawable.bundala_national_park_3), 6.2000, 81.2167)
        ));
        attractionsMap.put("Suriyawewa", Arrays.asList(
                new Attraction("Mahinda Rajapaksa International Cricket Stadium", "Modern international cricket stadium hosting national events.",
                        Arrays.asList(R.drawable.suriyawewa_stadium, R.drawable.suriyawewa_stadium_1), 6.1658, 81.1153),
                new Attraction("Madunagala Hot Springs", "Natural geothermal hot water springs with separate bathing wells.",
                        Arrays.asList(R.drawable.madunagala_hot_springs, R.drawable.madunagala_hot_springs_1, R.drawable.madunagala_hot_springs_2), 6.1733, 81.1101)
        ));
        attractionsMap.put("Ambalantota", Arrays.asList(
                new Attraction("Ussangoda National Park", "A unique coastal plateau with red soil, legends of meteor impact, and panoramic views.",
                        Arrays.asList(R.drawable.ussangoda_1, R.drawable.ussangoda_2, R.drawable.ussangoda_3, R.drawable.ussangoda_4), 6.0636, 80.9928)
        ));
        attractionsMap.put("Kalametiya", Arrays.asList(
                new Attraction("Kalametiya Bird Sanctuary", "A beautiful coastal wetland with over 150 bird species.",
                        Arrays.asList(R.drawable.kalametiya_bird_sanctuary, R.drawable.kalametiya_bird_sanctuary_1, R.drawable.kalametiya_bird_sanctuary_2), 6.0794, 80.8889)
        ));




    // Western Province
       // ---- Attractions for Colombo District ----
        attractionsMap.put("Colombo", Arrays.asList(
                new Attraction("Gangaramaya Temple & Seema Malaka", "Famous Buddhist temple with lakeside pavilions.",
                        Arrays.asList(R.drawable.gangaramaya_temple, R.drawable.gangaramaya_temple_1, R.drawable.gangaramaya_temple_2, R.drawable.gangaramaya_temple_3), 6.9271, 79.8612),
                new Attraction("Beira Lake", "Scenic lake in the heart of Colombo, with temple views.",
                        Arrays.asList(R.drawable.beira_lake, R.drawable.beira_lake_1), 6.9215, 79.8569),
                new Attraction("Galle Face Green", "Large oceanfront promenade popular for sunsets and leisure.",
                        Arrays.asList(R.drawable.galle_face_green, R.drawable.galle_face_green_1, R.drawable.galle_face_green_2), 6.9279, 79.8449),
                new Attraction("Colombo National Museum", "Sri Lanka’s largest museum, with cultural and historical artifacts.",
                        Arrays.asList(R.drawable.colombo_museum, R.drawable.colombo_museum_1, R.drawable.colombo_museum_2), 6.9175, 79.8608),
                new Attraction("Lotus Tower", "Tallest tower in South Asia with city views.",
                        Arrays.asList(R.drawable.lotus_tower, R.drawable.lotus_tower_1, R.drawable.lotus_tower_2), 6.9397, 79.8575),
                new Attraction("Old Dutch Hospital", "Colonial-era building now with shops and restaurants.",
                        Arrays.asList(R.drawable.old_dutch_hospital_col, R.drawable.old_dutch_hospital_col_1), 6.9344, 79.8415),
                new Attraction("Jami Ul-Alfar Mosque (Red Mosque)", "Iconic red and white patterned mosque in Pettah.",
                        Arrays.asList(R.drawable.red_mosqu, R.drawable.red_mosqu_1, R.drawable.red_mosqu_2), 6.9385, 79.8571),
                new Attraction("Pettah Market & Floating Market", "Busy commercial market with vibrant scenes.",
                        Arrays.asList(R.drawable.pettah_market, R.drawable.pettah_market_1, R.drawable.pettah_market_2), 6.9361, 79.8580),
                new Attraction("Viharamahadevi Park", "Largest park in Colombo with greenery and fountains.",
                        Arrays.asList(R.drawable.viharamahadevi_park, R.drawable.viharamahadevi_park_1, R.drawable.viharamahadevi_park_2), 6.9164, 79.8630),
                new Attraction("Independence Square", "Memorial hall and gardens built for Sri Lanka’s independence.",
                        Arrays.asList(R.drawable.independence_square, R.drawable.independence_square_1, R.drawable.independence_square_2), 6.9064, 79.8680),
                new Attraction("Colombo Port Maritime Museum", "Exhibits on Sri Lanka’s maritime history.",
                        Arrays.asList(R.drawable.colombo_maritime_museum, R.drawable.colombo_maritime_museum_1), 6.9363, 79.8423),
                new Attraction("Wolvendaal Church", "Dutch-era church built in 1749, one of the oldest in Colombo.",
                        Arrays.asList(R.drawable.wolvendaal_church, R.drawable.wolvendaal_church_1), 6.9408, 79.8586),
                new Attraction("St. Lucia’s Cathedral", "Largest and one of the most beautiful Roman Catholic cathedrals in Sri Lanka.",
                        Arrays.asList(R.drawable.st_lucias_cathedral, R.drawable.st_lucias_cathedral_1), 6.9405, 79.8650),
                new Attraction("Dutch Period Museum", "Former Dutch governor’s residence, now a history museum.",
                        Arrays.asList(R.drawable.dutch_period_museum, R.drawable.dutch_period_museum_1), 6.9337, 79.8611),
                new Attraction("Sri Lanka Planetarium", "Dome-shaped planetarium with astronomy shows.",
                        Arrays.asList(R.drawable.planetarium, R.drawable.planetarium_1), 6.8965, 79.8585),
                new Attraction("JDA Perera Art Gallery", "Art gallery showcasing contemporary works.",
                        Arrays.asList(R.drawable.jda_perera_gallery, R.drawable.jda_perera_gallery_1, R.drawable.jda_perera_gallery_2), 6.9102, 79.8680),
                new Attraction("Crow Island Beach Park", "Recreational park and beach in Mattakkuliya.",
                        Arrays.asList(R.drawable.crow_island, R.drawable.crow_island_1, R.drawable.crow_island_2, R.drawable.crow_island_3), 6.9730, 79.8650),
                new Attraction("One Galle Face Mall", "Luxury shopping mall with restaurants and cinema.",
                        Arrays.asList(R.drawable.one_galle_face, R.drawable.one_galle_face_1, R.drawable.one_galle_face_2, R.drawable.one_galle_face_3), 6.9270, 79.8440),
                new Attraction("BMICH (Bandaranaike Memorial International Conference Hall)", "Major convention hall and national event venue with iconic architecture and gardens.",
                        Arrays.asList(R.drawable.bmich, R.drawable.bmich_1, R.drawable.bmich_2), 6.9113, 79.8704),
                new Attraction("Nelum Pokuna Theatre", "Modern performing arts centre near the National Museum.",
                        Arrays.asList(R.drawable.nelum_pokuna, R.drawable.nelum_pokuna_1, R.drawable.nelum_pokuna_2), 6.9118, 79.8619)
        ));
        attractionsMap.put("Dehiwala", Arrays.asList(
                new Attraction("National Zoological Gardens (Dehiwala Zoo)", "Sri Lanka’s main zoo with diverse animals.",
                        Arrays.asList(R.drawable.dehiwala_zoo, R.drawable.dehiwala_zoo_1, R.drawable.dehiwala_zoo_2, R.drawable.dehiwala_zoo_3), 6.8533, 79.8655),
                new Attraction("Mount Lavinia Beach & Hotel", "Popular beach with colonial-style hotel and sunsets.",
                        Arrays.asList(R.drawable.mount_lavinia_beach, R.drawable.mount_lavinia_beach_1), 6.8296, 79.8636),
                new Attraction("Traditional Puppet Art Museum", "Museum showcasing Sri Lanka’s puppet heritage.",
                        Arrays.asList(R.drawable.puppet_museum, R.drawable.puppet_museum_1, R.drawable.puppet_museum_2), 6.8447, 79.8701),
                new Attraction("Bellanwila Rajamaha Viharaya", "One of the important Buddhist temples near Dehiwala.",
                        Arrays.asList(R.drawable.bellanwila_temple, R.drawable.bellanwila_temple_1), 6.8456, 79.9003),
                new Attraction("Wellawatte Beach", "Busy beach popular with locals.",
                        Arrays.asList(R.drawable.wellawatte_beach, R.drawable.wellawatte_beach_1), 6.8745, 79.8602)
        ));

        attractionsMap.put("Sri Jayawardenepura Kotte", Arrays.asList(
                new Attraction("Parliament Complex", "Sri Lanka’s parliament building on an artificial island.",
                        Arrays.asList(R.drawable.parliament_kotte, R.drawable.parliament_kotte_1), 6.9147, 79.9479),
                new Attraction("Diyatha Uyana", "Recreational park with walkways, markets and lake views.",
                        Arrays.asList(R.drawable.diyatha_uyana, R.drawable.diyatha_uyana_1, R.drawable.diyatha_uyana_2, R.drawable.diyatha_uyana_3), 6.9144, 79.9472),
                new Attraction("Beddagana Wetland Park", "Nature park with boardwalks and birdwatching.",
                        Arrays.asList(R.drawable.beddagana_wetland, R.drawable.beddagana_wetland_1, R.drawable.beddagana_wetland_2, R.drawable.beddagana_wetland_3), 6.8983, 79.9300),
                new Attraction("Archaeological Museum Kotte", "Museum dedicated to the history of the Kotte Kingdom.",
                        Arrays.asList(R.drawable.kotte_museum, R.drawable.kotte_museum_1), 6.9015, 79.9470)
        ));
        attractionsMap.put("Ratmalana", Arrays.asList(
                new Attraction("Sri Lanka Air Force Museum", "Aviation museum featuring historic aircraft and exhibits.",
                        Arrays.asList(R.drawable.air_force_museum, R.drawable.air_force_museum_1, R.drawable.air_force_museum_2), 6.8215, 79.8753)
        ));



        // ---- Attractions for Gampaha District ----
        attractionsMap.put("Gampaha", Arrays.asList(
                new Attraction("Henarathgoda Botanical Garden", "Historic botanical garden known for exotic plants and the first rubber tree in Sri Lanka.",
                        Arrays.asList(R.drawable.henarathgoda_garden, R.drawable.henarathgoda_garden_1, R.drawable.henarathgoda_garden_2), 7.0924, 79.9946)
        ));
        attractionsMap.put("Negombo", Arrays.asList(
                new Attraction("Hamilton Canal", "Historic canal connecting Colombo to Negombo, ideal for boat rides.",
                        Arrays.asList(R.drawable.hamilton_canal, R.drawable.hamilton_canal_1, R.drawable.hamilton_canal_2), 7.1300, 79.8600),
                new Attraction("Negombo Beach", "Golden beach with fishing boats and resort hotels.",
                        Arrays.asList(R.drawable.negombo_beach, R.drawable.negombo_beach_1, R.drawable.negombo_beach_2), 7.2083, 79.8358),
                new Attraction("St. Mary’s Church, Negombo", "Magnificent Roman Catholic church known for its painted ceilings.",
                        Arrays.asList(R.drawable.st_marys_negombo, R.drawable.st_marys_negombo_1), 7.2070, 79.8380),
                new Attraction("Dutch Fort, Negombo", "Historic fort built by the Portuguese and later used by the Dutch.",
                        Arrays.asList(R.drawable.dutch_fort_negombo, R.drawable.dutch_fort_negombo_1, R.drawable.dutch_fort_negombo_2), 7.2087, 79.8383),
                new Attraction("Negombo Lagoon", "Large coastal lagoon famous for boat rides and birdlife.",
                        Arrays.asList(R.drawable.negombo_lagoon, R.drawable.negombo_lagoon_1, R.drawable.negombo_lagoon_2), 7.1833, 79.8667),
                new Attraction("St. Stephen’s Church, Negombo", "Historic Anglican church built in 1877 with colonial architecture.",
                        Arrays.asList(R.drawable.st_stephens_church, R.drawable.st_stephens_church_1), 7.2132, 79.8398),
                new Attraction("Angurukaramulla Temple", "Famous Buddhist temple with a giant Buddha statue and murals.",
                        Arrays.asList(R.drawable.angurukaramulla_temple, R.drawable.angurukaramulla_temple_1), 7.2112, 79.8500)
        ));
        attractionsMap.put("Kelaniya", Arrays.asList(
                new Attraction("Kelaniya Raja Maha Viharaya", "Sacred Buddhist temple believed to be visited by Lord Buddha.",
                        Arrays.asList(R.drawable.kelaniya_temple, R.drawable.kelaniya_temple_1), 6.9550, 79.9220),
                new Attraction("Water World Lanka", "Sri Lanka’s only public aquarium and bird park with an underwater tunnel and boat safari.",
                        Arrays.asList(R.drawable.water_world_lanka, R.drawable.water_world_lanka_1, R.drawable.water_world_lanka_2), 6.9812, 79.9745)
        ));
        attractionsMap.put("Ja-Ela", Arrays.asList(
                new Attraction("Guruge Nature Park", "Theme and amusement park combining wildlife, history, and culture.",
                        Arrays.asList(R.drawable.guruge_park, R.drawable.guruge_park_1, R.drawable.guruge_park_2), 7.0733, 79.9021),
                new Attraction("Wattala Beach", "Quiet coastal area ideal for evening walks and sea views.",
                        Arrays.asList(R.drawable.wattala_beach, R.drawable.wattala_beach_1), 6.9870, 79.8775),
                new Attraction("Muthurajawela Marsh", "Vast wetland reserve rich in birdlife and mangroves, accessible by boat.",
                        Arrays.asList(R.drawable.muthurajawela, R.drawable.muthurajawela_1), 7.0967, 79.8783)
        ));
        attractionsMap.put("Katunayake", Arrays.asList(
                new Attraction("Bandaranaike International Airport Observation Deck", "Watch planes take off and land at Sri Lanka’s main airport.",
                        Arrays.asList(R.drawable.katunayake_airport_view, R.drawable.katunayake_airport_view_1, R.drawable.katunayake_airport_view_2), 7.1691, 79.8905)
        ));
        attractionsMap.put("Kirindiwela", Arrays.asList(
                new Attraction("Horagolla Walauwa", "Ancestral home of S.W.R.D. Bandaranaike, showcasing colonial architecture.",
                        Arrays.asList(R.drawable.horagolla_walauwa, R.drawable.horagolla_walauwa_1, R.drawable.horagolla_walauwa_2), 7.1400, 80.0185),
                new Attraction("Horagolla National Park", "Small tropical forest reserve ideal for birdwatching and nature walks.",
                        Arrays.asList(R.drawable.horagolla_park, R.drawable.horagolla_park_1, R.drawable.horagolla_park_2, R.drawable.horagolla_park_3), 7.1423, 80.0135)
        ));
        attractionsMap.put("Hanwella", Arrays.asList(
                new Attraction("Leisure World", "Sri Lanka’s first water and amusement park with slides, rides, and pools for all ages.",
                        Arrays.asList(R.drawable.leisure_world, R.drawable.leisure_world_1, R.drawable.leisure_world_2), 6.9575, 80.0634)
        ));


        /*cityAttractionsMap.put("Panadura", Arrays.asList("Panadura Beach"));
        cityAttractionsMap.put("Beruwala", Arrays.asList("Beruwala Beach"));

        cityAttractionsMap.put("Trincomalee", Arrays.asList("Koneswaram Temple", "Nilaveli Beach"));
        cityAttractionsMap.put("Nilaveli", Arrays.asList("Nilaveli Beach"));
        cityAttractionsMap.put("Uppuveli", Arrays.asList("Uppuveli Beach"));
        cityAttractionsMap.put("Kinniya", Arrays.asList("Kinniya Mosque"));
        cityAttractionsMap.put("Muttur", Arrays.asList("Muttur Lagoon"));

        cityAttractionsMap.put("Batticaloa", Arrays.asList("Batticaloa Fort", "Dutch Canal"));
        cityAttractionsMap.put("Kalkudah", Arrays.asList("Kalkudah Beach"));
        cityAttractionsMap.put("Pasikuda", Arrays.asList("Pasikuda Beach"));
        cityAttractionsMap.put("Eravur", Arrays.asList("Eravur Lagoon"));
        cityAttractionsMap.put("Valaichenai", Arrays.asList("Valaichenai Town"));

        cityAttractionsMap.put("Ampara", Arrays.asList("Ampara Town"));
        cityAttractionsMap.put("Akkaraipattu", Arrays.asList("Akkaraipattu Town"));
        cityAttractionsMap.put("Arugam Bay", Arrays.asList("Arugam Bay Beach"));
        cityAttractionsMap.put("Pottuvil", Arrays.asList("Pottuvil Town"));
        cityAttractionsMap.put("Kalmunai", Arrays.asList("Kalmunai Town"));

        cityAttractionsMap.put("Jaffna", Arrays.asList("Jaffna Fort", "Nallur Kandaswamy Temple"));
        cityAttractionsMap.put("Nallur", Arrays.asList("Nallur Temple"));
        cityAttractionsMap.put("Chavakachcheri", Arrays.asList("Chavakachcheri Town"));
        cityAttractionsMap.put("Point Pedro", Arrays.asList("Point Pedro Lighthouse"));
        cityAttractionsMap.put("Karainagar", Arrays.asList("Karainagar Beaches"));

        cityAttractionsMap.put("Kilinochchi", Arrays.asList("Kilinochchi Town"));
        cityAttractionsMap.put("Paranthan", Arrays.asList("Paranthan Town"));
        cityAttractionsMap.put("Pooneryn", Arrays.asList("Pooneryn Peninsula"));

        cityAttractionsMap.put("Mannar", Arrays.asList("Mannar Fort", "Pesalai Beaches"));
        cityAttractionsMap.put("Pesalai", Arrays.asList("Pesalai Beaches"));
        cityAttractionsMap.put("Thalaimannar", Arrays.asList("Thalaimannar Beaches"));

        cityAttractionsMap.put("Mullaitivu", Arrays.asList("Mullaitivu Beaches"));
        cityAttractionsMap.put("Puthukkudiyiruppu", Arrays.asList("Puthukkudiyiruppu Town"));

        cityAttractionsMap.put("Anuradhapura", Arrays.asList("Ancient City", "Sri Maha Bodhi"));
        cityAttractionsMap.put("Mihintale", Arrays.asList("Mihintale Temple"));
        cityAttractionsMap.put("Nochchiyagama", Arrays.asList("Nochchiyagama Town"));
        cityAttractionsMap.put("Kekirawa", Arrays.asList("Kekirawa Town"));

        cityAttractionsMap.put("Polonnaruwa", Arrays.asList("Ancient Ruins", "Parakrama Samudra"));
        cityAttractionsMap.put("Medirigiriya", Arrays.asList("Medirigiriya Vatadage"));
        cityAttractionsMap.put("Hingurakgoda", Arrays.asList("Hingurakgoda Town"));
        cityAttractionsMap.put("Minneriya", Arrays.asList("Minneriya National Park"));
        cityAttractionsMap.put("Kaduruwela", Arrays.asList("Kaduruwela Town"));

        cityAttractionsMap.put("Kurunegala", Arrays.asList("Kurunegala Lake", "Athugala Rock"));
        cityAttractionsMap.put("Kuliyapitiya", Arrays.asList("Kuliyapitiya Town"));
        cityAttractionsMap.put("Pannala", Arrays.asList("Pannala Town"));
        cityAttractionsMap.put("Narammala", Arrays.asList("Narammala Town"));
        cityAttractionsMap.put("Rideegama", Arrays.asList("Rideegama Town"));
        cityAttractionsMap.put("Ibbagamuwa", Arrays.asList("Ibbagamuwa Town"));

        cityAttractionsMap.put("Puttalam", Arrays.asList("Puttalam Lagoon", "Dutch Fort"));
        cityAttractionsMap.put("Kalpitiya", Arrays.asList("Kalpitiya Beach"));
        cityAttractionsMap.put("Chilaw", Arrays.asList("Chilaw Town"));
        cityAttractionsMap.put("Anamaduwa", Arrays.asList("Anamaduwa Town"));
        cityAttractionsMap.put("Wennappuwa", Arrays.asList("Wennappuwa Town"));
        cityAttractionsMap.put("Norochcholai", Arrays.asList("Norochcholai Power Station"));

        cityAttractionsMap.put("Ratnapura", Arrays.asList("Gem Mining", "Sinharaja Forest Reserve"));
        cityAttractionsMap.put("Balangoda", Arrays.asList("Balangoda Town"));
        cityAttractionsMap.put("Kuruwita", Arrays.asList("Kuruwita Town"));
        cityAttractionsMap.put("Pelmadulla", Arrays.asList("Pelmadulla Town"));
        cityAttractionsMap.put("Eheliyagoda", Arrays.asList("Eheliyagoda Town"));
        cityAttractionsMap.put("Embilipitiya", Arrays.asList("Embilipitiya Town"));

        cityAttractionsMap.put("Kegalle", Arrays.asList("Kegalle Town"));
        cityAttractionsMap.put("Mawanella", Arrays.asList("Mawanella Town"));
        cityAttractionsMap.put("Ruwanwella", Arrays.asList("Ruwanwella Town"));
        cityAttractionsMap.put("Aranayake", Arrays.asList("Aranayake Town"));
        cityAttractionsMap.put("Bulathkohupitiya", Arrays.asList("Bulathkohupitiya Town"));
        cityAttractionsMap.put("Warakapola", Arrays.asList("Warakapola Town"));  */

        return attractionsMap;  // Return all attraction data
    }
}

