package weather.weatherapp.utils;

public class Switch {
    public static String weatherCodeToText(int code) {
        return switch (code) {
            case 0 -> "Clear sky";

            case 1, 2, 3 -> "Partly cloudy";

            case 45, 48 -> "Fog";

            case 51, 53, 55 -> "Drizzle";
            case 56, 57 -> "Freezing drizzle";

            case 61, 63, 65 -> "Rain";
            case 66, 67 -> "Freezing rain";

            case 71, 73, 75 -> "Snow fall";
            case 77 -> "Snow grains";

            case 80, 81, 82 -> "Rain showers";

            case 85, 86 -> "Snow showers";

            case 95 -> "Thunderstorm";
            case 96, 99 -> "Thunderstorm with hail";

            default -> "Unknown weather";
        };
    }
}
