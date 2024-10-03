package com.demo.car.car.engine;

import com.demo.car.car.fueltank.FuelEmptyException;

/**
 * Represents the car engine.
 *
 * @author lehel.michailovits
 */
public interface Engine {

    /**
     * Attempts to start the engine.
     *
     * @throws EngineFailureException if the engine start is unsuccessful
     * @throws FuelEmptyException if there is not enough fuel is available for engine start
     */
    void start() throws EngineFailureException, FuelEmptyException;

    /**
     * Attempts to stop the engine.
     *
     * @throws EngineFailureException if the engine stop is unsuccessful
     */
    void stop() throws EngineFailureException;

}
