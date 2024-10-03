package com.demo.car.car.engine;

import com.demo.car.car.database.KeyValueService;
import com.demo.car.car.engine.impl.EngineImpl;
import com.demo.car.car.fueltank.FuelEmptyException;
import com.demo.car.car.fueltank.FuelTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EngineTest {

    @Mock
    private FuelTank fuelTank;

    private Engine engine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        engine = new EngineImpl(fuelTank);
    }

    @Test
    void testStartStop() throws Exception {
        doNothing().when(fuelTank).useFuelForStart();

        engine.start();
        engine.stop();
        engine.start();
        engine.stop();

        verify(fuelTank, times(2)).useFuelForStart();
    }

    @Test
    void testStartNotEnoughFuel() throws Exception {
        doThrow(new FuelEmptyException("Test fuel empty")).when(fuelTank).useFuelForStart();

        FuelEmptyException e = assertThrows(FuelEmptyException.class, engine::start);

        assertEquals("Test fuel empty", e.getMessage());
        verify(fuelTank).useFuelForStart();
    }

}
