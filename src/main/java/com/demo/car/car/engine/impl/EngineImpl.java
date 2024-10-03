package com.demo.car.car.engine.impl;

import com.demo.car.car.engine.Engine;
import com.demo.car.car.engine.EngineFailureException;
import com.demo.car.car.fueltank.FuelEmptyException;
import com.demo.car.car.fueltank.FuelTank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class EngineImpl implements Engine {

    private final FuelTank fuelTank;

    @Autowired
    public EngineImpl(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    @Override
    public void start() throws EngineFailureException, FuelEmptyException {
        // Perform engine start...
        fuelTank.useFuelForStart();
    }

    @Override
    public void stop() throws EngineFailureException {
        // Perform engine shutdown...
    }

}
