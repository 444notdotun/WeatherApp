package weather.weatherapp.service.Implemetation;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import weather.weatherapp.data.model.Location;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.exception.WeatherException;
import weather.weatherapp.utils.Mapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class LocationApi {
    @SneakyThrows
    public Location getLatitudeAndLongitude(String checkWeatherRequest){
        String getGeoCodingUrl =   "https://nominatim.openstreetmap.org/search?q="+
                URLEncoder.encode(checkWeatherRequest,"UTF-8")+"&format=json&limit=1";
        HttpClient client = HttpClient.newHttpClient();
        IO.println("location: "+checkWeatherRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getGeoCodingUrl))
                .header("User-Agent", "JavaWeatherApp/1.0")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response.body());
        if (node.isArray()&& !node.isEmpty()){
            IO.println("location Api good");
            JsonNode  locationNode = node.get(0);
            return Mapper.mapLocationApiToLocation(locationNode);
        }else {
            IO.println("location Api did not return any location");
            throw new WeatherException("Location not found");
        }
    }
}
