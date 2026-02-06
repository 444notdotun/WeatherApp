package weather.weatherapp;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weather.weatherapp.dtos.request.CheckWeatherRequest;
import weather.weatherapp.service.Interface.UserService;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    String checkWeatherRequest;
    @Autowired
    UserService  userService;

    @Test
    @SneakyThrows
    void testThatUserCanSeeWeatherUpdate(){
        checkWeatherRequest=("Lagos");
            assertEquals(7,userService.checkWeather(checkWeatherRequest).size());
    }

    @Test
    void testThatWrongAddressThrowsException(){
        checkWeatherRequest=("ghsyukhs");
        assertThrows(Exception.class,()->userService.checkWeather(checkWeatherRequest));
    }


}
