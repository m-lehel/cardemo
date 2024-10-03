package com.demo.car.car.fueltank;

import com.demo.car.car.database.KeyValueService;
import com.demo.car.car.fueltank.impl.FuelTankImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.demo.car.car.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class FuelTankTest {

    @Mock
    private KeyValueService keyValueService;

    private FuelTank fuelTank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fuelTank = new FuelTankImpl(keyValueService);
    }

    @Test
    void testUseFuelForStartEnoughFuel() throws FuelEmptyException {
        int initialFuelLevel = 100;
        int fuelUsageStart = 20;

        when(keyValueService.getValueIfPresent(FUEL_LEVEL)).thenReturn(String.valueOf(initialFuelLevel));
        when(keyValueService.getValueIfPresent(FUEL_USAGE_START)).thenReturn(String.valueOf(fuelUsageStart));

        fuelTank.useFuelForStart();

        verify(keyValueService).setValue(FUEL_LEVEL, String.valueOf(initialFuelLevel - fuelUsageStart));
    }

    @Test
    void testUseFuelForStartNotEnoughFuel() {
        int initialFuelLevel = 10;
        int fuelUsageStart = 20;

        when(keyValueService.getValueIfPresent(FUEL_LEVEL)).thenReturn(String.valueOf(initialFuelLevel));
        when(keyValueService.getValueIfPresent(FUEL_USAGE_START)).thenReturn(String.valueOf(fuelUsageStart));

        FuelEmptyException exception = assertThrows(FuelEmptyException.class, fuelTank::useFuelForStart);
        assertEquals("Not enough fuel for engine start!", exception.getMessage());
    }

    @Test
    void refuelTest() {
        String fuelTankCapacity = "123";

        when(keyValueService.getValueIfPresent(FUEL_TANK_CAPACITY)).thenReturn(fuelTankCapacity);

        fuelTank.refuel();
        verify(keyValueService).setValue(FUEL_LEVEL, fuelTankCapacity);
    }

}
