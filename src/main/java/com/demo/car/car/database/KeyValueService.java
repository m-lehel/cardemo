package com.demo.car.car.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling the key-value database entries.
 *
 * @author lehel.michailovits
 */
public interface KeyValueService {

    /**
     * Return an Optional of a key-value pair.
     *
     * @param key the key of the pair
     * @return the optional key-value pair
     */
    Optional<KeyValue> getValue(String key);

    /**
     * Return the String value of the given key, if present, <code>null</code> otherwise.
     * @return the value or <code>null</code> if not present
     */
    String getValueIfPresent(String key);

    /**
     * Creates or updates a key-value pair.
     *
     * @param key the key of the pair
     * @param value the value of the pair
     */
    void setValue(String key, String value);

    /**
     * Creates a new key-value pair, if a pair with the same key was not present.
     *
     * @param key the key of the pair
     * @param value the value of the pair
     */
    void setValueIfAbsent(String key, String value);

}
