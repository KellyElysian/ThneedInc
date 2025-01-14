package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class FileIO {
	
	private static File file;
	private static Scanner scanner;
	
	private static void createFile() {
		//Create new file, output if exists or not
		try{
			String currentPath = System.getProperty("user.dir");
			file = new File(currentPath + "\\src\\application\\thneedData.csv");
			if (file.createNewFile()) {
				System.out.println("New file created");
			} else {
				System.out.println("File already exists");
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Customer> readCustomers(){
		ArrayList<Customer> returnArrayList = new ArrayList<Customer>();
		createFile();
		
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()){
				String[] inputLine = scanner.nextLine().split(",");
				if (inputLine[0].equalsIgnoreCase("C")) {
					returnArrayList.add(new Customer(Integer.parseInt(inputLine[1]),inputLine[2], inputLine[3], inputLine[4], inputLine[5]));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file found");
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return returnArrayList;
	}

	public static ArrayList<Order> readOrders(){
		//Create an ArrayList to return
		//Make file workable
		ArrayList<Order> returnArrayList = new ArrayList<Order>();
		createFile();
			
		try {
			//New scanner
			scanner = new Scanner(file);
			//Order to work with inside of a loop
			Order readOrder;
			while (scanner.hasNextLine()) {
				String[] inputLine = scanner.nextLine().split(",");
				if (inputLine[0].equalsIgnoreCase("O")) {
					if (inputLine[6].equalsIgnoreCase("TBF")) {
						//Case when an order is not filled
						readOrder = new Order(Integer.parseInt(inputLine[1]),Integer.parseInt(inputLine[2]), inputLine[3], inputLine[4], inputLine[5]);
					} else {
						//Case when an order is filled
						readOrder = new Order(Integer.parseInt(inputLine[1]),Integer.parseInt(inputLine[2]), inputLine[3], inputLine[4], inputLine[5], inputLine[6], inputLine[7]);
					}
					
					for(int i = 8; i < inputLine.length; i+= 3) {
						//Indexes are weird, I didn't sync file structure to the Thneed constructor
						//This works on the assumption that an Order must have Thneeds associated
						readOrder.addThneed(Integer.parseInt(inputLine[i + 1]), Integer.parseInt(inputLine[i + 2]),inputLine[i]);
					}
					
					returnArrayList.add(readOrder);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file found");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return returnArrayList;
	}
	
	//Saves everything
	public static void saveFile(ArrayList<Order> orderList, ArrayList<Customer> customerList) {
		createFile();
					
		try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
			PrintWriter writer = new java.io.PrintWriter(fileWriter);
			for (Customer customer : customerList) {
				Customer writeCustomer = customer;
				writer.print("C" + ",");
				
				//Append all customer data (excluding associated orderNumbers)
				for (String item : writeCustomer.getOutputList()) {
					writer.print(item + ",");
				}
					
				writer.println();
			}
			
			for (Order order : orderList) {
				//writeOrder casts inputObject to an Order, to make it easier to work with 
				Order writeOrder = order;					
				writer.print("O,");
				
				//Append all order data (excluding thneeds in thneedList)
				for (String item : writeOrder.getOutputList()) {
					writer.print(item + ",");
				}
				//Append all thneed data in ArrayList
				for (int i = 0; i < writeOrder.getThneedList().size(); i++) {
					writer.print(writeOrder.getThneed(i).getColor() + ",");
					writer.print(writeOrder.getThneed(i).getQuantity() + ",");
					writer.print(writeOrder.getThneed(i).getSize() + ",");
				}
				
				writer.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static ArrayList<Customer> assignOrders(){
		ArrayList<Customer> returnList = new ArrayList<Customer>(readCustomers());
		for (Customer cust: returnList) {
			for (Order order: readOrders()) {
				if (order.getCustomerID() == cust.getCustomerID()) {
					cust.addOrder(order);
				}
			}
		}

		return returnList;
	}
}
	



