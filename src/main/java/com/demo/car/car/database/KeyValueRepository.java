package com.demo.car.car.database;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the key-value pairs.
 *
 * @author lehel.michailovits
 */
public interface KeyValueRepository extends JpaRepository<KeyValue, String> {}
