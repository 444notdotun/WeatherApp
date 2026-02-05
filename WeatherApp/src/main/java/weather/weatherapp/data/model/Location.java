package weather.weatherapp.data.model;

import lombok.Data;

@Data
public class Location {
    public double lat;
    public double lon;
    public String display_name;
    public String type;
}
