package com.example.DeliveryFeeApplication.deliveryFee;

import com.example.DeliveryFeeApplication.exception.ForbiddenVehicleException;
import com.example.DeliveryFeeApplication.exception.InvalidParameterException;
import com.example.DeliveryFeeApplication.weather.WeatherEntry;
import com.example.DeliveryFeeApplication.weather.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryFeeServiceTest {

    @Mock
    private WeatherService weatherService;
    private DeliveryFeeService service;

    @BeforeEach
    void setUp() {
        service = new DeliveryFeeService(weatherService);
    }

    @Test
    void getDeliveryFee() {

    }

    @Test
    void throwsExceptionWhenInvalidCityName() {
        String cityName = "Haapsalu";
        assertThatThrownBy(() -> service.getDeliveryFee(cityName, "car"))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("Invalid value for parameter 'city': " + cityName);
    }

    @Test
    void throwsExceptionWhenInvalidVehicle() {
        String vehicleType = "plane";
        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", vehicleType))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("Invalid value for parameter 'vehicle': " + vehicleType);
    }


    /*
    invalid ECs:
    vehicle = bike or scooter
    EC1: windspeed > 20
    EC2: phenomenon: glaze
    EC3: phenomenon: hail
    EC4: phenomenon: thunder
     */
    @Test
    void throwsExceptionWhenVehicleIsForbidden() {
        WeatherEntry windSpeedOver20 = new WeatherEntry(1L, "Tallinn", "mist", 123, 20, 25);
        WeatherEntry glaze = new WeatherEntry(1L, "Tallinn", "glaze", 123, 20, 10);
        WeatherEntry hail = new WeatherEntry(1L, "Tallinn", "hail", 123, 20, 10);
        WeatherEntry thunder = new WeatherEntry(1L, "Tallinn", "thunder", 123, 20, 10);


        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(windSpeedOver20);

        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "bike"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(glaze);

        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "bike"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");
        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "scooter"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(hail);

        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "bike"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");
        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "scooter"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(thunder);

        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "bike"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");
        assertThatThrownBy(() -> service.getDeliveryFee("Tallinn", "scooter"))
                .isInstanceOf(ForbiddenVehicleException.class)
                .hasMessageContaining("Usage of selected vehicle type is forbidden");
    }

    /*
    valid ECS:
    EC1: city = tallinn
    EC2: city = tartu
    EC3: city = pärnu
    EC4: vehicle = car
    EC5: vehicle = bike
    EC6: vehicle = scooter
    EC7: airtemp > 0
    EC8: 0 >= airtemp >= -10
    EC9: airtemp < -10
    EC10: 20 >= windspeed >= 10
    EC11: windspeed < 10
    EC12: phenomenon: rain
    EC13: phenomenon: snow
    EC14: phenomenon: sleet
    EC15: phenomenon: other and not invalid
     */
    @Test
    void canCalculateDeliveryFee() {
        WeatherEntry w1 = new WeatherEntry(1L, "Tallinn", "mist", 123, 20, 5); // EC1, EC7, EC11, EC15
        WeatherEntry w2 = new WeatherEntry(1L, "Tartu", "rain", 123, -5, 15); // EC2, EC8, EC10, EC12
        WeatherEntry w3 = new WeatherEntry(1L, "Pärnu", "snow", 123, -20, 10); // EC3, EC9, EC10, EC13
        WeatherEntry w4 = new WeatherEntry(1L, "Tallinn", "sleet", 123, 0, 20); //EC14
        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(w1);
        assertThat(service.getDeliveryFee("Tallinn", "car")).isEqualTo(4); // EC4

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(w2);
        assertThat(service.getDeliveryFee("Tartu", "bike")).isEqualTo(2.5 + 0.5 + 0.5 + 0.5); // EC5

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(w3);
        assertThat(service.getDeliveryFee("Pärnu", "scooter")).isEqualTo(2.5 + 1 + 1); // EC6

        when(weatherService.getLatestEntryByName(anyString()))
                .thenReturn(w4);
        assertThat(service.getDeliveryFee("Tallinn", "bike")).isEqualTo(3 + 0.5 + 0.5 + 1);

    }
}