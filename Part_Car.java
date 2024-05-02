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

public class Part_Car {
	
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
				sql = "select * from car_part";
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
	
	
	static // ________________________________SEARCH CAR_PART________________________________________________
	VBox Car_PartHboxsearch = new VBox();


	public static void searchCar_Part() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		Car_PartHboxsearch.getChildren().clear();
		
		
		Label car = new Label("car");
		setFont(car);
		
		TextField tcar = new TextField();
		Label part = new Label("part");
		setFont(part);
		
		TextField tpart = new TextField();
		
		Button b = new Button("search");
		setFontb(b);	

		Car_PartHboxsearch.getChildren().addAll(car, tcar, part, tpart,  b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(Car_PartHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
			//	AssSecondUpdate.vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from car_part " );
				String q2=new String("");
				
				String scar = tcar.getText();
				String spart = tpart.getText();
				
				
				boolean isDigit = true;
				for (int i = 0; i < spart.length(); i++) {
					if(!(Character.isDigit(spart.charAt(i)))) {
						isDigit=false;
					}
					
				}
				if(isDigit==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {
				

				if ((scar.isEmpty()) && (spart.isEmpty()) ) {

					q2="select * from car_part " ;
				}else {
					q+=" WHERE ";
					if(!scar.isEmpty()) {
						q+=" car = '"+scar +"' AND ";
					}
					if(!spart.isEmpty()) {
						q+=" part = '"+spart +"' AND ";
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
	
	// ______________________________PART_CAR INSERT________________________________________________________

		static VBox CarPartHboxinsert = new VBox();
		static String CarPartStr_car;
		static String CarPartStr_part;
		static void insertCarPart() throws SQLException, ClassNotFoundException {
			AssSecondUpdate.vvbox.getChildren().clear();
			CarPartHboxinsert.getChildren().clear();
			
			Label car = new Label("car");
			setFont(car);
			Label part = new Label("part");
			setFont(part);
			Button b = new Button("insert");
			setFontb(b);

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select name From car");// car
			ArrayList<String> arr = new ArrayList();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);
			}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBox_car = new ComboBox<>(manu);
			// part
			ResultSet r2 = stmt.executeQuery("select no From device");
			ArrayList<String> arr2 = new ArrayList();

			while (r2.next()) {
				String x = r2.getString(1);
				arr2.add(x);
			}
			ObservableList<String> manu1 = FXCollections.observableArrayList(arr2);
			ComboBox<String> comboBox_part = new ComboBox<>(manu1);

			CarPartHboxinsert.getChildren().addAll(car, comboBox_car, part, comboBox_part, b);
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(CarPartHboxinsert);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});

			comboBox_car.setOnAction(event -> {
				CarPartStr_car = comboBox_car.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + CarPartStr_car);

			});
			comboBox_part.setOnAction(event -> {
				CarPartStr_part = comboBox_part.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + CarPartStr_part);

			});

			b.setOnAction(e -> {
				try {
				//	AssSecondUpdate.vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM car_part ");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					if (!(CarPartStr_car.isEmpty()) && !(CarPartStr_part.isEmpty())) {

						ArrayList<String> textFields = new ArrayList<>();
						textFields.add(CarPartStr_car);
						textFields.add(CarPartStr_part);

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
						String query = "INSERT INTO car_part " + q2.toString() + " VALUES " + values.toString();

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
					}	} catch (Exception e2) {	}});}
		
		static // ________________________________UPDATE CAR PART________________________________________________
		VBox carPartHboxupdate = new VBox();
		static String updaetCarPartCarStr;
		static String updaetCarPartPartStr;
		
		
		public static void updateCar_Part() throws ClassNotFoundException, SQLException {
			AssSecondUpdate.vvbox.getChildren().clear();
			carPartHboxupdate.getChildren().clear();
			
			
			
			Label name = new Label("car");
			Label part = new Label("part");
			Button b = new Button("update");
			setFontb(b);
			setFont(name);
			setFont(part);

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();

			ResultSet r = stmt.executeQuery("select car From car_part");
			ArrayList<String> arr = new ArrayList<>();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxName = new ComboBox<>(manu);

			Statement stmt1 = con.createStatement();

			ResultSet rpart = stmt1.executeQuery("select no From device ");
			ArrayList<String> arrpart = new ArrayList<>();

			while (rpart.next()) {
				String x = rpart.getString(1);
				arrpart.add(x);}
			
			
			ObservableList<String> manu1 = FXCollections.observableArrayList(arrpart);
			ComboBox<String> comboBoxPart = new ComboBox<>(manu1);

			carPartHboxupdate.getChildren().addAll(name, comboBoxName, part, comboBoxPart, b);	
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(carPartHboxupdate);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});

			comboBoxName.setOnAction(event -> {
				updaetCarPartCarStr = comboBoxName.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + updaetCarPartCarStr);
			});
			comboBoxPart.setOnAction(event -> {
				updaetCarPartPartStr = comboBoxPart.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + updaetCarPartPartStr);
			});

			b.setOnAction(e -> {
				try {
					//AssSecondUpdate.vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					StringBuilder update = new StringBuilder("UPDATE car_part ");
					StringBuilder set = new StringBuilder("SET ");
					StringBuilder where = new StringBuilder("WHERE ");

					if (!(updaetCarPartCarStr.isEmpty()) && !(updaetCarPartPartStr.isEmpty())) {

						set.append("car= '" + updaetCarPartCarStr + "' , part= '" + updaetCarPartPartStr + "' ");
						where.append("car= '" + updaetCarPartCarStr +  "' ;");

						String query = update.toString() + set.toString() + where.toString();

						System.out.println("update car_part    " + query);

						PreparedStatement pstmt = con1.prepareStatement(query);

						pstmt.executeUpdate();
						buildData(null); 
						 displayInfoAlert("Updated succesfully");
						
						
					} else {
						displayInfoAlert("Enter all the element !");
						System.out.println("Enter all the elements ");
					}
				} catch (Exception e2) {
					e2.printStackTrace(); // Handle exceptions appropriately in your application
				}});}
		
		
		
		
		//________________________________DELET CAR_PART_______________________________________
		static VBox DeleteCar_Part1=new VBox();
		static String Car_PartStr1;		
		public synchronized static void DeleteCar_Part() throws ClassNotFoundException, SQLException {
			AssSecondUpdate.vvbox.getChildren().clear();
			DeleteCar_Part1.getChildren().clear();
			
			Label name = new Label("car");
			setFont(name);
			Button b=new Button("Delete");
			setFontb(b);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
			Statement stmt = con.createStatement();
			ResultSet r = stmt.executeQuery("select car From car_part");
			ArrayList<String> arr = new ArrayList<>();
			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);}	
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBoxName = new ComboBox<>(manu);
			DeleteCar_Part1.getChildren().addAll(name,comboBoxName,b);
			Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
				AssSecondUpdate.vvbox.getChildren().add(DeleteCar_Part1);
				AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
			});
			comboBoxName.setOnAction(event -> {
				Car_PartStr1 = comboBoxName.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + Car_PartStr1);
			});
			b.setOnAction(e -> {
				try {

					//DeleteCarName.getChildren().clear();
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					
					StringBuilder update = new StringBuilder("DELETE FROM car_part ");
					StringBuilder where = new StringBuilder("WHERE ");
					where.append("car ='"+Car_PartStr1+"';");
					String query = update.toString() + where.toString();
					System.out.println("update car_part    " + query);
					PreparedStatement pstmt = con1.prepareStatement(query);
					pstmt.executeUpdate();
					buildData(null); 
					 displayInfoAlert("Deleted succesfully");
					
					

				} catch (Exception e2) {	e2.printStackTrace(); }});}
		
		
		//________________________________DELET CAR_PART 2_______________________________________
				static VBox DeleteCar_Part2=new VBox();
				static String Car_PartStr2;		
				public synchronized static void DeleteCar_Part2() throws ClassNotFoundException, SQLException {
					AssSecondUpdate.vvbox.getChildren().clear();
					DeleteCar_Part2.getChildren().clear();
					
					Label name = new Label("part");
					setFont(name);
					Button b=new Button("Delete");
					setFontb(b);
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
					Statement stmt = con.createStatement();
					ResultSet r = stmt.executeQuery("select part From car_part");
					ArrayList<String> arr = new ArrayList<>();
					while (r.next()) {
						String x = r.getString(1);
						arr.add(x);}	
					ObservableList<String> manu = FXCollections.observableArrayList(arr);
					ComboBox<String> comboBoxName = new ComboBox<>(manu);
					DeleteCar_Part2.getChildren().addAll(name,comboBoxName,b);
					Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
						AssSecondUpdate.vvbox.getChildren().add(DeleteCar_Part2);
						AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
					});
					comboBoxName.setOnAction(event -> {
						Car_PartStr2 = comboBoxName.getSelectionModel().getSelectedItem();
						System.out.println("Selected value: " + Car_PartStr2);
					});
					b.setOnAction(e -> {
						try {

							//DeleteCarName.getChildren().clear();
							
							Class.forName("com.mysql.jdbc.Driver");
							Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
							
							StringBuilder update = new StringBuilder("DELETE FROM car_part ");
							StringBuilder where = new StringBuilder("WHERE ");
							where.append("part ='"+Car_PartStr2+"';");
							String query = update.toString() + where.toString();
							System.out.println("update car_part    " + query);
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
