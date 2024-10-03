package com.demo.car.car.fueltank;

/**
 * Represents the car fuel tank.
 *
 * @author lehel.michailovits
 */
public interface FuelTank {

    /**
     * Attempts to use the fuel required for the engine start.
     *
     * @throws FuelEmptyException if there is not enough fuel is available for engine start
     */
    void useFuelForStart() throws FuelEmptyException;

    /**
     * Tops up the fuel tank.
     */
    void refuel();

}
