import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    @DisplayName("if horses is null")
    void checkNullConstructor() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("if horses is empty")
    void checkEmptyConstructor() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>())
        );

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("getHorses() returns the same objects in the same order")
    void getHorsesShouldReturnSameList() {
        List<Horse> expectedHorses = IntStream.rangeClosed(1, 30)
                .mapToObj(i -> new Horse("Horse " + i, i, i * 0.1))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        List<Horse> actualHorses = hippodrome.getHorses();

        assertEquals(expectedHorses.size(), actualHorses.size(),
                "The list sizes do not match");

        IntStream.range(0, expectedHorses.size())
                .forEach(i -> assertSame(expectedHorses.get(i), actualHorses.get(i),
                "Objects at position" + i + " do not match"));
    }

    @Test
    @DisplayName("move() calls move on all 50 horses")
    void move() {
        List<Horse> horses = IntStream.range(0, 50)
                .mapToObj(i -> mock(Horse.class))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    @DisplayName("getWinner() returns the horse with the largest distance value")
    void getWinner() {
        Horse horse1 = new Horse("Horse1", 1, 1);
        Horse horse2 = new Horse("Horse2", 2, 2);
        Horse horse3 = new Horse("Horse3", 3, 3);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse3, hippodrome.getWinner());
    }
}
