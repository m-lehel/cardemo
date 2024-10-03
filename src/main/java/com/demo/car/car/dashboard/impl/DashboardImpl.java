package com.demo.car.car.dashboard.impl;

import static com.demo.car.car.constants.Constants.*;

import com.demo.car.car.constants.CarStatus;
import com.demo.car.car.dashboard.Dashboard;
import com.demo.car.car.database.KeyValue;
import com.demo.car.car.database.KeyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class DashboardImpl implements Dashboard {

    private final KeyValueService keyValueService;

    @Override
    public String display() {
        String status = CarStatus.valueOf(keyValueService.getValueIfPresent(CAR_STATUS)).getDisplayMessage();
        String lastError = keyValueService.getValue(ERROR).map(KeyValue::getValue).orElse("");
        double fuelLevel = Double.parseDouble(keyValueService.getValueIfPresent(FUEL_LEVEL));
        double fuelTankCapacity = Double.parseDouble(keyValueService.getValueIfPresent(FUEL_TANK_CAPACITY));

        return String.format("""
            Status: %s<br>
            Error: %s<br>
            Fuel level: %3.0f%%<br>
        """, status, lastError, (fuelLevel / fuelTankCapacity) * 100.0);
    }

}
