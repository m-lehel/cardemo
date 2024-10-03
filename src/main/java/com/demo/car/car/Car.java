package com.demo.car.car;

import com.demo.car.car.brake.Brake;
import static com.demo.car.car.constants.Constants.*;

import com.demo.car.car.constants.CarStatus;
import com.demo.car.car.dashboard.Dashboard;
import com.demo.car.car.database.KeyValueService;
import com.demo.car.car.engine.Engine;
import com.demo.car.car.fueltank.FuelTank;
import com.demo.car.car.util.RunnableWithException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public final class Car {

    private static final Logger logger = LoggerFactory.getLogger(Car.class);

    private final Engine engine;
    private final FuelTank fuelTank;
    private final Brake brake;
    private final Dashboard dashboard;
    private final KeyValueService keyValueService;

    @Autowired
    public Car(Engine engine, FuelTank fuelTank, Brake brake, Dashboard dashboard, KeyValueService keyValueService) {
        this.engine = engine;
        this.fuelTank = fuelTank;
        this.brake = brake;
        this.dashboard = dashboard;
        this.keyValueService = keyValueService;
        initializeDB(keyValueService);
    }

    private static void initializeDB(KeyValueService keyValueService) {
        keyValueService.setValue(FUEL_TANK_CAPACITY, "50");
        keyValueService.setValue(FUEL_USAGE_START, "5");

        keyValueService.setValueIfAbsent(FUEL_LEVEL, "50");
        keyValueService.setValueIfAbsent(CAR_STATUS, CarStatus.STOPPED.toString());
    }

    @GetMapping("/")
    public String getHelpMessage() {
        logger.trace("Request @ /");

        return """
            The following functions are available for the car:<br>
            \t- start the car at /start<br>
            \t- stop the car at /stop<br>
            \t- refuel the car at /refuel<br>
            \t- display the fuel level at /fuel-level<br>
            \t- display the dashboard at /dashboard<br>
        """;
    }

    @GetMapping("/start")
    public String start() {
        logger.trace("Request @ /start");

        String startResponse = handleStart();
        logger.debug(startResponse);
        return startResponse;
    }

    private String handleStart() {
        if (isRunning()) {
            return "Car is already started!";
        }

        String error = startModules(
            brake::startSystem,
            engine::start
        );

        keyValueService.setValue(ERROR, error);
        if (!error.isEmpty()) {
            return error;
        }

        keyValueService.setValue(CAR_STATUS, CarStatus.RUNNING.toString());

        return "Car started successfully!";
    }

    @GetMapping("/stop")
    public String stop() {
        logger.trace("Request @ /stop");

        String stopResponse = handleStop();
        logger.debug(stopResponse);
        return stopResponse;
    }

    private String handleStop() {
        if (!isRunning()) {
            return "Car is already stopped!";
        }

        String error = stopModules(
            engine::stop,
            brake::stopSystem
        );

        keyValueService.setValue(CAR_STATUS, CarStatus.STOPPED.toString());

        keyValueService.setValue(ERROR, error);
        if (!error.isEmpty()) {
            return error;
        }

        return "Car stopped successfully!";
    }

    @GetMapping("/refuel")
    public String refuel() {
        logger.trace("Request @ /refuel");

        String refuelResponse = handleRefuel();
        logger.debug(refuelResponse);
        return refuelResponse;
    }

    private String handleRefuel() {
        fuelTank.refuel();
        return "The car is refueled successfully!";
    }

    @GetMapping("/fuel-level")
    public String getFuelLevel() {
        logger.trace("Request @ /fuel-level");

        String fuelLevelResponse = handleFuelLevel();
        logger.debug(fuelLevelResponse);
        return fuelLevelResponse;
    }

    private String handleFuelLevel() {
        String fuelLevel = keyValueService.getValueIfPresent(FUEL_LEVEL);
        return String.format("Current fuel level: %s liters", fuelLevel);
    }

    @GetMapping("/dashboard")
    public String displayDashboard() {
        logger.trace("Request @ /dashboard");

        return dashboard.display();
    }

    private boolean isRunning() {
        System.out.println(keyValueService.getValueIfPresent(CAR_STATUS));
        return switch (CarStatus.valueOf(keyValueService.getValueIfPresent(CAR_STATUS))) {
            case CarStatus.STOPPED -> false;
            case CarStatus.RUNNING -> true;
        };
    }

    private static String startModules(RunnableWithException... moduleStarters) {
        for (RunnableWithException moduleStarter : moduleStarters) {
            try {
                moduleStarter.run();
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "";
    }

    private static String stopModules(RunnableWithException... moduleStoppers) {
        List<String> errors = new ArrayList<>();
        for (RunnableWithException moduleStopper : moduleStoppers) {
            try {
                moduleStopper.run();
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }
        return String.join(" ", errors);
    }

}
