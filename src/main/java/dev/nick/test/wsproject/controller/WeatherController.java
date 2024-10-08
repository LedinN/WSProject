package dev.nick.test.wsproject.controller;

import dev.nick.test.wsproject.model.WeatherData;
import dev.nick.test.wsproject.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{location}")
    public Mono<ResponseEntity<WeatherData>> getWeather(@PathVariable String location) {
        return weatherService.getCurrentWeather(location)
                .map(ResponseEntity::ok);

    }

}
