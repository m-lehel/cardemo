package com.demo.car.car.brake;

/**
 * Marker Exception type for brake failures.
 *
 * @author lehel.michailovits
 */
public final class BrakeFailureException extends Exception {

    public BrakeFailureException(String message) {
        super(message);
    }

}
