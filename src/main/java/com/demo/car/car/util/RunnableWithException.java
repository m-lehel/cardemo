package com.demo.car.car.util;

/**
 * Represents an operation that does not return a result and may throw an Exception.
 *
 * @author lehel.michailovits
 */
@FunctionalInterface
public interface RunnableWithException {

    /**
     * Runs this operation.
     *
     * @throws Exception on failure
     */
    void run() throws Exception;

}
