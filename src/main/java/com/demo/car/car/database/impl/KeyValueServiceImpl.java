package com.demo.car.car.database.impl;

import com.demo.car.car.database.KeyValue;
import com.demo.car.car.database.KeyValueRepository;
import com.demo.car.car.database.KeyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class KeyValueServiceImpl implements KeyValueService {

    private final KeyValueRepository repository;

    @Override
    public Optional<KeyValue> getValue(String key) {
        return repository.findById(key);
    }

    @Override
    public String getValueIfPresent(String key) {
        return getValue(key).map(KeyValue::getValue).orElse(null);
    }

    @Override
    public void setValue(String key, String value) {
        repository.save(new KeyValue(key, value));
    }

    @Override
    public void setValueIfAbsent(String key, String value) {
        if (getValue(key).isEmpty()) {
            setValue(key, value);
        }
    }

}
