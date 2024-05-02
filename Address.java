import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;

import javafx.scene.text.FontWeight;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Address  {
	
	private static ObservableList<ObservableList> data;

	static TableView tableview;
	
	static void setFont(Label l) {
		l.setFont(javafx.scene.text.Font.font("Courier New", 20));
		
	}
	static void setFontb(Button b) {
		b.setStyle("-fx-font-weight: bold");
		
	}
	
	
	public static void buildData(String sql) {
		data = FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");

			Statement stmt = con.createStatement();

			if (sql == null) {
				sql = "select * from address";
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
	
	
	
	static // ________________________________SEARCH ADDRESS________________________________________________
	VBox addressHboxsearch = new VBox();


	public static void searchAddress() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		addressHboxsearch.getChildren().clear();
		
		
		Label id = new Label("id");// combo
		setFont(id);
		TextField tid = new TextField();
		Label building = new Label("building");
		setFont(building);
		
		TextField tbuilding = new TextField();
		Label street = new Label("street");
		setFont(street);
		
		TextField tstreet = new TextField();
		Label city = new Label("city");
		setFont(city);
		
		TextField tcity = new TextField();
		Label country = new Label("country");
		setFont(country);
		
		TextField tcountry = new TextField();
		Button b = new Button("search");
		setFontb(b);	

		addressHboxsearch.getChildren().addAll(id,tid, building, tbuilding, street, tstreet, city, tcity, country,
				tcountry, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(addressHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
				//addressHboxsearch.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from address " );
				String q2=new String("");
				
				String sid = tid.getText();
				String sbuilding = tbuilding.getText();
				String sstreet = tstreet.getText();
				String scity = tcity.getText();
				String scountry = tcountry.getText();
				
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit2 = true;
				for (int i = 0; i < sbuilding.length(); i++) {
					if(!(Character.isDigit(sbuilding.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				if(isDigit1==false || isDigit2==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
				
				
				if(sid.isEmpty() && sbuilding.isEmpty() && sstreet.isEmpty() && scity.isEmpty() && scity.isEmpty()) {
					q2="select * from address " ;
				}else {
					q+=" WHERE ";
					if(!sid.isEmpty()) {
						q+=" id = '"+sid +"' AND ";
					}
					if(!sbuilding.isEmpty()) {
						q+=" buidling = '"+sbuilding +"' AND ";
					}
					if(!sstreet.isEmpty()) {
						q+=" street = '"+sstreet +"' AND ";
					}
					if(!scity.isEmpty()) {
						q+=" city = '"+scity +"' AND ";
					}
					if(!scountry.isEmpty()) {
						q+=" country = '"+scountry +"' AND ";
					}
								if(q.endsWith("' AND " )) {
						for (int i = 0; i < q.length()-5; i++) {
							q2+=q.charAt(i);
							
						}
						System.out.println(q2+"kkkkkkkkkkkkkkkkkkkkkkkkkk");
						
					}
								}
				
			
	                
	                  buildData(q2);
				}
	                
	                

	                con.close();

			} catch (Exception e2) {
				e2.printStackTrace(); // Handle exceptions appropriately in your application
			}
		});
	}

	static // ________________________________INSERT ADDRESS________________________________________________

	VBox addressHboxinsert = new VBox();

	public static void insertAddress() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		addressHboxinsert.getChildren().clear();
		Label id = new Label("id");
		setFont(id);
		
		TextField tid = new TextField();
		Label building = new Label("building");
		setFont(building);
		TextField tbuidling = new TextField();
		Label street = new Label("street");
		setFont(street);
		TextField tstreet = new TextField();
		Label city = new Label("city");
		setFont(city);
		TextField tcity = new TextField();
		Label country = new Label("country");
		setFont(country);
		TextField tcountry = new TextField();
		Button b = new Button("insert");
		setFontb(b);
		addressHboxinsert.getChildren().addAll(id, tid, building, tbuidling, street, tstreet, city, tcity, country,tcountry, b);	
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(addressHboxinsert);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});
		b.setOnAction(e -> {
			try {
				//addressHboxinsert.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM address");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");
				String sid = tid.getText();
				String sbuidling = tbuidling.getText();
				String sstreet = tstreet.getText();
				String scity = tcity.getText();
				String scountry = tcountry.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sid.length(); i++) {
					if(!(Character.isDigit(sid.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit2 = true;
				for (int i = 0; i < sbuidling.length(); i++) {
					if(!(Character.isDigit(sbuidling.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				if(isDigit1==false || isDigit2==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
					ResultSet r = stmt.executeQuery("select id From address");
					ArrayList<String> arr = new ArrayList<>();

					while (r.next()) {
						String x = r.getString(1);
						arr.add(x);
					}
					boolean key=false;
					for (String ele : arr) {
						if (ele.equals(sid)) {key=true;
				
						}
					}
					
					if(key==true) {
						displayInfoAlert("THE ID ALREADY EXIST");
					}else {
						
					
				 

				if (!(sid.isEmpty()) && !(sbuidling.isEmpty()) && !(sstreet.isEmpty()) && !(scity.isEmpty())
						&& !(scountry.isEmpty())) {

					ArrayList<TextField> textFields = new ArrayList<>();
					textFields.add(tid);
					textFields.add(tbuidling);
					textFields.add(tstreet);
					textFields.add(tcity);
					textFields.add(tcountry);
				
					
					

					int count = 0;

					for (int i = 0; i < textFields.size(); i++) {
						String columnName = textFields.get(i).getText();
						if (!(columnName.isEmpty())) {
							count++;
							try {
								columns.append(metaData.getColumnName(i + 1));
							} catch (SQLException e1) {
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
					String query = "INSERT INTO address " + q2.toString() + " VALUES " + values.toString();

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
					displayInfoAlert("Inserted succesfully");
					buildData(null); 
					
					
			

					
					 
				} else {
					displayInfoAlert("Enter all the element ");
					System.out.println("Enter all the element ");
				}
					}
				}
				} catch (Exception e2) {}	});}
   
	static // ________________________________UPDATE ADDRESS________________________________________________
	VBox addressHboxupdate = new VBox();
	static String updaetAddressStr;

	public static void updateAddress() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		addressHboxupdate.getChildren().clear();
		Label id = new Label("id");// combo
		setFont(id);
		Label building = new Label("building");
		setFont(building);
		
		TextField tbuilding = new TextField();
		Label street = new Label("street");
		setFont(street);
		
		TextField tstreet = new TextField();
		Label city = new Label("city");
		setFont(city);
		
		TextField tcity = new TextField();
		Label country = new Label("country");
		setFont(country);
		
		TextField tcountry = new TextField();
		Button b = new Button("update");
		setFontb(b);

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();

		ResultSet r = stmt.executeQuery("select id From address");
		ArrayList<String> arr = new ArrayList<>();

		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);
		}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBox = new ComboBox<>(manu);

		addressHboxupdate.getChildren().addAll(id, comboBox, building, tbuilding, street, tstreet, city, tcity, country,
				tcountry, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(addressHboxupdate);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBox.setOnAction(event -> {
			updaetAddressStr = comboBox.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetAddressStr);
		});

		b.setOnAction(e -> {
			try {
			//	addressHboxupdate.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				StringBuilder update = new StringBuilder("UPDATE address ");
				StringBuilder set = new StringBuilder("SET ");
				StringBuilder where = new StringBuilder("WHERE ");

				String sbuilding = tbuilding.getText();
				String sstreet = tstreet.getText();
				String scity = tcity.getText();
				String scountry = tcountry.getText();
				
				
				boolean isDigit2 = true;
				for (int i = 0; i < sbuilding.length(); i++) {
					if(!(Character.isDigit(sbuilding.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				if( isDigit2==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

				if (!(updaetAddressStr.isEmpty()) && !(sbuilding.isEmpty()) && !(sstreet.isEmpty())
						&& !(scity.isEmpty()) && !(scountry.isEmpty())) {

					set.append("id= '" + updaetAddressStr + "' , buidling= '" + sbuilding + "' , street= '" + sstreet
							+ "' , city= '" + scity + "' , country= '" + scountry + "' ");
					where.append("id= '" + updaetAddressStr + "' ;");

					String query = update.toString() + set.toString() + where.toString();

					System.out.println("update address    " + query);

					PreparedStatement pstmt = con1.prepareStatement(query);

					pstmt.executeUpdate();
					buildData(null); 
					 displayInfoAlert("Updated succesfully");
					
				} else {
					displayInfoAlert("Enter all the element !");
					System.out.println("Enter all the elements ");
				}
				}
			} catch (Exception e2) {
				e2.printStackTrace(); // Handle exceptions appropriately in your application
			}
		});
	}

	// ________________________________DELET ADDRESS_______________________________________
	static VBox DeleteAddressID = new VBox();
	static String DeleteAddressIDStr;

	public static void DeleteAddress() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		DeleteAddressID.getChildren().clear();
		
		
		Label id = new Label("id");
		setFont(id);
		
		Button b = new Button("Delete");
		setFontb(b);
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery("select id From address");
		ArrayList<String> arr = new ArrayList<>();
		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);
		}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxID = new ComboBox<>(manu);
		DeleteAddressID.getChildren().addAll(id, comboBoxID, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(DeleteAddressID);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});
		comboBoxID.setOnAction(event -> {
			DeleteAddressIDStr = comboBoxID.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + DeleteAddressIDStr);
		});
		b.setOnAction(e -> {
			try {
				
			//	DeleteAddressID.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				StringBuilder update = new StringBuilder("DELETE FROM address ");
				StringBuilder where = new StringBuilder("WHERE ");
				where.append("id ='" + DeleteAddressIDStr + "';");
				String query = update.toString() + where.toString();
				System.out.println("update orders    " + query);
				PreparedStatement pstmt = con1.prepareStatement(query);
				pstmt.executeUpdate();
				
				buildData(null); 
				 displayInfoAlert("Deleted succesfully");
				
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
	}

	private void displayMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private static void displayInfoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	
	
	 

}
