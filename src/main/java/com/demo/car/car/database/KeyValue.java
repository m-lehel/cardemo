package com.demo.car.car.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A key-value pair for persisting application states.
 *
 * @author lehel.michailovits
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class KeyValue {

    @Id
    private String key;
    private String value;

}
