package com.demo.car.car.dashboard;

import com.demo.car.car.constants.CarStatus;
import com.demo.car.car.dashboard.impl.DashboardImpl;
import com.demo.car.car.database.KeyValue;
import com.demo.car.car.database.KeyValueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.demo.car.car.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class DashboardTest {

    @Mock
    private KeyValueService keyValueService;

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dashboard = new DashboardImpl(keyValueService);
    }

    @Test
    void testDisplayError() {
        CarStatus status = CarStatus.RUNNING;
        String lastError = "Some error";
        double fuelLevel = 50.0;
        double fuelTankCapacity = 100.0;

        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(status.toString());
        when(keyValueService.getValue(ERROR)).thenReturn(Optional.of(new KeyValue(ERROR, lastError)));
        when(keyValueService.getValueIfPresent(FUEL_LEVEL)).thenReturn(String.valueOf(fuelLevel));
        when(keyValueService.getValueIfPresent(FUEL_TANK_CAPACITY)).thenReturn(String.valueOf(fuelTankCapacity));

        String result = dashboard.display();

        String expectedOutput = String.format("""
            Status: %s<br>
            Error: %s<br>
            Fuel level: %3.0f%%<br>
        """, status.getDisplayMessage(), lastError, (fuelLevel / fuelTankCapacity) * 100.0);

        assertEquals(expectedOutput, result);
    }

    @Test
    void testDisplayNoError() {
        CarStatus status = CarStatus.RUNNING;
        double fuelLevel = 66.0;
        double fuelTankCapacity = 3.0;

        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(status.toString());
        when(keyValueService.getValue(ERROR)).thenReturn(Optional.empty());
        when(keyValueService.getValueIfPresent(FUEL_LEVEL)).thenReturn(String.valueOf(fuelLevel));
        when(keyValueService.getValueIfPresent(FUEL_TANK_CAPACITY)).thenReturn(String.valueOf(fuelTankCapacity));

        String result = dashboard.display();

        String expectedOutput = String.format("""
            Status: %s<br>
            Error: %s<br>
            Fuel level: %3.0f%%<br>
        """, status.getDisplayMessage(), "", (fuelLevel / fuelTankCapacity) * 100.0);

        assertEquals(expectedOutput, result);
    }

}
