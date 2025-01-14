package application;

import java.util.ArrayList;

/*A class that contains the relevant information for a particular customer,
including a unique Customer ID #, name, address, phone number and list of orders
associated with this customer
 */

public class Customer {
	//customerID to store customerID
	//Strings firstName, lastName to store both first name last name. Split to two, as its easier to concat rather than split down the line
	//String address to store address. 
	//String phonenumber to store phonenumber. xxx-xxx-xxxx format. I can't see a reason to put this as an int, we shouldn't be working with the numbers directly
	//Order list orderList to store any related orders.
	private int customerID;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private ArrayList<Order> orderList;
	
	//No arg constructor, mostly for testing purposes
	public Customer() {
		customerID = 0;
		firstName = "John";
		lastName = "Doe";
		address = "1600 Pennsylvania Avenue NW Washington DC 20500";
		phoneNumber = "123-456-7890";
		orderList = new ArrayList<Order>();
	}
	
	//full arg constructor
	public Customer(int customerID, String firstName, String lastName, String address, String phoneNumbers) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumbers;
		orderList = new ArrayList<Order>();
	}
	
	//getters
	public int getCustomerID() {
		return customerID;
	}	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}	
	//returns a list of all orders associated with a customer
	public Order[] getOrders() {
		Order[] returnOrderList = new Order[orderList.size()];
		for (int i = 0; i < orderList.size(); i++) {
			returnOrderList[i] = orderList.get(i);
		}
		
		return returnOrderList;
	}	
	//returns orderList
	public ArrayList<Order> getOrderList(){
		return orderList;
	}
	

	//support method to make file output easier
	public String[] getOutputList() {
		String[] outputArray = new String[5];
		outputArray[0] = Integer.toString(customerID);
		outputArray[1] = firstName;
		outputArray[2] = lastName;
		outputArray[3] = address;
		outputArray[4] = phoneNumber;
		return outputArray;
	}
	
	//setter to set orderList
	public void setOrderList(ArrayList<Order> inList) {
		for (Order item : inList) {
			orderList.add(item);
		}
	}

	// Adds a order onto the Order List
	public void addOrder (Order o) {
		orderList.add(o);
	}
	

}
