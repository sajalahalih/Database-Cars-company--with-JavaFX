import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class Orders {
	
	static void setFont(Label l) {
		l.setFont(javafx.scene.text.Font.font("Courier New", 20));
		
	}
	static void setFontb(Button b) {
		b.setStyle("-fx-font-weight: bold");
		
	}
	
	
	
	
	private static ObservableList<ObservableList> data;

	static TableView tableview;
	public static void buildData(String sql) {
		data = FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");

			Statement stmt = con.createStatement();

			if (sql == null) {
				sql = "select * from orders";
			}

			ResultSet rs = stmt.executeQuery(sql);

			tableview.getColumns().clear();
			tableview.getItems().clear();

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableview.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
			}

			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			tableview.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	
	
	
	
	static // ________________________________SEARCH Orders________________________________________________
	VBox OrdersHboxsearch = new VBox();


	public static void searchOrders() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		OrdersHboxsearch.getChildren().clear();
		
		
		Label id = new Label("id");
		setFont(id);
		TextField tid = new TextField();
		
		
		Label date = new Label("date");
		setFont(date);
		TextField tdate = new TextField();
		
		
		Label customer = new Label("customer");
		setFont(customer);	
		TextField tcustomer = new TextField();
		
		
		Label car = new Label("car");
		setFont(car);
		TextField tcar = new TextField();
		
		
		Button b = new Button("search");
		setFontb(b);	

		OrdersHboxsearch.getChildren().addAll(id, tid, date, tdate, customer, tcustomer, car, tcar, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(OrdersHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from orders " );
				String q2=new String("");
				
				String sid = tid.getText();
				String sdate = tdate.getText();
				String scustomer = tcustomer.getText();
				String scar = tcar.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit2 = true;
				for (int i = 0; i < scustomer.length(); i++) {
					if(!(Character.isDigit(scustomer.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				boolean isDigit3 = true;
				for (int i = 0; i < sdate.length(); i++) {
					if(!(Character.isDigit(sdate.charAt(i)))) {
						isDigit3=false;
					}
					
				}
				if(isDigit1==false || isDigit2==false|| isDigit3==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
				

				if ((sdate.isEmpty()) && (sdate.isEmpty()) && (scustomer.isEmpty()) && (scar.isEmpty())) {

					q2="select * from orders " ;
				}else {
					q+=" WHERE ";
					if(!sid.isEmpty()) {
						q+=" id = '"+sid +"' AND ";
					}
					if(!sdate.isEmpty()) {
						q+=" date = '"+sdate +"' AND ";
					}
					if(!scustomer.isEmpty()) {
						q+=" customer = '"+scustomer +"' AND ";
					}
					if(!scar.isEmpty()) {
						q+=" car = '"+scar +"' AND ";
					}
					
					
								if(q.endsWith("' AND " )) {
						for (int i = 0; i < q.length()-5; i++) {
							q2+=q.charAt(i);
							
						}
						System.out.println(q2);
						
					}
								}
				
				
				
	                  

	                buildData(q2);
				}

	                con.close();

			} catch (Exception e2) {
				displayInfoAlert("Enter the element correct !");
				e2.printStackTrace(); // Handle exceptions appropriately in your application
			}
		});
	}
	

	// __________________________________ORDER__________________________________________-
	static VBox orderHboxinsert = new VBox();
	static String CustomerStr_order;
	static String CarStr_order;
	static void insertOrders() throws SQLException, ClassNotFoundException {
		
		AssSecondUpdate.vvbox.getChildren().clear();
		orderHboxinsert.getChildren().clear();
		
		Label id = new Label("id");
		TextField tid = new TextField();
		Label date = new Label("date");
		TextField tdate = new TextField();
		Label customer = new Label("customer");
		Label car = new Label("car");
		Button b = new Button("insert");
		setFontb(b);
		setFont(id);
		setFont(date);
		setFont(car);
		setFont(customer);
		

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery("select id From customer ");// car
		ArrayList<String> arr = new ArrayList();

		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);
		}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBox_customer = new ComboBox<>(manu);	
		ResultSet r2 = stmt.executeQuery("select name From car ");// customer
		ArrayList<String> arr2 = new ArrayList();
		while (r2.next()) {
			String x = r2.getString(1);
			arr2.add(x);
		}
		ObservableList<String> manu1 = FXCollections.observableArrayList(arr2);
		ComboBox<String> comboBox_car = new ComboBox<>(manu1);

		orderHboxinsert.getChildren().addAll(id, tid, date, tdate, customer, comboBox_customer, car, comboBox_car, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(orderHboxinsert);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});
		comboBox_customer.setOnAction(event -> {
			CustomerStr_order = comboBox_customer.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + CustomerStr_order);

		});
		comboBox_car.setOnAction(event -> {
			CarStr_order = comboBox_car.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + CarStr_order);

		});

		b.setOnAction(e -> {
			try {
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt1 = con1.createStatement();

				ResultSet rs = stmt1.executeQuery("SELECT * FROM manufacture");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");

				String sid = tid.getText();
				String sdate = tdate.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit3 = true;
				for (int i = 0; i < sdate.length(); i++) {
					if(!(Character.isDigit(sdate.charAt(i)))) {
						isDigit3=false;
					}
					
				}
				
				if(isDigit1==false||isDigit3==false ) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
					ResultSet r5 = stmt1.executeQuery("select id From orders");
					ArrayList<String> arr5 = new ArrayList<>();

					while (r5.next()) {
						String x = r5.getString(1);
						arr5.add(x);
					}
					boolean key=false;
					for (String ele : arr5) {
						if (ele.equals(sid)) {key=true;
				
						}
					}
					
					if(key==true) {
						displayInfoAlert("THE ID ALREADY EXIST");
					}else {

				if (!(sid.isEmpty()) && !(sdate.isEmpty()) && !(CustomerStr_order.isEmpty())	&& !(CarStr_order.isEmpty())) {
					ArrayList<String> textFields = new ArrayList<>();
					textFields.add(sid);
					textFields.add(sdate);
					textFields.add(CustomerStr_order);
					textFields.add(CarStr_order);

					int count = 0;

					for (int i = 0; i < textFields.size(); i++) {
						String columnName = textFields.get(i);
						if (!(columnName.isEmpty())) {
							count++;
							try {
								columns.append(metaData.getColumnName(i + 1));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							values.append("'").append(columnName).append("'");

							if (i < textFields.size() - 1) {
								columns.append(",");
								values.append(",");
							}
						}
					}
					columns.append(")");
					values.append(")");
					StringBuilder q2 = new StringBuilder("");
					try {
						if (metaData.getColumnCount() != count) {

							for (int j = 0; j < columns.length() - 2; j++) {
								q2.append(columns.charAt(j));
								System.out.println("g11111" + q2.toString());
								System.out.println("qiereeeeee" + columns.charAt(j));
							}
							q2.append(")");

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					String query = "INSERT INTO orders " + q2.toString() + " VALUES " + values.toString();

					StringBuilder q = new StringBuilder("");

					if (metaData.getColumnCount() != count) {
						for (int j = 0; j < query.length() - 2; j++) {
							q.append(query.charAt(j));
							System.out.println("g11111" + q.toString());
							System.out.println("qiereeeeee" + query.charAt(j));
						}
						q.append(")");

					} else {
						q = q.append(query);
					}
					System.out.println("inserttttttttttttttttttttttt" + query);
					System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq" + q.toString());
					PreparedStatement pstmt = con.prepareStatement(q.toString());
					pstmt.executeUpdate();
					
					 buildData(null);
					 displayInfoAlert("Inserted succesfully");
					
					
				} else {
					displayInfoAlert("Enter all the element! ");
					System.out.println("Enter all the element ");
				}
				}
				}
				} catch (Exception e2) {}	});}		


	static // ________________________________UPDATE ORDERS________________________________________________
	VBox orderHboxupdate = new VBox();
	static String updaetOrderIDStr;
	static String updaetOrderCustomerStr;
	static String updaetIrderCarStr;
	public static void updateOrders() throws ClassNotFoundException, SQLException {
		
		AssSecondUpdate.vvbox.getChildren().clear();
		orderHboxupdate.getChildren().clear();
		
		Label id = new Label("id");
		Label date = new Label("date");
		TextField tdate = new TextField();
		Label customer = new Label("customer");
		Label car = new Label("car");
		Button b = new Button("update");
		setFontb(b);
		setFont(id);
		setFont(date);
		setFont(car);
		setFont(customer);
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery("select id From orders");
		ArrayList<String> arr = new ArrayList<>();
		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);}	
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxID = new ComboBox<>(manu);
		Statement stmt1 = con.createStatement();
		ResultSet rMade = stmt1.executeQuery("select id From customer ");
		ArrayList<String> arrMade = new ArrayList<>();
		while (rMade.next()) {
			String x = rMade.getString(1);
			arrMade.add(x);}
		ObservableList<String> manu1 = FXCollections.observableArrayList(arrMade);
		ComboBox<String> comboBoxCustomre = new ComboBox<>(manu1);		
		Statement stmt2 = con.createStatement();
		ResultSet rcar = stmt2.executeQuery("select name From car ");
		ArrayList<String> arrcar = new ArrayList<>();
		while (rcar.next()) {
			String x = rcar.getString(1);
			arrcar.add(x);}
		ObservableList<String> manu2 = FXCollections.observableArrayList(arrcar);
		ComboBox<String> comboBoxCar = new ComboBox<>(manu2);

		orderHboxupdate.getChildren().addAll(id, comboBoxID, date, tdate, customer, comboBoxCustomre, car, comboBoxCar, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(orderHboxupdate);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});
		comboBoxID.setOnAction(event -> {
			updaetOrderIDStr = comboBoxID.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetOrderIDStr);
		});
		comboBoxCustomre.setOnAction(event -> {
			updaetOrderCustomerStr = comboBoxCustomre.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetOrderCustomerStr);
		});
		comboBoxCar.setOnAction(event -> {
			updaetIrderCarStr = comboBoxCar.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " +  updaetIrderCarStr);
		});
		b.setOnAction(e -> {			
			try {	
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");			
				StringBuilder update = new StringBuilder("UPDATE orders ");
				StringBuilder set = new StringBuilder("SET ");
				StringBuilder where = new StringBuilder("WHERE ");
                String sdate = tdate.getText();
                boolean isDigit1 = true;
				for (int i = 0; i < sdate.length(); i++) {
					if(!(Character.isDigit(sdate.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				
				if(isDigit1==false ) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
				if (!(updaetIrderCarStr.isEmpty()) && !(sdate.isEmpty()) && !(updaetOrderCustomerStr.isEmpty())&& !(updaetOrderIDStr.isEmpty())) {
					set.append("id= '" + updaetOrderIDStr + "' , date= '" + sdate + "' , customer= '" + updaetOrderCustomerStr
							+ "' , car= '" + updaetIrderCarStr + "' ");
					where.append("id= '" + updaetOrderIDStr + "' ;");
					String query = update.toString() + set.toString() + where.toString();
					System.out.println("update orders    " + query);
					PreparedStatement pstmt = con1.prepareStatement(query);
					pstmt.executeUpdate();
					
					 buildData(null);
					 displayInfoAlert("Updated succesfully");
					
				} else {
					displayInfoAlert("Enter all the element !");
					System.out.println("Enter all the elements ");
				}
				
				}
				} catch (Exception e2) {	e2.printStackTrace(); }});}
	
		
				//________________________________DELET ORDERS_______________________________________
				static VBox HboxDeleteOrdersID=new VBox();
				static String DeleteOrdersIDStr;		
				public static void DeleteOrders() throws ClassNotFoundException, SQLException {
					AssSecondUpdate.vvbox.getChildren().clear();
					HboxDeleteOrdersID.getChildren().clear();
					
					Label id = new Label("id");
					Button b=new Button("Delete");
					setFontb(b);
					setFont(id);
					
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					Statement stmt = con.createStatement();
					ResultSet r = stmt.executeQuery("select id From orders");
					ArrayList<String> arr = new ArrayList<>();
					while (r.next()) {
						String x = r.getString(1);
						arr.add(x);}	
					ObservableList<String> manu = FXCollections.observableArrayList(arr);
					ComboBox<String> comboBoxName = new ComboBox<>(manu);
					HboxDeleteOrdersID.getChildren().addAll(id,comboBoxName,b);
					Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
						AssSecondUpdate.vvbox.getChildren().add(HboxDeleteOrdersID);
						AssSecondUpdate.vvbox.layout(); });// Update the layout to reflect the changes});	
					comboBoxName.setOnAction(event -> {
						DeleteOrdersIDStr = comboBoxName.getSelectionModel().getSelectedItem();
						System.out.println("Selected value: " + DeleteOrdersIDStr);
					});
					b.setOnAction(e -> {				
						try {	
							
						//	AssSecondUpdate.vvbox.getChildren().clear();
							Class.forName("com.mysql.jdbc.Driver");
							Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");						
							StringBuilder update = new StringBuilder("DELETE FROM orders ");
							StringBuilder where = new StringBuilder("WHERE ");
							where.append("id ='"+DeleteOrdersIDStr+"';");
							String query = update.toString() + where.toString();
							System.out.println("update orders    " + query);
							PreparedStatement pstmt = con1.prepareStatement(query);
							pstmt.executeUpdate();
							
							 buildData(null);
							 
							 displayInfoAlert("Deleted succesfully");
							
							
						} catch (Exception e2) {	e2.printStackTrace(); }});}
				
				private static void displayInfoAlert(String message) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(message);
					alert.showAndWait();}

}
