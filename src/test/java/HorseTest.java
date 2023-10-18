import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class HorseTest {
    private final String name = "Apollo";
    private final double distance = 10.0;
    private final double negativeDistance = -10.0;
    private final double speed = 5.0;
    private final double negativeSpeed = -5.0;
    private final String exceptionMessageWhenNameIsNull = "Name cannot be null.";
    private final String exceptionMessageWhenNameIsBlank = "Name cannot be blank.";
    private final String exceptionMessageWhenSpeedIsNegative = "Speed cannot be negative.";
    private final String exceptionMessageWhenDistanceIsNegative = "Distance cannot be negative.";

    @Test
    void throwExceptionIfNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed));
    }

    @Test
    void throwExceptionIfSpeedIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, negativeSpeed));
    }

    @Test
    void throwExceptionIfDistanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, negativeDistance));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t"})
    void throwExceptionIfNameDoesNotContainLetters(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed));
    }

    @Test
    void checkExceptionMessageWhenNameIsNull() {
        try {
            new Horse(null, speed);
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenNameIsNull, exception.getMessage());
        }
    }

    @Test
    void checkExceptionMessageWhenDistanceIsNegative() {
        try {
            new Horse(name, speed, negativeDistance);
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenDistanceIsNegative, exception.getMessage());
        }
    }

    @Test
    void checkExceptionMessageWhenSpeedIsNegative() {
        try {
            new Horse(name, negativeSpeed);
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenSpeedIsNegative, exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n", "\t"})
    void checkExceptionMessageWhenNameIsBlank(String name) {
        try {
            new Horse(name, speed);
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenNameIsBlank, exception.getMessage());
        }
    }

    @Test
    void checkIfMethodGetNameReturnCorrectName() {
        Horse horse = new Horse(name, speed);

        assertEquals(name, horse.getName());
    }

    @Test
    void checkIfMethodGetSpeedReturnCorrectSpeed() {
        Horse horse = new Horse(name, speed);

        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse1 = new Horse(name, speed, distance);
        Horse horse2 = new Horse(name, speed);

        assertAll(
                () -> assertEquals(distance, horse1.getDistance()),
                () -> assertEquals(0, horse2.getDistance())
        );
    }

    @Test
    void checkIfMethodMoveCallsGetRandomDoubleMethod() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse(name, speed);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.6, 0.7})
    void checkIfMethodMoveCalculateCorrectDistance(double fakeValue) {
        double min = 0.2;
        double max = 0.9;
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = distance + speed * fakeValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeValue);
            horse.move();
        }

        assertEquals(expectedDistance, horse.getDistance());
    }

}