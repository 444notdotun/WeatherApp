package weather.weatherapp.service.Interface;

import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.dtos.response.CheckWeatherResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<CheckWeatherResponse> checkWeather(String checkWeatherRequest) throws UnsupportedEncodingException;
}
