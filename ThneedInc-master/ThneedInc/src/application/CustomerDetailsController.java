package application;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class CustomerDetailsController {
	@FXML
	private Label custName;
	@FXML
	private Label custID;
	@FXML
	private Label custAddress;
	@FXML
	private Label custPhone;
	
	// Main method for this controller to fetch customer informa
	public void getCustomerInformation(Customer c) {
		
		String fullName = c.getFirstName() + " " + c.getLastName();
		String cID = Integer.toString(c.getCustomerID());
		
		custName.setText(fullName);
		custID.setText(cID);
		custAddress.setText(c.getAddress());
		custPhone.setText(c.getPhoneNumber());
	}

}
