package com.demo.car;

import com.demo.car.car.database.KeyValue;
import com.demo.car.car.database.KeyValueService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class KeyValueServiceTestImpl implements KeyValueService {

    private final Map<String, KeyValue> db = new LinkedHashMap<>();

    @Override
    public Optional<KeyValue> getValue(String key) {
        return Optional.ofNullable(db.get(key));
    }

    @Override
    public String getValueIfPresent(String key) {
        return getValue(key).map(KeyValue::getValue).orElse(null);
    }

    @Override
    public void setValue(String key, String value) {
        db.put(key, new KeyValue(key, value));
    }

    @Override
    public void setValueIfAbsent(String key, String value) {
        db.computeIfAbsent(key, (_k) -> new KeyValue(key, value));
    }
}
