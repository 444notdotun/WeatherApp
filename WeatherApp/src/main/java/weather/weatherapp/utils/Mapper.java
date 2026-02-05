package weather.weatherapp.utils;

import tools.jackson.databind.JsonNode;
import weather.weatherapp.data.model.Location;
import weather.weatherapp.dtos.response.CheckWeatherResponse;

public class Mapper {
    public static CheckWeatherResponse mapWeatherResponseToDailyWeatherReport(JsonNode dailyReport,int i) {
        CheckWeatherResponse checkWeatherResponse = new CheckWeatherResponse();
        checkWeatherResponse.setWeather(Switch.weatherCodeToText(dailyReport.get("weather_code").get(i).asInt()));
        checkWeatherResponse.setDate(dailyReport.get("time").get(i).asString());
        checkWeatherResponse.setSunrise(dailyReport.get("sunrise").get(i).asString());
        checkWeatherResponse.setSunset(dailyReport.get("sunset").get(i).asString());
        checkWeatherResponse.setTemperatureMax(dailyReport.get("temperature_2m_max").get(i).asString());
        checkWeatherResponse.setTemperatureMin(dailyReport.get("temperature_2m_min").get(i).asString());
        return  checkWeatherResponse;

    }
    public static Location mapLocationApiToLocation(JsonNode  node) {
        Location location = new Location();
        location.setLon(node.get("lon").asDouble());
        location.setLat(node.get("lat").asDouble());
        location.setDisplay_name(node.get("display_name").asString());
        location.setType(node.get("type").asString());
        return location;
    }
}
