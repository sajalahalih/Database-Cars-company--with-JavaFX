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

public class Manufacture {
	
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
				sql = "select * from manufacture";
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
	
	
	
	static // ________________________________SEARCH MANUFACTURE________________________________________________
	VBox manufactureHboxsearch = new VBox();


	public static void searchManufacture() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		manufactureHboxsearch.getChildren().clear();
		
		
		Label name = new Label("name");
		setFont(name);
		TextField tname = new TextField();
		
		
		Label type = new Label("type");
		setFont(type);
		TextField ttype = new TextField();
		
		
		Label city = new Label("city");
		setFont(city);	
		TextField tcity = new TextField();
		
		
		Label country = new Label("country");
		setFont(country);
		TextField tcountry = new TextField();
		
		
		Button b = new Button("search");
		setFontb(b);	

		manufactureHboxsearch.getChildren().addAll(name, tname, type, ttype, city, tcity, country, tcountry, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(manufactureHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
				//AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from manufacture " );
				String q2=new String("");
				
				String sname = tname.getText();
				String stype = ttype.getText();
				String scity = tcity.getText();
				String scounrty = tcountry.getText();
				

				if ((stype.isEmpty()) && (stype.isEmpty()) && (scity.isEmpty()) && (scounrty.isEmpty())) {

					q2="select * from manufacture " ;
				}else {
					q+=" WHERE ";
					if(!stype.isEmpty()) {
						q+=" name = '"+sname +"' AND ";
					}
					if(!stype.isEmpty()) {
						q+=" type = '"+stype +"' AND ";
					}
					if(!scity.isEmpty()) {
						q+=" city = '"+scity +"' AND ";
					}
					if(!scounrty.isEmpty()) {
						q+=" country = '"+scounrty +"' AND ";
					}
					
					
								if(q.endsWith("' AND " )) {
						for (int i = 0; i < q.length()-5; i++) {
							q2+=q.charAt(i);
							
						}
						System.out.println(q2);
						
					}
								}
				
				
				 buildData(q2);
	                

	                con.close();

			} catch (Exception e2) {
				displayInfoAlert("Enter the element correct !");
				e2.printStackTrace(); // Handle exceptions appropriately in your application
			}
		});
	}
	
	// _____________________manufacture_______________________________
		static VBox manufactureHboxinsert = new VBox();

		static void insertManufacture() throws SQLException, ClassNotFoundException {
			
			AssSecondUpdate.vvbox.getChildren().clear();
			manufactureHboxinsert.getChildren().clear();

			Label name = new Label("name");
			TextField tname = new TextField();

			Label type = new Label("type");
			TextField ttype = new TextField();

			Label city = new Label("city");
			TextField tcity = new TextField();

			Label country = new Label("country");
			TextField tcountry = new TextField();

			Button b = new Button("insert");
			setFontb(b);
			setFont(name);
			setFont(type);
			setFont(city);
			setFont(country);
			

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");

			manufactureHboxinsert.getChildren().addAll(name, tname, type, ttype, city, tcity, country, tcountry, b);	
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(manufactureHboxinsert);
				AssSecondUpdate.	vvbox.layout(); // Update the layout to reflect the changes
			});

			b.setOnAction(e -> {
				try {
					//AssSecondUpdate.	vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM manufacture");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sname = tname.getText();
					String stype = ttype.getText();
					String scity = tcity.getText();
					String scountry = tcountry.getText();
					
					ResultSet r5 = stmt1.executeQuery("select name From manufacture");
					ArrayList<String> arr5 = new ArrayList<>();

					while (r5.next()) {
						String x = r5.getString(1);
						arr5.add(x);
					}
					boolean key=false;
					for (String ele : arr5) {
						if (ele.equals(sname)) {key=true;
				
						}
					}
					
					if(key==true) {
						displayInfoAlert("THE NAME ALREADY EXIST");
					}else {

					if (!(sname.isEmpty()) && !(stype.isEmpty()) && !(scity.isEmpty()) && !(scountry.isEmpty())) {
						ArrayList<String> textFields = new ArrayList<>();
						textFields.add(sname);
						textFields.add(stype);
						textFields.add(scity);
						textFields.add(scountry);
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
									values.append(",");	}}}
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
						String query = "INSERT INTO manufacture " + q2.toString() + " VALUES " + values.toString();

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
					} catch (Exception e2) {	}});}
		
		
		static // ________________________________UPDATE manufacture________________________________________________
		VBox manufactureHboxupdate = new VBox();
		static String updaetManufactureNameStr;
		public static void updateManufacture() throws ClassNotFoundException, SQLException {
			
			AssSecondUpdate.vvbox.getChildren().clear();
			manufactureHboxupdate.getChildren().clear();
			
			Label name = new Label("name");
			Label type = new Label("type");
			TextField ttype = new TextField();
			Label city = new Label("city");
			TextField tcity = new TextField();
			Label country = new Label("country");
			TextField tcountry = new TextField();
			Button b = new Button("update");
			setFontb(b);
			setFont(name);
			setFont(type);
			setFont(city);
			setFont(country);
			
			
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select name From manufacture");
			ArrayList<String> arr = new ArrayList<>();
			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxName = new ComboBox<>(manu);
			manufactureHboxupdate.getChildren().addAll(name, comboBoxName, type, ttype, city, tcity, country, tcountry, b);	
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(manufactureHboxupdate);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});
			comboBoxName.setOnAction(event -> {
				updaetManufactureNameStr = comboBoxName.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + updaetManufactureNameStr);
			});
			b.setOnAction(e -> {
				try {
				//	AssSecondUpdate.	vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					StringBuilder update = new StringBuilder("UPDATE manufacture ");
					StringBuilder set = new StringBuilder("SET ");
					StringBuilder where = new StringBuilder("WHERE ");
					String stype = ttype.getText();
					String scity = tcity.getText();
					String scountry = tcountry.getText();
					if (!(stype.isEmpty()) && !(scity.isEmpty()) && !(scountry.isEmpty())&& !(updaetManufactureNameStr.isEmpty())) {
						set.append("name= '" + updaetManufactureNameStr + "' , type= '" + stype + "' , city= '" + scity							+ "' , country= '" + scountry + "' ");
						where.append("name= '" + updaetManufactureNameStr + "' ;");
						String query = update.toString() + set.toString() + where.toString();
						System.out.println("update manufacture    " + query);
						PreparedStatement pstmt = con1.prepareStatement(query);
					pstmt.executeUpdate();
					
					 buildData(null);
					 displayInfoAlert("Updated succesfully");
					
					} else {
						displayInfoAlert("Enter all the element !");
						System.out.println("Enter all the elements ");
					}
				
				} catch (Exception e2) {e2.printStackTrace(); }});}	// Handle exceptions appropriately in your application	
		

		//________________________________DELET MANUFACTURE_______________________________________
		static VBox DeleteManuName=new VBox();
		static String CarManuName;

		public static void DeleteManufacture() throws ClassNotFoundException, SQLException {
			AssSecondUpdate.vvbox.getChildren().clear();
			DeleteManuName.getChildren().clear();
			
			Label no = new Label("name");
			Button b=new Button("Delete");
			
			setFontb(b);
			setFont(no);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select name From manufacture");
			ArrayList<String> arr = new ArrayList<>();
			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}	
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxName = new ComboBox<>(manu);
			DeleteManuName.getChildren().addAll(no,comboBoxName,b);
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(DeleteManuName);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});
			comboBoxName.setOnAction(event -> {
				CarManuName = comboBoxName.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + CarManuName);
			});
			b.setOnAction(e -> {
				try {
				//	AssSecondUpdate.	vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");			
					StringBuilder update = new StringBuilder("DELETE FROM manufacture ");
					StringBuilder where = new StringBuilder("WHERE ");
					where.append("name ='"+CarManuName+"';");
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
