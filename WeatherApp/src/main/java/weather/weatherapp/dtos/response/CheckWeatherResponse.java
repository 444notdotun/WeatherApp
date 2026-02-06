package weather.weatherapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class CheckWeatherResponse {
    private String date;
    private String weather;
    private String temperatureMax;
    private String temperatureMin;
    private String sunset;
    private String sunrise;


}
