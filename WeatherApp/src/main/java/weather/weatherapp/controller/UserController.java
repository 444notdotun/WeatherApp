package weather.weatherapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.dtos.response.CheckWeatherResponse;
import weather.weatherapp.service.Interface.UserService;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/CheckWeather")
    public ResponseEntity<?> CheckWeather(@Valid @RequestBody CheckWeatherRequest checkWeatherRequest) {
        try{
            List<CheckWeatherResponse> responses = userService.checkWeather(checkWeatherRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
