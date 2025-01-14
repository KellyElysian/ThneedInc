package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ChoiceBox;

public class CreateOrderController implements ChangeListener<Number>{
	@FXML
	private ChoiceBox<String> customerBox;
	@FXML
	private Label orderNumber;
	@FXML
	private TextField size;
	@FXML
	private TextField color;
	@FXML
	private TextField quantity;
	@FXML
	private Button createThneedButton;
	@FXML
	private Button confirmOrderButton;
	@FXML
	private Button createCustomerButton;
	@FXML
	private Label thneedAlert;
	
	
	// Tracks how many orders are in system
	private int orderAmount = 0;
	
	// Controller and Stage Variables
	private OrderPageController opc;
	
	private Stage createCustomerStage;
	private CreateCustomerController createCustController;
	
	// Thneed list for helping track what thneeds are in the order and will be used to create Order Object
	private ArrayList<Thneed> thneedList = new ArrayList<>();
	private ArrayList<Customer> custList = new ArrayList<>();
	private HashMap<String, Customer> custIden = new HashMap<>();


	@FXML
	public void createThneedButtonClick(ActionEvent event) {
		
		// Parses strings into ints and makes a new Thneed object
		int sizeInt = Integer.parseInt(size.getText());
		int quanInt = Integer.parseInt(quantity.getText());
		
		Thneed nThneed = new Thneed(quanInt, sizeInt, color.getText());
		
		thneedList.add(nThneed);
		
		// Pop up a message stating successful creation of thneed and adding thneed to the thneed list
		thneedAlert.setVisible(true);
		thneedAlert.setManaged(true);
		
		// Clears text fields
		size.clear();
		color.clear();
		quantity.clear();
	}
	

	@FXML
	public void confirmOrderButtonClick(ActionEvent event) {
		// TODO 
		
		// Stores user selected customer from choice box in a Customer Object Variable and gets customerID 
		String selectedName = customerBox.getSelectionModel().getSelectedItem();
		Customer selectedCustomer = custIden.get(selectedName);
		int custID = selectedCustomer.getCustomerID();
		
		// Creating the new Order
		@SuppressWarnings("unchecked")
		ArrayList<Thneed> temp = (ArrayList<Thneed>) thneedList.clone();
		Order newOrder = new Order(orderAmount, custID, selectedName);
		
		newOrder.setThneedList(temp);
		
		opc.addOrder(newOrder);
		
		// Clears thneed list for a new order
		thneedList.clear();
		
		// Finds the customer who placed this order and place the Order in their Order List
		for (Customer c : custList) {
			if (c.getCustomerID() == newOrder.getCustomerID()) {
				c.addOrder(newOrder);
			}
		}
		
		opc.orderSort();
		opc.windowState(true);
		Stage thisStage = (Stage) thneedAlert.getScene().getWindow();
		thisStage.close();
	}
	

	@FXML
	public void createCustomerButtonClick(ActionEvent event) {
		// TODO
		
		// Pulling in the window of creating a new customer when the button is clicked by the user.
		if (createCustomerStage == null) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateCustomer.fxml"));
			AnchorPane customerRoot;
			
			try {
				customerRoot = (AnchorPane) loader.load();
				Scene window2Scene = new Scene(customerRoot);
				createCustomerStage = new Stage();
				createCustomerStage.setTitle("Creating a Customer");
				createCustomerStage.setScene(window2Scene);
				createCustomerStage.setResizable(false);
				
				createCustController = (CreateCustomerController) loader.getController();
				createCustController.setOrderPageController(opc);
				createCustController.start();
				
				System.out.println("Create Customer Window Loaded");
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		opc.windowState(false);
		createCustomerStage.show();
	}
	
	// Hides the Thneed Added to List alert
	public void start() {
		// Hide Thneed Alert
		thneedAlert.setManaged(false);
		thneedAlert.setVisible(false);
		
		// Attachs listener to the choicebox
		customerBox.getSelectionModel().selectedIndexProperty().addListener(this);
		
		// Sets Order Number and proper Order Label
		orderAmount = opc.getOrderList().size() + 1;
		orderNumber.setText("Order Number: " + orderAmount);
		
		// Gets pre-existing customer list from the order page controller and stores it here.
		custList = opc.getCustList();
		
		customerBox.getSelectionModel().clearSelection();
		
		// Populates ArrayList and HashMap at the same time
		if (!custList.isEmpty()) {
			for (Customer c: custList) {
				String fullName = c.getFirstName() + " " + c.getLastName();
				
				if (!customerBox.getItems().contains(fullName)) {
					customerBox.getItems().add(fullName);
				}
				
				custIden.put(fullName, c);
			}
		}
	}
	
	// Communication between windows add customer controller
	public void newCust(Customer c) {
		
		String fullName = c.getFirstName() + " " + c.getLastName();
		custIden.put(fullName, c);
		
		if (!customerBox.getItems().contains(fullName)) {
			customerBox.getItems().add(fullName);
		}
	}
	
	// Sets the OrderPageController
	public void setOPC(OrderPageController c) {
		opc = c;
	}
	
	public void setCList (ArrayList<Customer> c) {
		custList = c;
	}

	// Change Listener for the Choicebox
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) { }
}
