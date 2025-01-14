package application;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class OrderTest {
        @Test
        public void testNumber() {
            Order testOrder = new Order(1, 1, "test");
            assertEquals("should return the number", 1, testOrder.getOrderNumber());
        }
        @Test
        public void testID() {
         Order testOrder = new Order(1, 1, "test");
         assertEquals("should return the id", 1, testOrder.getCustomerID());
    }
    @Test
    public void testName() {
        Order testOrder = new Order(1, 1, "test");
        assertEquals("should return the name", "test", testOrder.getCustomerName());
    }
    @Test
    public void testDate() {
        Order testOrder = new Order(1, 1, "test");
        assertEquals("should return the date", LocalDate.now(), testOrder.getOrderDate());
    }
    
    // Functionally works
    @Test
    public void testTime() {
        Order testOrder = new Order(1, 1, "test");
        assertEquals("should return the time", LocalTime.now(), testOrder.getOrderTime());
    }
    @Test
    public void testFilledDate() {
        Order testOrder = new Order(1, 1, "test");
        assertEquals("should return the filled Date", null, testOrder.getFilledDate());
    }
    @Test
    public void testFilledTime() {
        Order testOrder = new Order(1, 1, "test");
        assertEquals("should return the filled time", null, testOrder.getFilledTime());
    }
    


}
