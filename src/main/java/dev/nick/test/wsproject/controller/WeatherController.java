package dev.nick.test.wsproject.controller;

import dev.nick.test.wsproject.model.WeatherData;
import dev.nick.test.wsproject.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WebClient weatherWebClient;
    private final ApiService apiService;

    public WeatherController(WebClient.Builder webClient, ApiService apiService){
        this.weatherWebClient = webClient
                .baseUrl("https://api.tomorrow.io/v4/weather/realtime")
                .build();
        this.apiService = apiService;
    }

    @GetMapping("/{location}")
    public Mono<ResponseEntity<WeatherData>> getWeather(@PathVariable String location){

        String apiKey = apiService.getApiKey();

        return weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("location", location)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherData.class)
                .map(ResponseEntity::ok);
    }

}
