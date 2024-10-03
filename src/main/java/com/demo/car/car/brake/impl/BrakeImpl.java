package com.demo.car.car.brake.impl;

import com.demo.car.car.brake.Brake;
import com.demo.car.car.brake.BrakeFailureException;
import org.springframework.stereotype.Component;

@Component
public final class BrakeImpl implements Brake {

    @Override
    public void startSystem() throws BrakeFailureException {
        // Perform brake system start...
    }

    @Override
    public void stopSystem() throws BrakeFailureException {
        // Perform brake system shutdown...
    }

}
