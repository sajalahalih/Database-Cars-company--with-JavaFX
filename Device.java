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

public class Device {
	
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
				sql = "select * from device";
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
	
	
	
	static // ________________________________SEARCH DEVICE________________________________________________
	VBox deviceHboxsearch = new VBox();


	public static void searchDevice() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		deviceHboxsearch.getChildren().clear();
		
		
		Label no = new Label("no");
		setFont(no);
		TextField tno = new TextField();
		
		
		Label name = new Label("name");
		setFont(name);
		TextField tname = new TextField();
		
		
		Label price = new Label("price");
		setFont(price);	
		TextField tprice = new TextField();
		
		
		Label weight = new Label("weight");
		setFont(weight);
		TextField tweight = new TextField();
		
		Label made = new Label("made");
		setFont(made);
		TextField tmade = new TextField();
		
		Button b = new Button("search");
		setFontb(b);	

		deviceHboxsearch.getChildren().addAll(no, tno, name, tname, price, tprice, weight, tweight,made,tmade, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(deviceHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
			//	AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from device " );
				String q2=new String("");
				
				String sno = tno.getText();
				String sname = tname.getText();
				String sprice = tprice.getText();
				String sweight = tweight.getText();
				String smade = tmade.getText();
				
				boolean isDigit1 = true;
				for (int i = 0; i < sno.length(); i++) {
					if(!(Character.isDigit(sno.charAt(i)))) {
						isDigit1=false;
					}
					
				}
				boolean isDigit2 = true;
				for (int i = 0; i < sprice.length(); i++) {
					if(!(Character.isDigit(sprice.charAt(i)))) {
						isDigit2=false;
					}
					
				}
				boolean isDigit3 = true;
				for (int i = 0; i < sweight.length(); i++) {
					if(!(Character.isDigit(sweight.charAt(i)))) {
						isDigit3=false;
					}
					
				}
				if(isDigit1==false || isDigit2==false || isDigit3==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

				if ((sno.isEmpty()) && (sname.isEmpty()) && (sprice.isEmpty()) && (sweight.isEmpty())&& (smade.isEmpty())) {

					q2="select * from device " ;
				}else {
					q+=" WHERE ";
					if(!sno.isEmpty()) {
						q+=" no = '"+sno +"' AND ";
					}
					if(!sname.isEmpty()) {
						q+=" name = '"+sname +"' AND ";
					}
					if(!sprice.isEmpty()) {
						q+=" price = '"+sprice +"' AND ";
					}
					if(!sweight.isEmpty()) {
						q+=" weight = '"+sweight +"' AND ";
					}
					if(!smade.isEmpty()) {
						q+=" made = '"+smade +"' AND ";
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
	
	// ___________________________________________device____________________________________________________
		static VBox deviceHboxinsert = new VBox();
		static String deviceStr;
		static void insertDevice() throws SQLException, ClassNotFoundException {
			AssSecondUpdate.vvbox.getChildren().clear();
			deviceHboxinsert.getChildren().clear();
			
			Label no = new Label("no");
			TextField tno = new TextField();
			Label name = new Label("name");
			TextField tname = new TextField();
			Label price = new Label("price");
			TextField tprice = new TextField();
			Label weight = new Label("weight");
			TextField tweight = new TextField();

			Label made = new Label("made");

			Button b = new Button("insert");
			setFontb(b);
			
			setFont(no);
			setFont(name);
			setFont(price);
			setFont(weight);
			setFont(made);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();

			ResultSet r = stmt.executeQuery("select name From manufacture");
			ArrayList<String> arr = new ArrayList();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);
			}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBox = new ComboBox<>(manu);

			deviceHboxinsert.getChildren().addAll(no, tno, name, tname, price, tprice, weight, tweight, made, comboBox, b);
			Platform.runLater(() -> {		// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(deviceHboxinsert);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});

			comboBox.setOnAction(event -> {
				deviceStr = comboBox.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + deviceStr);

			});

			b.setOnAction(e -> {
				try {
					//AssSecondUpdate.vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM device");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sno = tno.getText();
					String sname = tname.getText();
					String sprice = tprice.getText();
					String sweight = tweight.getText();
					
					boolean isDigit1 = true;
					for (int i = 0; i < sno.length(); i++) {
						if(!(Character.isDigit(sno.charAt(i)))) {
							isDigit1=false;
						}
						
					}
					boolean isDigit2 = true;
					for (int i = 0; i < sprice.length(); i++) {
						if(!(Character.isDigit(sprice.charAt(i)))) {
							isDigit2=false;
						}
						
					}
					boolean isDigit3 = true;
					for (int i = 0; i < sweight.length(); i++) {
						if(!(Character.isDigit(sweight.charAt(i)))) {
							isDigit3=false;
						}
						
					}
					if(isDigit1==false || isDigit2==false || isDigit3==false ) {
						displayInfoAlert("Enter The Element AS Integer !");
					}
					else {
						
						ResultSet r5 = stmt.executeQuery("select no From device");
						ArrayList<String> arr5 = new ArrayList<>();

						while (r5.next()) {
							String x = r5.getString(1);
							arr5.add(x);
						}
						boolean key=false;
						for (String ele : arr5) {
							if (ele.equals(sno)) {key=true;
					
							}
						}
						
						if(key==true) {
							displayInfoAlert("THE NO ALREADY EXIST");
						}else {

					if (!(sno.isEmpty()) && !(sname.isEmpty()) && !(sprice.isEmpty()) && !(sweight.isEmpty())
							&& !(deviceStr.isEmpty())) {

						ArrayList<String> textFields = new ArrayList<>();
						textFields.add(sno);
						textFields.add(sname);
						textFields.add(sprice);
						textFields.add(sweight);
						textFields.add(deviceStr);
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
						String query = "INSERT INTO device " + q2.toString() + " VALUES " + values.toString();

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

				} catch (Exception e2) {	System.out.println("lllllllllllllllllllllllllllllll");	}});}
		
		static // ________________________________UPDATE DEVICE________________________________________________
		VBox deviceHboxupdate = new VBox();
		static String updaetDeviceNOStr;
		static String updaetDeviceMadeStr;
		public static void updateDevice() throws ClassNotFoundException, SQLException {
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			AssSecondUpdate.vvbox.getChildren().clear();
			deviceHboxupdate.getChildren().clear();
			
			Label no = new Label("no");
			Label name = new Label("name");
			TextField tname = new TextField();
			Label price = new Label("price");
			TextField tprice = new TextField();
			Label weight = new Label("weight");
			TextField tweight = new TextField();
			Label made = new Label("made");
			Button b = new Button("update");
			setFontb(b);
			setFont(no);
			setFont(name);
			setFont(price);
			setFont(weight);
			setFont(made);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select no From device");
			ArrayList<String> arr = new ArrayList<>();
			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}	
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxNO = new ComboBox<>(manu);
			Statement stmt1 = con.createStatement();
			ResultSet raddress = stmt1.executeQuery("select name From manufacture ");
			ArrayList<String> arrMade = new ArrayList<>();
			while (raddress.next()) {
				String x = raddress.getString(1);
				arrMade.add(x);}
			ObservableList<String> manu1 = FXCollections.observableArrayList(arrMade);
			ComboBox<String> comboBoxamade = new ComboBox<>(manu1);
			deviceHboxupdate.getChildren().addAll(no, comboBoxNO, name, tname, price, tprice, weight, tweight, made,
					comboBoxamade, b);
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(deviceHboxupdate);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});
			comboBoxNO.setOnAction(event -> {
				updaetDeviceNOStr = comboBoxNO.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + updaetDeviceNOStr);
			});
			comboBoxamade.setOnAction(event -> {
				updaetDeviceMadeStr = comboBoxamade.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + updaetDeviceMadeStr);
			});
			b.setOnAction(e -> {
				try {
				//	AssSecondUpdate.vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					StringBuilder update = new StringBuilder("UPDATE device ");
					StringBuilder set = new StringBuilder("SET ");
					StringBuilder where = new StringBuilder("WHERE ");

					String sname = tname.getText();
					String sprice = tprice.getText();
					String sweight = tweight.getText();
					
					
					boolean isDigit2 = true;
					for (int i = 0; i < sprice.length(); i++) {
						if(!(Character.isDigit(sprice.charAt(i)))) {
							isDigit2=false;
						}
						
					}
					boolean isDigit3 = true;
					for (int i = 0; i < sweight.length(); i++) {
						if(!(Character.isDigit(sweight.charAt(i)))) {
							isDigit3=false;
						}
						
					}
					if( isDigit2==false || isDigit3==false) {
						displayInfoAlert("Enter The Element AS Integer !");
					}
					else {

					if (!(sname.isEmpty()) && !(sprice.isEmpty()) && !(sweight.isEmpty()) && !(updaetDeviceNOStr.isEmpty())&& !(updaetDeviceMadeStr.isEmpty())) {

							
						set.append("no= '" + updaetDeviceNOStr + "' , name= '" + sname + "' , price= '" + sprice
								+ "' , weight= '" + sweight + "' , made= '" + updaetDeviceMadeStr + "' ");
						where.append("no= '" + updaetDeviceNOStr + "' ;");

						String query = update.toString() + set.toString() + where.toString();

						System.out.println("update device    " + query);

						PreparedStatement pstmt = con1.prepareStatement(query);

						pstmt.executeUpdate();
						 buildData(null);
						 displayInfoAlert("Updated succesfully");
					
						
					} else {
						displayInfoAlert("Enter all the element !");
						System.out.println("Enter all the elements ");
						}
					
					}
				} catch (Exception e2) {e2.printStackTrace();}});	} // Handle exceptions appropriately in your application
		

		//________________________________DELET DEVICE_______________________________________
		static VBox DeleteDeviceNo=new VBox();
		static String CarDeviceNo;			
		public static void DeleteDevice() throws ClassNotFoundException, SQLException {
			AssSecondUpdate.vvbox.getChildren().clear();
			DeleteDeviceNo.getChildren().clear();
			
			Label no = new Label("no");
			Button b=new Button("Delete");
			setFontb(b);
			setFont(no);
			
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select no From device");
			ArrayList<String> arr = new ArrayList<>();
			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}	
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxNo = new ComboBox<>(manu);
			DeleteDeviceNo.getChildren().addAll(no,comboBoxNo,b);
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(DeleteDeviceNo);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});
			comboBoxNo.setOnAction(event -> {
				CarDeviceNo = comboBoxNo.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + CarDeviceNo);
			});
			b.setOnAction(e -> {
				try {
					//AssSecondUpdate.vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");			
					StringBuilder update = new StringBuilder("DELETE FROM device ");
					StringBuilder where = new StringBuilder("WHERE ");
					where.append("no ='"+CarDeviceNo+"';");
					String query = update.toString() + where.toString();
					System.out.println("update device    " + query);
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
