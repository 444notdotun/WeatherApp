package weather.weatherapp.service.Implemetation;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import weather.weatherapp.data.model.Location;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.dtos.response.CheckWeatherResponse;
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
    @SneakyThrows
    @Override
    public List<CheckWeatherResponse> checkWeather(CheckWeatherRequest checkWeatherRequest) {
        Location genCodings= getLatitudeAndLongitude(checkWeatherRequest);
        JsonNode weatherReport= weatherApi.getWeather(genCodings);
        if(weatherReport.has("daily")&& !weatherReport.isEmpty()){
            IO.println("weather report sent");
            JsonNode daily = weatherReport.get("daily");
            return getReport(daily);
        }else {
            throw new Exception("WEATHER API RESPONSE NOT FOUND");
        }

    }

    @SneakyThrows
    private Location getLatitudeAndLongitude(CheckWeatherRequest checkWeatherRequest){
        String getGeoCodingUrl =   "https://nominatim.openstreetmap.org/search?q="+
                URLEncoder.encode(checkWeatherRequest.getLocation(),"UTF-8")+"&format=json&limit=1";
        HttpClient client = HttpClient.newHttpClient();
        IO.println("location: "+checkWeatherRequest.getLocation());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getGeoCodingUrl))
                .header("User-Agent", "JavaWeatherApp/1.0")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper  objectMapper = new ObjectMapper();
      JsonNode node = objectMapper.readTree(response.body());
      if (node.isArray()&& !node.isEmpty()){
          IO.println("location Api good");
          JsonNode  locationNode = node.get(0);
         return Mapper.mapLocationApiToLocation(locationNode);
      }else {
          IO.println("location Api did not return any location");
          throw new Exception("Location not found");
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
