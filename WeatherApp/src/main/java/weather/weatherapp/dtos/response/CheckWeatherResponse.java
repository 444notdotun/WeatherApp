package weather.weatherapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class CheckWeatherResponse {
    @JsonProperty("time")
    private String date;
    @JsonProperty("weather_code")
    private String weather;
    @JsonProperty("temperature_2m_max")
    private String temperatureMax;
    @JsonProperty("temperature_2m_min")
    private String temperatureMin;
    private String sunset;
    private String sunrise;


}
