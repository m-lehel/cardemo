package com.demo.car.car.constants;

import lombok.Getter;

@Getter
public enum CarStatus {

    STOPPED("The car is stopped"),
    RUNNING("The car is running");

    private final String displayMessage;

    CarStatus(String displayMessage) {
        this.displayMessage = displayMessage;
    }

}
