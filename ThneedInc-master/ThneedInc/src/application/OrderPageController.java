package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

public class OrderPageController implements ChangeListener<Number>, Comparator<Order> {
	
	@FXML
	private ChoiceBox<Integer> orderBox;
	@FXML
	private Label orderDetails;
	@FXML
	private Label custDetail;
	@FXML
	private Button saveDataButton;
	@FXML
	private Button custDataButton;
	@FXML
	private Button createOrderButton;
	@FXML
	private Label allThneeds;
	@FXML
	private Button fillOrderButton;
	@FXML
	private Label dataSavedAlert;
	
	// Customer Data window variables and related
	private Stage customerDetailStage;
	private CustomerDetailsController custDetailsController;
	
	// Create Order window variables and related
	private Stage createOrderStage;
	private CreateOrderController creOrderController;

	// ArrayList for Orders and Customers alongside a Hashmap for Identifying customers
	private ArrayList<Customer> customerList = new ArrayList<>();
	private ArrayList<Order> orderList = new ArrayList<>();
	private HashMap<Integer, Customer> custIden = new HashMap<>();
	private HashMap<Integer, Order> orderIden = new HashMap<>();
	
	// File Input and Output Variable
	private FileIO fileObj;
	
	@SuppressWarnings("static-access")
	@FXML
	private void saveDataButtonClick(ActionEvent event) {
		// Saves the file
		fileObj.saveFile(orderList, customerList);
		
		// Pops up a label to show that the data has been saved.
		dataSavedAlert.setVisible(true);
		dataSavedAlert.setManaged(true);
	}

	@FXML
	private void custDataButtonClick(ActionEvent event) {
		
		// Pulling in the details of the customer when the button is clicked by the user.
		if (customerDetailStage == null) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerDetails.fxml"));
			AnchorPane customerRoot;
			
			try {
				customerRoot = (AnchorPane) loader.load();
				Scene window2Scene = new Scene(customerRoot);
				customerDetailStage = new Stage();
				customerDetailStage.setTitle("Details of the Customer");
				customerDetailStage.setScene(window2Scene);
				customerDetailStage.setResizable(false);
				custDetailsController = (CustomerDetailsController) loader.getController();
				System.out.println("Details Window Loaded");
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// Fetching the appropriate customer data
		int selectOrderNumber = orderBox.getSelectionModel().getSelectedItem();
		Order selectOrder = orderIden.get(selectOrderNumber);
		Customer corCust = custIden.get(selectOrder.getCustomerID());
		custDetailsController.getCustomerInformation(corCust);
		
		// Hides a unnecessary label
		dataSavedAlert.setVisible(false);
		dataSavedAlert.setManaged(false);
		
		// Shows the new window.
		customerDetailStage.show();
		
	}
	
	@FXML
	private void createOrderButtonClick(ActionEvent event) {
		// TODO

		// Pulls up the window to allow the user to create a new order
		if (createOrderStage == null) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateOrder.fxml"));
			AnchorPane createOrderRoot;
			
			try {
				createOrderRoot = (AnchorPane) loader.load();
				Scene window2Scene = new Scene(createOrderRoot);
				createOrderStage = new Stage();
				createOrderStage.setTitle("Creating a Order");
				createOrderStage.setScene(window2Scene);
				createOrderStage.setResizable(false);
				creOrderController = (CreateOrderController) loader.getController();
				creOrderController.setOPC(this);
				System.out.println("Creating Order Window Loaded");
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// Hides this window
		windowState(false);
		
		// Calls startup method
		creOrderController.start();
		
		// Hides a unnecessary label
		dataSavedAlert.setVisible(false);
		dataSavedAlert.setManaged(false);
		
		// Shows the new window.
		createOrderStage.show();
	}
	
	
	// Fills the current selected order
	@FXML
	private void fillOrderButtonClick (ActionEvent event){
		
		ObservableList<Integer> items = orderBox.getItems();
		int index = orderBox.getSelectionModel().getSelectedIndex();
		
		// Finds which order is selected and stores it
		int selectOrderNumber = items.get(index);
		Order selectOrder = orderIden.get(selectOrderNumber);
		selectOrder.fill();
		
		// Replacing old Order Object with new one that contains filledDates
		orderIden.replace(selectOrderNumber, selectOrder);
		for (Order o : orderList) {
			if (o.getOrderNumber() == selectOrderNumber) {
				o.fill();
			}
		}
		
		// String variables for storing informations about the order.
		String orderNum = Integer.toString(selectOrder.getOrderNumber());
		String dateCreated = selectOrder.getOrderDate().format(DateTimeFormatter.ofPattern("M-d-yyyy"));
		String timeCreated = selectOrder.getOrderTime().format(DateTimeFormatter.ofPattern("h:m:s a"));
		String dateFilled = selectOrder.getFilledDate().format(DateTimeFormatter.ofPattern("M-d-yyyy"));
		String timeFilled = selectOrder.getFilledTime().format(DateTimeFormatter.ofPattern("h:m:s a"));
		
		String collected = String.format("Order Number: %s \n Date Created: %s \n Time Created: %s"
				+ "\n Date Filled: %s \n Time Filled: %s", orderNum, dateCreated, timeCreated, dateFilled, timeFilled);
		
		orderDetails.setText(collected);
		orderSort();
	}
	
	
	// Startup method for when window opens
	public void startup() {
		// Hides elements that doesn't need to shown initially
		elementVisible(false);
		
		dataSavedAlert.setVisible(false);
		dataSavedAlert.setManaged(false);
		
		// Adds Change Listener to our Order ChoiceBox
		orderBox.getSelectionModel().selectedIndexProperty().addListener(this);
		
		// Puts all customer ID to match to their Customer Objects.
		if (!customerList.isEmpty()) {
			for (Customer c: customerList) {
				custIden.put(c.getCustomerID(), c);
			}
		}
		
		// Puts all pre-existing orders into the choicebox and hashmap
		if (!orderList.isEmpty()) {
			for (Order o: orderList) {
				
				orderBox.getItems().add(o.getOrderNumber());
				
				orderIden.put(o.getOrderNumber(), o);
			}
		}
	}
	
	// Adds customer, part of inter-window communication
	public void addCust(Customer c) {
		
		customerList.add(c);
		custIden.put(c.getCustomerID(), c);
		creOrderController.newCust(c);
	}
	
	// Adds the order onto the choicebox and ArrayList
	public void addOrder(Order o) {
		
		// Adds order onto an ArrayList and ChoiceBox Items. Also adds it onto a HashMap
		orderList.add(o);
		orderBox.getItems().add(o.getOrderNumber());
		orderIden.put(o.getOrderNumber(), o);
	}
	
	// Method for hiding/showing elements
	public void elementVisible (boolean b) {
		custDetail.setVisible(b);
		custDetail.setManaged(b);
		
		custDataButton.setVisible(b);
		custDataButton.setManaged(b);
		
		allThneeds.setVisible(b);
		allThneeds.setManaged(b);
		
		fillOrderButton.setVisible(b);
		fillOrderButton.setManaged(b);
		
		orderDetails.setVisible(b);
		orderDetails.setManaged(b);
	}
	
	// Method for auto sorting orders in the correct order
	public void orderSort() {
		
		// Sorting using a implemented Comparator
		Collections.sort(orderList, this);
		
		// Temporarily removes Change Listener to sort list
		orderBox.getSelectionModel().selectedIndexProperty().removeListener(this);
		
		// Clears all items from the choicebox.
		orderBox.getItems().clear();
		
		// Adds the newly order items onto the ChoiceBox items
		for (Order o : orderList) {
			orderBox.getItems().add(o.getOrderNumber());
		}
		
		elementVisible(false);
		
		// Readds Change Listener to the choice box after all the sorted list is added appropriately
		orderBox.getSelectionModel().selectedIndexProperty().addListener(this);
	
	}
	
	// Helps hide and show windows appropriately
	public void windowState(boolean b) {
		Stage thisStage;
		thisStage = (Stage) orderDetails.getScene().getWindow();
		
		if (b) thisStage.show();
		
		else thisStage.hide();
	}
	
	// Standard getters and setters
	public ArrayList<Customer> getCustList(){
		return customerList;
	}
	
	public ArrayList<Order> getOrderList(){
		return orderList;
	}
	
	// Standard settings
	public void setFileIO (FileIO f) {
		fileObj = f;
	}
	
	public void setCustList (ArrayList<Customer> cList) {
		customerList = cList;
	}
	
	public void setOrderList (ArrayList<Order> oList) {
		orderList = oList;
	}

	// Checks when the checkbox is selecting a new order number and changes details to match it.
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		
		orderChange();
	}
	
	public void orderChange() {
		
		int index = orderBox.getSelectionModel().getSelectedIndex();
		
		// Finds which order is selected and stores it
		int selectOrderNumber = orderBox.getItems().get(index);
		Order selectOrder = orderIden.get(selectOrderNumber);
		
		// Storing order information into strings
		String orderNum = Integer.toString(selectOrder.getOrderNumber());
		String dateCreated = selectOrder.getOrderDate().format(DateTimeFormatter.ofPattern("M-d-yyyy"));
		String timeCreated = selectOrder.getOrderTime().format(DateTimeFormatter.ofPattern("h:m:s a"));
		
		// Checking date and time filled
		String dateFilled = "";
		if (selectOrder.getFilledDate() != null) {
			dateFilled = selectOrder.getFilledDate().format(DateTimeFormatter.ofPattern("M-d-yyy"));
		}
		else {
			dateFilled = "Not Filled";
		}
		
		String timeFilled = "";
		if (selectOrder.getFilledTime() != null) {
			timeFilled = selectOrder.getFilledTime().format(DateTimeFormatter.ofPattern("h:m:s a"));
		}
		else {
			timeFilled = "Not Filled";
		}
		
		String collected = String.format("Order Number: %s \n Date Created: %s \n Time Created: %s"
				+ "\n Date Filled: %s \n Time Filled: %s", orderNum, dateCreated, timeCreated, dateFilled, timeFilled);
		
		orderDetails.setText(collected);
		
		// Collects all thneeds into a single string
		String thneeds = "List of Thneeds:\n";
		
		for (Thneed th : selectOrder.getThneedList()) {
			thneeds += th.toString() + "\n";
		}
		
		allThneeds.setText(thneeds);
		
		// Gets corresponding customer who placed the order
		Customer corCust = custIden.get(selectOrder.getCustomerID());
		
		String fullName = corCust.getFirstName() + " " + corCust.getLastName();
		custDetail.setText("Customer: " + fullName);
		
		// Makes elements appear
		elementVisible(true);
		
		// Hides a unnecessary label for FileIO
		dataSavedAlert.setVisible(false);
		dataSavedAlert.setManaged(false);
	}

	@Override
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		if (o1.getFilledDate() != null && o2.getFilledDate() != null) {
			LocalDateTime o1Combined = LocalDateTime.of(o1.getFilledDate(), o1.getFilledTime());
			LocalDateTime o2Combined = LocalDateTime.of(o2.getFilledDate(), o2.getFilledTime());
			
			if (o1Combined.isBefore(o2Combined)) {
				return 1;
			}
			else if (o1Combined.isAfter(o2Combined)) {
				return -1;
			}
			else {
				return 0;
			}
		}
		else if (o1.getFilledDate() != null || o2.getFilledDate() != null) {
			if (o1.getFilledDate() != null) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			LocalDateTime o1Combined = LocalDateTime.of(o1.getOrderDate(), o1.getOrderTime());
			LocalDateTime o2Combined = LocalDateTime.of(o2.getOrderDate(), o2.getOrderTime());
			if (o1Combined.isBefore(o2Combined)) {
				return -1;
			}
			else if (o1Combined.isAfter(o2Combined)) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}





