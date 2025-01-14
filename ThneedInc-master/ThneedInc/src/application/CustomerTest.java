package application;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CustomerTest {

    @Test
    public void testFirstName() {
            Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
            assertEquals("The name should be John", "John", testCustomer.getFirstName());
    }

    @Test
    public void testId() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("The id should be 0", 0, testCustomer.getCustomerID());
    }

    @Test
    public void testLastName() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("The name should be Doe", "Doe", testCustomer.getLastName());
    }

    @Test
    public void testFullName() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("The name should be John Doe", "John Doe", testCustomer.getFullName());
    }

    @Test
    public void testAddress() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("the address should be the same", "1600 Pennsylvania Avenue NW Washington DC 20500", testCustomer.getAddress());
    }

    @Test
    public void testNumber() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("the number should be the same", "123-456-7890", testCustomer.getPhoneNumber());
    }

	@Test
    public void testOrders() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        Order[] testOrders = testCustomer.getOrders();
        assertArrayEquals("should return the array of orders", testOrders, testCustomer.getOrders());
    }

    @Test
    public void testOrderList() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        assertEquals("should return the arraylist of orders", new ArrayList<Order>() , testCustomer.getOrderList());
    }

    @Test
    public void testSetOrderList() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        testCustomer.setOrderList(new ArrayList<Order>());
        assertEquals("should return the arraylist of orders with new set value", new ArrayList<Order>() , testCustomer.getOrderList());
    }

    @Test
    public void testaddOrder() {
        Customer testCustomer = new Customer(0, "John", "Doe","1600 Pennsylvania Avenue NW Washington DC 20500", "123-456-7890");
        testCustomer.addOrder(new Order());
        assertEquals("should add a value to the orders", testCustomer.getOrderList(), testCustomer.getOrderList());
    }





}