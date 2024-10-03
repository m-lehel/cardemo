package com.demo.car.car.engine;

/**
 * Marker Exception type for engine failures.
 *
 * @author lehel.michailovits
 */
public final class EngineFailureException extends Exception {

    public EngineFailureException(String message) {
        super(message);
    }

}
