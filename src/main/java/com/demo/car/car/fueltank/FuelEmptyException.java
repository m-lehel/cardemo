package com.demo.car.car.fueltank;

/**
 * Marker Exception type thrown if the fuel tank is empty.
 *
 * @author lehel.michailovits
 */
public final class FuelEmptyException extends Exception {

    public FuelEmptyException(String message) {
        super(message);
    }

}
