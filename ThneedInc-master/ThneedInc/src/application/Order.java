package application;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/*A class that contains the relevant information for a particular order, including a
unique Order #, a list of Thneeds ordered (quantity for a particular size & color… and
order could have multiple different types of Thneeds), which customer ordered the item,
the date it was ordered and the date it was filled.
*/

public class Order {
	//int orderNumber to track Order #
	//Thneed ArrayList thneedList is to track each ordered Thneed associated with the order
	//private int customerID is to track which customer ordered the item. Not doing this as a customer class, I dont think the full class is needed within an order
	//LocalDate dateOrdered is for the day the order was made, dateFilled the day it was filled
	//LocalTime timeOrdered is for the time the order was made, timeFilled the time it was filled
	//Date and time are separate as for now, may help with displaying in gui down the line
	//Numbers after each line is index of location in list for getOutputList
	private int orderNumber; //0
	private int customerID; //1
	private String customerName; //2
	private ArrayList<Thneed> thneedList; 
	private LocalDate dateOrdered; //3
	private LocalTime timeOrdered; //4
	private LocalDate dateFilled; //5
	private LocalTime timeFilled; //6

	public Order() {
		orderNumber = 1;
		customerID = 1;
		customerName = "Test";
		dateOrdered = LocalDate.now();
		timeOrdered = LocalTime.now();
		thneedList = new ArrayList<Thneed>();
	}
	
	//takes int orderNumber, int customerID, String customerName, and a Thneed ArrayList as arguments.
	public Order(int orderNumber, int customerID, String customerName) {
		this.orderNumber = orderNumber;
		this.customerID = customerID;
		this.customerName = customerName;
		this.thneedList = new ArrayList<Thneed>();
		dateOrdered = LocalDate.now();
		timeOrdered = LocalTime.now();
	}
	
	//Same as above, with date & time ordered as parameters
	public Order(int orderNumber, int customerID, String customerName, String dateOrdered, String timeOrdered) {
		this.orderNumber = orderNumber;
		this.customerID = customerID;
		this.customerName = customerName;
		this.thneedList = new ArrayList<Thneed>();
		this.dateOrdered = LocalDate.parse(dateOrdered);
		this.timeOrdered = LocalTime.parse(timeOrdered);
	}
	
	//Same as above, with date & time ordered as parameters
		public Order(int orderNumber, int customerID, String customerName, String dateOrdered, String timeOrdered, String dateFilled, String timeFilled) {
			this.orderNumber = orderNumber;
			this.customerID = customerID;
			this.customerName = customerName;
			this.thneedList = new ArrayList<Thneed>();
			this.dateOrdered = LocalDate.parse(dateOrdered);
			this.timeOrdered = LocalTime.parse(timeOrdered);
			this.dateFilled = LocalDate.parse(dateFilled);
			this.timeFilled = LocalTime.parse(timeFilled);
		}
	
	//sets the data fields for dateFilled and timeFilled to current value. Call when filling orders
	public void fill() {
		dateFilled = LocalDate.now();
		timeFilled = LocalTime.now();
	}
	
	//Getters
	public int getOrderNumber() {
		return orderNumber;
	}
	public int getCustomerID() {
		return customerID;
	}	
	public String getCustomerName() {
		return customerName;
	}
	public LocalDate getOrderDate() {
		return dateOrdered;
	}
	public LocalTime getOrderTime() {
		return timeOrdered;
	}
	public LocalDate getFilledDate() {
		return dateFilled;
	}
	public LocalTime getFilledTime() {
		return timeFilled;
	}
	//for the entire ArrayList
	public ArrayList<Thneed> getThneedList() {
		return thneedList;
	}
	//for a specific thneed from the ArrayList
	public Thneed getThneed(int index) {
		return thneedList.get(index);
	}
	
	//"adder" method to thneedList
	public void addThneed(int quantity, int size, String color) {
		thneedList.add(new Thneed(quantity, size, color));
	}
	
	public void setThneedList(ArrayList<Thneed> inThneedList) {
		for (Thneed item : inThneedList) {
			thneedList.add(item);
		}
	}
	
	//Returns a list of Strings, that are in order of the structure for FileIO
	public String[] getOutputList() {
		String[] returnList = new String[7];
		returnList[0] = Integer.toString(orderNumber);
		returnList[1] = Integer.toString(customerID);
		returnList[2] = customerName;
		returnList[3] = dateOrdered.toString();
		returnList[4] = timeOrdered.toString();
		
		try {
			returnList[5] = dateFilled.toString();
		} catch (NullPointerException e) {
			returnList[5] = "TBF"; //To Be Filled
		}
		
		try {
			returnList[6] = timeFilled.toString();
		} catch (NullPointerException e) {
			returnList[6] = "TBF"; //To Be Filled
		}
		
		return returnList;
	}
	
	
	
	
}
