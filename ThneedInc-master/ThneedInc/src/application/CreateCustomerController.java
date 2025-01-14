package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CreateCustomerController {
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField streetAdd;
	@FXML
	private TextField cityAdd;
	@FXML
	private TextField stateAdd;
	@FXML
	private TextField zipAdd;
	@FXML
	private TextField phoneNumber;
	@FXML
	private Button createButton;
	
	private int cID = 0;
	
	private OrderPageController OPC;


	@FXML
	public void createButtonClick(ActionEvent event) {
		Customer newCustomer;
		
		String fName = firstName.getText();
		String lName = lastName.getText();
		String address = streetAdd.getText() + " " + cityAdd.getText() + " " + stateAdd.getText() + " " + zipAdd.getText();
		String phone = phoneNumber.getText();
		
		newCustomer = new Customer(cID, fName, lName, address, phone);
		
		OPC.addCust(newCustomer);
		
		cID++;
		
		Stage thisWindow = (Stage) createButton.getScene().getWindow();
		thisWindow.close();
		clear();
	}
	
	// Sets OrderPageController for this controller
	public void setOrderPageController(OrderPageController c) {
		OPC = c;
	}
	
	// Startup method to help set up certain variables in this controller
	public void start() {
		cID = OPC.getCustList().size() + 1;
	}
	
	// Clearing method (clean-up)
	public void clear() {
		firstName.clear();
		lastName.clear();
		streetAdd.clear();
		cityAdd.clear();
		stateAdd.clear();
		zipAdd.clear();
		phoneNumber.clear();
	}
	
}
