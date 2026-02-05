package weather.weatherapp;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.service.Interface.UserService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    CheckWeatherRequest checkWeatherRequest;
    @Autowired
    UserService  userService;

    @Test
    @SneakyThrows
    void testThatUserCanSeeWeatherUpdate(){
        checkWeatherRequest = new CheckWeatherRequest();
        checkWeatherRequest.setLocation("Lagos");
            assertEquals(7,userService.checkWeather(checkWeatherRequest).size());
    }


}
