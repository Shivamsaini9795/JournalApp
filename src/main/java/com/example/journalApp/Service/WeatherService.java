package com.example.journalApp.Service;

import com.example.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apikey="983366b8f495df0453e48573e606e10a";
    private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=City";

    @Autowired
    private RestTemplate restTemplate;
    
    public WeatherResponse getWeather(String city)
    {
        String finalAPI = API.replace("City", city).replace("API_KEY", apikey);
        ResponseEntity<WeatherResponse> responce = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = responce.getBody();
        return body;
    }
}
