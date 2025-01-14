package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThneedTest {

    @Test
    public void testSize() {
        Thneed testThneed = new Thneed(1, 0, "orange");
        assertEquals("should return the same size", 0, testThneed.getSize());
    }

    @Test
    public void testColor() {
        Thneed testThneed = new Thneed(1, 0, "orange");
        assertEquals("should return the same color", "orange", testThneed.getColor());
    }

    @Test
    public void testQuantity() {
        Thneed testThneed = new Thneed(1, 0, "orange");
        assertEquals("should return the same quantity", 1, testThneed.getQuantity());
    }

    @Test
    public void testToString() {
        Thneed testThneed = new Thneed(1, 0, "orange");
        assertEquals("should return a string based on size so in thhos case Small", "Small", testThneed.toString());
    }

}
