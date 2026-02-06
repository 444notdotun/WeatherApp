package weather.weatherapp.service.Implemetation;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import weather.weatherapp.data.model.Location;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.dtos.response.CheckWeatherResponse;
import weather.weatherapp.exception.WeatherException;
import weather.weatherapp.service.Interface.UserService;
import weather.weatherapp.utils.Mapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    WeatherApi weatherApi;
    @Autowired
    LocationApi locationApi;
    @Override
    public List<CheckWeatherResponse> checkWeather(CheckWeatherRequest checkWeatherRequest) {
        Location resultOfLocationApi= locationApi.getLatitudeAndLongitude(checkWeatherRequest);
        JsonNode weatherReport= weatherApi.getWeather(resultOfLocationApi);
        validateWeatherReport(weatherReport);
        IO.println("weather report sent");
        JsonNode dailyReport = weatherReport.get("daily");
        IO.println(getReport(dailyReport));
        return getReport(dailyReport);
    }


    private void validateWeatherReport(JsonNode weatherReport){
        if(!weatherReport.has("daily")&& weatherReport.isEmpty()){
            IO.println("weather Api not working");
            throw new WeatherException("weather report not sent");
        }
    }



    private List<CheckWeatherResponse> getReport(JsonNode dailyReport){
        List<CheckWeatherResponse> responses= new ArrayList<>();
        for(int i = 0; i < dailyReport.get("weather_code").size(); i++) {
            responses.add(Mapper.mapWeatherResponseToDailyWeatherReport(dailyReport,i));
        }
        return  responses;
    }

}
