package weather.weatherapp.service.Implemetation;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import weather.weatherapp.data.model.Location;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
public class WeatherApi {
    public JsonNode getWeather(Location location) throws IOException, InterruptedException {
        String weatherApiUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=sunrise,sunset,temperature_2m_max,temperature_2m_min,weather_code",location.getLat(),location.getLon());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weatherApiUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode weatherReport = mapper.readTree(response.body());
        IO.println(weatherReport);
        return  weatherReport;
    }
}
