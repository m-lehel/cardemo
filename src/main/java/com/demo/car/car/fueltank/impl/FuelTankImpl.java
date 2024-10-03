package com.demo.car.car.fueltank.impl;

import static com.demo.car.car.constants.Constants.*;
import com.demo.car.car.database.KeyValueService;
import com.demo.car.car.fueltank.FuelEmptyException;
import com.demo.car.car.fueltank.FuelTank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class FuelTankImpl implements FuelTank {

    private final KeyValueService keyValueService;

    @Override
    public void useFuelForStart() throws FuelEmptyException {
        int currentFuelLevel = Integer.parseInt(keyValueService.getValueIfPresent(FUEL_LEVEL));
        int fuelUsageStart = Integer.parseInt(keyValueService.getValueIfPresent(FUEL_USAGE_START));

        if (currentFuelLevel < fuelUsageStart) {
            throw new FuelEmptyException("Not enough fuel for engine start!");
        }

        int newFuelLevel = currentFuelLevel - fuelUsageStart;
        keyValueService.setValue(FUEL_LEVEL, String.valueOf(newFuelLevel));
    }

    @Override
    public void refuel() {
        String maxFuelLevel = keyValueService.getValueIfPresent(FUEL_TANK_CAPACITY);
        keyValueService.setValue(FUEL_LEVEL, maxFuelLevel);
    }

}
