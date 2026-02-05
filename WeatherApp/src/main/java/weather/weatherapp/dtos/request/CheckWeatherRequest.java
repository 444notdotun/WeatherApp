package weather.weatherapp.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckWeatherRequest {
@NotNull(message = "can not be null")
@NotBlank(message = "can not be blank")
    private String Location;

}
