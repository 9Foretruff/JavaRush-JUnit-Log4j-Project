import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    private final String name = "Apollo";
    private final double speed = 5.0;
    private final String exceptionMessageWhenListOfHorsesIsNull = "Horses cannot be null.";
    private final String exceptionMessageWhenListOfHorsesIsEmpty = "Horses cannot be empty.";

    @Test
    void throwExceptionIfListOfHorsesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void throwExceptionIfListOfHorsesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    void checkExceptionMessageWhenListOfHorsesIsNull() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenListOfHorsesIsNull, exception.getMessage());
        }
    }

    @Test
    void checkExceptionMessageWhenListOfHorsesIsEmpty() {
        try {
            new Hippodrome(Collections.emptyList());
        } catch (IllegalArgumentException exception) {
            assertEquals(exceptionMessageWhenListOfHorsesIsEmpty, exception.getMessage());
        }
    }

    @Test
    void checkIfMethodGetHorsesReturnCorrectList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(name + i, speed));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertAll(
                () -> assertNotNull(hippodrome.getHorses()),
                () -> assertEquals(30, hippodrome.getHorses().size()),
                () -> {
                    for (int i = 0; i < 30; i++) {
                        assertEquals(name + i, hippodrome.getHorses().get(i).getName());
                    }
                }
        );
    }

    @Test
    void checkIfMethodMoveCallsForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse hors : horses) {
            Mockito.verify(hors, Mockito.times(1)).move();
        }
    }

    @Test
    void checkIfMethodGetWinnerReturnCorrectWinner() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("Horse1", 1, 10),
                new Horse("Horse2", 1, 20),
                new Horse("Horse3", 1, 30)
        ));
        assertEquals("Horse3", hippodrome.getWinner().getName());
    }
}