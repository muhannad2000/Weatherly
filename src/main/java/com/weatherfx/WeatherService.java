package com.weatherfx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherService {

    public static String getWeather(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

            String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name="
                    + encodedCity + "&count=1";

            String geoResult = readUrl(geoUrl);

            if (!geoResult.contains("\"latitude\"")) {
                return "City not found.";
            }

            double latitude = extractDouble(geoResult, "\"latitude\":");
            double longitude = extractDouble(geoResult, "\"longitude\":");

            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude="
                    + latitude + "&longitude=" + longitude + "&current_weather=true";

            String weatherResult = readUrl(weatherUrl);

            int weatherIndex = weatherResult.indexOf("\"current_weather\":");
            int tempIndex = weatherResult.indexOf("\"temperature\":", weatherIndex);
            int windIndex = weatherResult.indexOf("\"windspeed\":", weatherIndex);

            double temperature = extractDoubleFromIndex(weatherResult, tempIndex, "\"temperature\":");
            double windSpeed = extractDoubleFromIndex(weatherResult, windIndex, "\"windspeed\":");

            return "Temperature: " + temperature + "°C"
                    + "\nWind Speed: " + windSpeed + " km/h";

        } catch (Exception e) {
            return "Error fetching weather.";
        }
    }

    private static String readUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        return response.toString();
    }

    private static double extractDouble(String text, String key) {
        int index = text.indexOf(key);
        return extractDoubleFromIndex(text, index, key);
    }

    private static double extractDoubleFromIndex(String text, int index, String key) {
        int start = index + key.length();
        int end = text.indexOf(",", start);
        String value = text.substring(start, end);
        return Double.parseDouble(value);
    }
}