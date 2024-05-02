import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AssSecondUpdate extends Application {
	static TabPane tabPane = new TabPane();
	public static HBox vvbox = new HBox();

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		SplitPane splitPane = new SplitPane();
		vvbox.setPadding(new Insets(15.0));
		
		
	           
	           

	            Address.
	            tableview = new TableView();
	            Address.buildData(null);
	            Tab tab1=new Tab("address",Address.tableview);
                tabPane.getTabs().addAll(tab1);
                
              
                car.
	            tableview = new TableView();
                car.buildData(null);
                Tab tab2=new Tab("car",car.tableview);
                tabPane.getTabs().addAll(tab2);
                
                Part_Car.
	            tableview = new TableView();
                Part_Car.buildData(null);
                Tab tab_Part_Car=new Tab("car_part",Part_Car.tableview);
                tabPane.getTabs().addAll(tab_Part_Car);
                
                
                customer.
	            tableview = new TableView();
                customer.buildData(null);
                Tab tab_customer=new Tab("customer",customer.tableview);
                tabPane.getTabs().addAll(tab_customer);
                
                Device.
	            tableview = new TableView();
                Device.buildData(null);
                Tab tab_Device=new Tab("device",Device.tableview);
                tabPane.getTabs().addAll(tab_Device);
                
                Manufacture.
	            tableview = new TableView();
                Manufacture.buildData(null);
                Tab tab_Manufacture=new Tab("manufacture",Manufacture.tableview);
                tabPane.getTabs().addAll(tab_Manufacture);
                
                Orders.
	            tableview = new TableView();
                Orders.buildData(null);
                Tab tab_Orders=new Tab("orders",Orders.tableview);
                tabPane.getTabs().addAll(tab_Orders);
                
                
	            
                
                
                
                
                
                
                
                Button clearButton = new Button("Clear");
    	        clearButton.setOnAction(e -> clearTextFields(vvbox));
    	        clearButton.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
                
                Address.searchAddress();
				Address.insertAddress();
				
				Address.updateAddress();
				Address.DeleteAddress();
	            
				vvbox.getChildren().add(clearButton);
				 VBox.setVgrow(clearButton, Priority.ALWAYS);
			        vvbox.setAlignment(Pos.CENTER_LEFT);
	            
	       
	
		vvbox.setSpacing(10);
	
		

	        
		
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
					
					if (newValue != null) {

						String selectedTableName = newValue.getText();
						System.out.println("Selected Table Name: " + selectedTableName);
						//-------------------------------------------
						
						// _________________ADDRESS_______________________________
						if (selectedTableName.equals("address")) {
							

							try {
							Address.searchAddress();
							Address.insertAddress();
							
							Address.updateAddress();
							Address.DeleteAddress();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// ______________________CAR_PART__________________________
						if (selectedTableName.equals("car_part")) {

							try {
								displayInfoAlert("PLS Update the part only ! ");
								Part_Car.searchCar_Part();
								
							Part_Car.	insertCarPart();
							
							Part_Car.	updateCar_Part();
							Part_Car.DeleteCar_Part();
							Part_Car.DeleteCar_Part2();
							
							
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// _________________________CAR___________________________
						if (selectedTableName.equals("car")) {
						try {
							car.	searchCar();
							car.	insertCar();
							car.	updateCar();
							car.	DeleteCar();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();}}
						// ___________________CUSTOMER___________________________
						if (selectedTableName.equals("customer")) {
							try {
								customer.searchCar();
								customer.insertCustomer();
								customer.updateCustomer();
								customer.DeleteCustomer();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// ___________________DEVICE___________________________
						if (selectedTableName.equals("device")) {
							try {
								Device.searchDevice();
								Device.insertDevice();
								Device.	updateDevice();
							    Device.	DeleteDevice();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// ___________________MANUFACTURE___________________________
						if (selectedTableName.equals("manufacture")) {
							try {
								Manufacture.searchManufacture();
								Manufacture.insertManufacture();
							Manufacture.	updateManufacture();
							Manufacture.DeleteManufacture();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// ___________________ORDERS___________________________
						if (selectedTableName.equals("orders")) {
							try {
								Orders.searchOrders();
							Orders.	insertOrders();
							Orders.	updateOrders();
							Orders.	DeleteOrders();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();	}
							}
		
						
						}
					vvbox.getChildren().add(clearButton);
						 VBox.setVgrow(clearButton, Priority.ALWAYS);
					        vvbox.setAlignment(Pos.CENTER_LEFT);
					});
	
		
		
		 
		splitPane.getItems().addAll(vvbox, tabPane);
		
		Label saja=new Label("MADE BY SAJA LAHALIH 2023 -202109972");
		saja.setFont(javafx.scene.text.Font.font("Courier New", 30));
		 BorderPane borderPane = new BorderPane();
		 
		// borderPane.setPadding(new Insets(10, 10, 10, 10)); 
		 
		 BorderPane.setMargin(saja, new Insets(10, 10, 10, 100));
	        borderPane.setCenter(splitPane);
	        borderPane.setBottom(saja);
		
		 
		
		splitPane.setDividerPosition(0, 0.5);

		Scene scene = new Scene(borderPane, 800, 600);
		
		String imageUrl = getClass().getResource("e.jfif").toExternalForm();
	        BackgroundImage backgroundImage = new BackgroundImage(
	                new javafx.scene.image.Image(imageUrl, 0, 0, false, true),
	                BackgroundRepeat.REPEAT,
	                BackgroundRepeat.REPEAT,
	                BackgroundPosition.DEFAULT,
	                new BackgroundSize(.5, 1.0, true, true, false, false)
	        );

	        // Set the background to the VBox
	        splitPane.setBackground(new Background(backgroundImage));

		  

		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		stage.show();
		
	}
	
	


	private void displayMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();}
	
	 public static void clearTextFields(HBox vvbox2) {
	        for (Node node : vvbox2.getChildren()) {
	            if (node instanceof VBox) {
	                for (Node innerNode : ((VBox) node).getChildren()) {
	                    if (innerNode instanceof TextField) {
	                        ((TextField) innerNode).clear();
	                    } else if (innerNode instanceof ComboBox) {
	                        ((ComboBox<?>) innerNode).getSelectionModel().clearSelection();
	                    }
	                    
	                }
	            }
	        }
	    }
		private static void displayInfoAlert(String message) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
	
	
	
	
	
}
									
