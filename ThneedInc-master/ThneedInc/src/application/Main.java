package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private OrderPageController opc;
	private FileIO fileData;
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		try {
			// set a title for the Window
			primaryStage.setTitle("Order Main Page");
			primaryStage.setResizable(false);
			
			// get an FXML loader and read in the fxml code
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/OrderPage.fxml"));
			AnchorPane mainLayout = (AnchorPane)loader.load();
			
			// Sets a OrderPageController Variable
			opc = (OrderPageController) loader.getController();
			
			// File Input and Output and loads data from a file previously
			fileData = new FileIO();
			opc.setFileIO(fileData);
			
			opc.setCustList(fileData.assignOrders());
			opc.setOrderList(fileData.readOrders());
			
			// Startup method
			opc.startup();
			
			// Create the scene with the layout in the fxml code, set the scene and show it
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Whenever application is created, 
	@SuppressWarnings("static-access")
	@Override
	public void stop() {
		fileData.saveFile(opc.getOrderList(), opc.getCustList());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
