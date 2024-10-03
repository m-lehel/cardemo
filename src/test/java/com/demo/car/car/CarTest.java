package com.demo.car.car;

import com.demo.car.CarApplication;
import com.demo.car.car.brake.Brake;
import com.demo.car.car.brake.BrakeFailureException;
import com.demo.car.car.constants.CarStatus;
import com.demo.car.car.dashboard.Dashboard;
import com.demo.car.car.database.KeyValueService;
import com.demo.car.car.engine.Engine;
import com.demo.car.car.engine.EngineFailureException;
import com.demo.car.car.fueltank.FuelTank;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static com.demo.car.car.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = CarApplication.class
)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
final class CarTest {

    @Autowired
    private Car car;

    @MockBean
    private Engine engine;
    @MockBean
    private FuelTank fuelTank;
    @MockBean
    private Brake brake;
    @MockBean
    private Dashboard dashboard;
    @MockBean
    private KeyValueService keyValueService;

    @Test
    void testStartNoFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.STOPPED.toString());
        doNothing().when(brake).startSystem();
        doNothing().when(engine).start();

        String response = car.start();
        assertEquals("Car started successfully!", response);

        verify(brake).startSystem();
        verify(engine).start();

        verify(keyValueService).setValue(ERROR, "");
        verify(keyValueService).setValue(CAR_STATUS, CarStatus.RUNNING.toString());
    }

    @Test
    void testStartAlreadyStarted() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.RUNNING.toString());

        String response = car.start();
        assertEquals("Car is already started!", response);

        verify(brake, never()).startSystem();
        verify(engine, never()).start();

        verify(keyValueService, never()).setValue(eq(CAR_STATUS), any(String.class));
        verify(keyValueService, never()).setValue(eq(ERROR), any(String.class));
    }

    @Test
    void testStartEngineFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.STOPPED.toString());
        doNothing().when(brake).startSystem();
        doThrow(new EngineFailureException("Test engine failure")).when(engine).start();

        String response = car.start();
        assertEquals("Test engine failure", response);

        verify(brake).startSystem();
        verify(engine).start();

        verify(keyValueService).setValue(ERROR, "Test engine failure");
        verify(keyValueService, never()).setValue(eq(CAR_STATUS), any(String.class));
    }

    @Test
    void testStartBrakeSystemFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.STOPPED.toString());
        doThrow(new BrakeFailureException("Test brake failure")).when(brake).startSystem();
        doNothing().when(engine).start();

        String response = car.start();
        assertEquals("Test brake failure", response);

        verify(brake).startSystem();
        verify(engine, never()).start();

        verify(keyValueService).setValue(ERROR, "Test brake failure");
        verify(keyValueService, never()).setValue(eq(CAR_STATUS), any(String.class));
    }

    @Test
    void testStartBrakeSystemAndEngineFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.STOPPED.toString());
        doThrow(new BrakeFailureException("Test brake failure")).when(brake).startSystem();
        doThrow(new EngineFailureException("Test engine failure")).when(engine).start();

        String response = car.start();
        assertEquals("Test brake failure", response);

        verify(brake).startSystem();
        verify(engine, never()).start();

        verify(keyValueService).setValue(ERROR, "Test brake failure");
        verify(keyValueService, never()).setValue(eq(CAR_STATUS), any(String.class));
    }

    @Test
    void testStopNoFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.RUNNING.toString());
        doNothing().when(brake).stopSystem();
        doNothing().when(engine).stop();

        String response = car.stop();
        assertEquals("Car stopped successfully!", response);

        verify(brake).stopSystem();
        verify(engine).stop();

        verify(keyValueService).setValue(ERROR, "");
        verify(keyValueService).setValue(CAR_STATUS, CarStatus.STOPPED.toString());
    }

    @Test
    void testStopAlreadyStopped() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.STOPPED.toString());

        String response = car.stop();
        assertEquals("Car is already stopped!", response);

        verify(brake, never()).stopSystem();
        verify(engine, never()).stop();

        verify(keyValueService, never()).setValue(eq(CAR_STATUS), any(String.class));
        verify(keyValueService, never()).setValue(eq(ERROR), any(String.class));
    }

    @Test
    void testStopEngineFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.RUNNING.toString());
        doNothing().when(brake).stopSystem();
        doThrow(new EngineFailureException("Test engine failure")).when(engine).stop();

        String response = car.stop();
        assertEquals("Test engine failure", response);

        verify(brake).stopSystem();
        verify(engine).stop();

        verify(keyValueService).setValue(ERROR, "Test engine failure");
        verify(keyValueService).setValue(CAR_STATUS, CarStatus.STOPPED.toString());
    }

    @Test
    void testStopBrakeSystemFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.RUNNING.toString());
        doThrow(new BrakeFailureException("Test brake failure")).when(brake).stopSystem();
        doNothing().when(engine).stop();

        String response = car.stop();
        assertEquals("Test brake failure", response);

        verify(brake).stopSystem();
        verify(engine).stop();

        verify(keyValueService).setValue(ERROR, "Test brake failure");
        verify(keyValueService).setValue(CAR_STATUS, CarStatus.STOPPED.toString());
    }

    @Test
    void testStopBrakeSystemAndEngineFailure() throws Exception {
        when(keyValueService.getValueIfPresent(CAR_STATUS)).thenReturn(CarStatus.RUNNING.toString());
        doThrow(new BrakeFailureException("Test brake failure")).when(brake).stopSystem();
        doThrow(new EngineFailureException("Test engine failure")).when(engine).stop();

        String response = car.stop();
        assertEquals("Test engine failure Test brake failure", response);

        verify(brake).stopSystem();
        verify(engine).stop();

        verify(keyValueService).setValue(ERROR, "Test engine failure Test brake failure");
        verify(keyValueService).setValue(CAR_STATUS, CarStatus.STOPPED.toString());
    }

    @Test
    void testRefuel() {
        String response = car.refuel();
        assertEquals("The car is refueled successfully!", response);
        verify(fuelTank).refuel();
    }

    @Test
    void testFuelLevel() {
        String fuelLevel = "4";

        when(keyValueService.getValueIfPresent(FUEL_LEVEL)).thenReturn(fuelLevel);

        String response = car.getFuelLevel();
        assertEquals(String.format("Current fuel level: %s liters", fuelLevel), response);
    }

    @Test
    void testDashboard() {
        car.displayDashboard();
        verify(dashboard).display();
    }

}