package com.demo.car.car.brake;

/**
 * Represents the car brake system.
 *
 * @author lehel.michailovits
 */
public interface Brake {

    /**
     * Attempts to start the brake system.
     *
     * @throws BrakeFailureException if the brake system start is unsuccessful
     */
    void startSystem() throws BrakeFailureException;

    /**
     * Attempts to stop the brake system.
     *
     * @throws BrakeFailureException if the brake system stop is unsuccessful
     */
    void stopSystem() throws BrakeFailureException;

}
