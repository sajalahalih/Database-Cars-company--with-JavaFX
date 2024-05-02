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

public class customer {
	
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
				sql = "select * from customer";
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
	
	
	static // ________________________________SEARCH CUSTOMER________________________________________________
	VBox customerHboxsearch = new VBox();


	public static void searchCar() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		customerHboxsearch.getChildren().clear();
		
		
		Label id = new Label("id");
		setFont(id);
		TextField tid = new TextField();
		
		
		Label f_name = new Label("f_name");
		setFont(f_name);
		TextField tf_name = new TextField();
		
		
		Label l_name = new Label("l_name");
		setFont(l_name);	
		TextField tl_name = new TextField();
		
		
		Label address = new Label("address");
		TextField taddress = new TextField();
		
		Label job = new Label("job");
		TextField tjob = new TextField();
		
		Button b = new Button("search");
		setFontb(b);	

		customerHboxsearch.getChildren().addAll(id, tid, f_name, tf_name, l_name, tl_name, address, taddress,job,tjob, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(customerHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from customer " );
				String q2=new String("");
				
				String sid = tid.getText();
				String sf_name = tf_name.getText();
				String sl_name = tl_name.getText();
				String saddress = taddress.getText();
				String sjob = tjob.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit2 = true;
				for (int i = 0; i < saddress.length(); i++) {
					if(!(Character.isDigit(saddress.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				if(isDigit1==false || isDigit2==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

				if ((sid.isEmpty()) && (sf_name.isEmpty()) && (sl_name.isEmpty()) && (saddress.isEmpty())&& (sjob.isEmpty())) {

					q2="select * from customer " ;
				}else {
					q+=" WHERE ";
					if(!sid.isEmpty()) {
						q+=" id = '"+sid +"' AND ";
					}
					if(!sf_name.isEmpty()) {
						q+=" f_name = '"+sf_name +"' AND ";
					}
					if(!sl_name.isEmpty()) {
						q+=" l_name = '"+sl_name +"' AND ";
					}
					if(!saddress.isEmpty()) {
						q+=" address = '"+saddress +"' AND ";
					}
					if(!sjob.isEmpty()) {
						q+=" job = '"+sjob +"' AND ";
					}
					
								if(q.endsWith("' AND " )) {
						for (int i = 0; i < q.length()-5; i++) {
							q2+=q.charAt(i);
							
						}
						System.out.println(q2);
						
					}
								}
				
				
	                buildData(q2);}	                

	                con.close();

			} catch (Exception e2) {
				displayInfoAlert("Enter the element correct !");
				e2.printStackTrace(); // Handle exceptions appropriately in your application
			}
		});
	}
	
	
	static // _______________________CUSTOMER_____________________________________________

	VBox cusromerHboxinsert = new VBox();
	static String customerStr;

	static void insertCustomer() throws SQLException, ClassNotFoundException {
		
		AssSecondUpdate.vvbox.getChildren().clear();
		cusromerHboxinsert.getChildren().clear();

		Label id = new Label("id");
		
		TextField tid = new TextField();

		Label f_name = new Label("f_name");
		TextField tf_name = new TextField();

		Label l_name = new Label("l_name");
		TextField tl_name = new TextField();

		Label address = new Label("address");

		Label job = new Label("job");
		TextField tjob = new TextField();

		Button b = new Button("insert");
		setFontb(b);
		setFont(id);
		setFont(f_name);
		setFont(l_name);
		setFont(address);
		setFont(job);
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();

		ResultSet r = stmt.executeQuery("select id From address");
		ArrayList<String> arr = new ArrayList();

		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);
		}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBox = new ComboBox<>(manu);

		cusromerHboxinsert.getChildren().addAll(id, tid, f_name, tf_name, l_name, tl_name, address, comboBox, job, tjob,
				b);

		// HERE THE HBOX SHOWSSSSSSSS
		Platform.runLater(() -> {
			AssSecondUpdate.vvbox.getChildren().add(cusromerHboxinsert);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBox.setOnAction(event -> {
			customerStr = comboBox.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + customerStr);

		});

		b.setOnAction(e -> {
			try {
			//	AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt1 = con1.createStatement();

				ResultSet rs = stmt1.executeQuery("SELECT * FROM customer");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");

				String sid = tid.getText();
				String sFname = tf_name.getText();
				String sLname = tl_name.getText();
				String sjob = tjob.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				
				if(isDigit1==false ) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
					
					ResultSet r5 = stmt.executeQuery("select id From customer");
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

				if (!(sid.isEmpty()) && !(sFname.isEmpty()) && !(sLname.isEmpty()) && !(sjob.isEmpty())
						&& !(customerStr.isEmpty())) {

					ArrayList<String> textFields = new ArrayList<>();
					textFields.add(sid);
					textFields.add(sFname);
					textFields.add(sLname);
					textFields.add(customerStr);
					textFields.add(sjob);

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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String query = "INSERT INTO customer " + q2.toString() + " VALUES " + values.toString();

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
					displayInfoAlert("Enter all the element !");
					System.out.println("Enter all the element ");
				}
				}
				}
			} catch (Exception e2) {
				System.out.println("lllllllllllllllllllllllllllllll");	}	});}
	

	static // ________________________________UPDATE CUSTOMER________________________________________________
	VBox customerHboxupdate = new VBox();
	static String updaetCarIDStr;
	static String updaetCarAddressStr;
	public static void updateCustomer() throws ClassNotFoundException, SQLException {
		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		AssSecondUpdate.vvbox.getChildren().clear();
		customerHboxupdate.getChildren().clear();
		
		
		Label id = new Label("id");
		Label f_name = new Label("f_name");
		TextField tf_name = new TextField();
		Label l_name = new Label("l_name");
		TextField tl_name = new TextField();
		Label address = new Label("address");
		Label job = new Label("job");
		TextField tjob = new TextField();
		Button b = new Button("update");
		setFontb(b);
		setFontb(b);
		setFont(id);
		setFont(f_name);
		setFont(l_name);
		setFont(address);
		setFont(job);

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();

		ResultSet r = stmt.executeQuery("select id From customer");
		ArrayList<String> arr = new ArrayList<>();

		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxID = new ComboBox<>(manu);

		Statement stmt1 = con.createStatement();

		ResultSet raddress = stmt1.executeQuery("select id From address ");
		ArrayList<String> arrMade = new ArrayList<>();

		while (raddress.next()) {
			String x = raddress.getString(1);
			arrMade.add(x);}
		ObservableList<String> manu1 = FXCollections.observableArrayList(arrMade);
		ComboBox<String> comboBoxaddress = new ComboBox<>(manu1);

		customerHboxupdate.getChildren().addAll(id, comboBoxID, f_name, tf_name, l_name, tl_name, address,comboBoxaddress, job, tjob, b);	
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.	vvbox.getChildren().add(customerHboxupdate);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBoxID.setOnAction(event -> {
			updaetCarIDStr = comboBoxID.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetCarIDStr);
		});
		comboBoxaddress.setOnAction(event -> {
			updaetCarAddressStr = comboBoxaddress.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetCarAddressStr);
		});

		b.setOnAction(e -> {
			try {
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				StringBuilder update = new StringBuilder("UPDATE customer ");
				StringBuilder set = new StringBuilder("SET ");
				StringBuilder where = new StringBuilder("WHERE ");

				String sf_name = tf_name.getText();
				String sl_name = tl_name.getText();
				String sjob = tjob.getText();

				if (!(sf_name.isEmpty()) && !(sl_name.isEmpty()) && !(sjob.isEmpty())
						&& !(updaetCarAddressStr.isEmpty()) && !(updaetCarIDStr.isEmpty())) {

					set.append("id= '" + updaetCarIDStr + "' , f_name= '" + sf_name + "' , l_name= '" + sl_name
							+ "' , address= '" + updaetCarAddressStr + "' , job= '" + sjob + "' ");
					where.append("id= '" + updaetCarIDStr  + "' ;");

					String query = update.toString() + set.toString() + where.toString();

					System.out.println("update customer    " + query);

					PreparedStatement pstmt = con1.prepareStatement(query);

					pstmt.executeUpdate();
					 buildData(null);
					 displayInfoAlert("Updated succesfully");
					
				} else {
					displayInfoAlert("Enter all the element !");
					System.out.println("Enter all the elements ");
				}
			} catch (Exception e2) {
				e2.printStackTrace(); }	});}// Handle exceptions appropriately in your application
	
	//________________________________DELET CUSTOMER_______________________________________
	static VBox DeleteCustomerID=new VBox();
	static String CarCustomerID;		
	public static void DeleteCustomer() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		DeleteCustomerID.getChildren().clear();
		
		Label id = new Label("id");
		Button b=new Button("Delete");
		setFontb(b);
		setFontb(b);
		setFont(id);
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery("select id From customer");
		ArrayList<String> arr = new ArrayList<>();
		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);}	
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxID = new ComboBox<>(manu);
		DeleteCustomerID.getChildren().addAll(id,comboBoxID,b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.	vvbox.getChildren().add(DeleteCustomerID);
			AssSecondUpdate.	vvbox.layout(); // Update the layout to reflect the changes
		});
		comboBoxID.setOnAction(event -> {
			CarCustomerID = comboBoxID.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + CarCustomerID);
		});
		b.setOnAction(e -> {
			try {
			//	AssSecondUpdate.	vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");						
				StringBuilder update = new StringBuilder("DELETE FROM customer ");
				StringBuilder where = new StringBuilder("WHERE ");
				where.append("id ='"+CarCustomerID+"';");
				String query = update.toString() + where.toString();
				System.out.println("update customer    " + query);
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
