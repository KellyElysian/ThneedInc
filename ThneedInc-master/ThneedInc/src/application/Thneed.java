package application;

public class Thneed {
	//int size to represent size, 0 = small, 1 medium, 2 large
	//string color to hold which color
	private int size;
	private String color;
	private int quantity;
	
	//no arg constructor
	public Thneed() {
		size = 0;
		color = "orange";
		quantity = 1;
	}
	
	//size & color constructor
	public Thneed(int size, String color) {
		this.size = size;
		this.color = color;
		quantity = 1;
	}
	
	public Thneed(int quantity, int size, String color) {
		this.size = size;
		this.color = color;
		this.quantity = quantity;
	}
	
	
	//getter methods
	public int getSize() {
		return size;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String toString() {
		String returnString = "";
		returnString += quantity + " ";
		switch(size) {
		case 0:
			returnString += "Small";
			break;
		case 1:
			returnString += "Medium";
			break;
		case 2:
			returnString += "Large";
			break;
		}
		returnString +=  " " + color;
		return returnString;
	}
	
}
