import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

class HorseTest {

    @Test
    @DisplayName("if name is null")
    void nameNullException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 1.0, 1.0)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    @DisplayName("if name is blank")
    void nameBlankException(String name) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0, 1.0)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @DisplayName("if speed is negative")
    void speedNegativeException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", -1.0, 1.0)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("if distance is negative")
    void distanceNegativeException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", 1.0, -1.0)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("getName() returns the name passed to the constructor")
    void getName() {
        String expectedName = "TestHorse";
        Horse horse = new Horse(expectedName, 1.0, 1.0);
        assertEquals(expectedName, horse.getName(),
                "getName() must return the name passed to the constructor");
    }

    @Test
    @DisplayName("getSpeed() returns speed passed to the constructor")
    void getSpeed() {
        double expectedSpeed = 1.0;
        Horse horse = new Horse("TestHorse", 1.0, 1.0);
        assertEquals(expectedSpeed,horse.getSpeed(),
                "getSpeed() must return speed passed to the constructor");
    }

    @Test
    @DisplayName("getDistance() returns the distance passed to the constructor")
    void getDistance() {
        double expectedDistance = 1.0;
        Horse horse = new Horse("TestHorse", 1.0, 1.0);
        assertEquals(expectedDistance,horse.getSpeed(),
                "getDistance() must return distance passed to the constructor");
    }

    @Test
    @DisplayName("getDistance() returns 0 if constructor with two parameters")
    void getDistanceDefaultConstructor() {
        Horse horse = new Horse("TestHorse", 2.0);

        double actualDistance = horse.getDistance();

        assertEquals(0.0, actualDistance,
                "getDistance() must return 0 if no value is specified.");
    }

    @Test
    @DisplayName("move() calls GetRandomDouble()")
    void move() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Test", 2.0, 5.0);

            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @DisplayName("calculate the distance")
    @CsvSource({
            "0.2, 3.0, 7.0",  // random, speed, initial distance
            "0.5, 3.0, 7.0",
            "0.9, 3.0, 7.0",
            "0.2, 5.0, 10.0",  //other speed and distance
            "0.5, 5.0, 10.0",
            "0.9, 5.0, 10.0"
    })
    void moveCalculatesDistanceCorrectly(double random, double speed, double initialDistance) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            Horse horse = new Horse("TestHorse", speed, initialDistance);
            horse.move();

            double expected = initialDistance + speed * random;
            assertEquals(expected, horse.getDistance(), 0.0001, "The distance calculation must be correct.");
        }
    }
}