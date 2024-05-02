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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class car  {
	
	
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
				sql = "select * from car";
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
	
	
	
	
	static // ________________________________SEARCH CAR________________________________________________
	VBox carHboxsearch = new VBox();


	public static void searchCar() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		carHboxsearch.getChildren().clear();
		
		
		Label name = new Label("name");
		setFont(name);
		
		TextField tname = new TextField();
		Label model = new Label("model");
		setFont(model);
		
		TextField tmodel = new TextField();
		Label year = new Label("year");
		setFont(year);
		
		TextField tyear = new TextField();
		Label made = new Label("made");
		setFont(made);
		TextField tmade = new TextField();
		
		TextField tcountry = new TextField();
		Button b = new Button("search");
		setFontb(b);	

		carHboxsearch.getChildren().addAll(name, tname, model, tmodel, year, tyear, made, tmade, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(carHboxsearch);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});


		b.setOnAction(e -> {
			try {
				//carHboxsearch.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt = con.createStatement();

				
				

				String q = new String("select * from car " );
				String q2=new String("");
				
				String sname = tname.getText();
				String smodel = tmodel.getText();
				String syear = tyear.getText();
				String smade = tmade.getText();
				
				boolean isDigit = true;
				for (int i = 0; i < syear.length(); i++) {
					if(!(Character.isDigit(syear.charAt(i)))) {
						isDigit=false;
					}
					
				}
				if(isDigit==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

				if ((sname.isEmpty()) && (smodel.isEmpty()) && (syear.isEmpty()) && (smade.isEmpty())) {

					q2="select * from car " ;
				}else {
					q+=" WHERE ";
					if(!sname.isEmpty()) {
						q+=" name = '"+sname +"' AND ";
					}
					if(!smodel.isEmpty()) {
						q+=" model = '"+smodel +"' AND ";
					}
					if(!syear.isEmpty()) {
						q+=" year = '"+syear +"' AND ";
					}
					if(!smade.isEmpty()) {
						q+=" city = '"+smade +"' AND ";
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
	
	static // ______________________________INSERT CAR__________________________________________________________________

	VBox carHboxinsert = new VBox();
	static String str;

	static void insertCar() throws SQLException, ClassNotFoundException {
		AssSecondUpdate.vvbox.getChildren().clear();
		carHboxinsert.getChildren().clear();

		Label name = new Label("name");
		setFont(name);
		
		TextField tname = new TextField();
		Label model = new Label("model");
		setFont(model);
		
		TextField tmodel = new TextField();
		Label year = new Label("year");
		setFont(year);
		
		TextField tyear = new TextField();
		Label made = new Label("made");
		setFont(made);
		
		Button b = new Button("insert");
		setFontb(b);

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

		carHboxinsert.getChildren().addAll(name, tname, model, tmodel, year, tyear, made, comboBox, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(carHboxinsert);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBox.setOnAction(event -> {
			str = comboBox.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + str);

		});

		b.setOnAction(e -> {
			try {

				//carHboxinsert.getChildren().clear();

				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				Statement stmt1 = con1.createStatement();

				ResultSet rs = stmt1.executeQuery("SELECT * FROM car");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");

				String sname = tname.getText();
				String smodel = tmodel.getText();
				String syear = tyear.getText();
				
				boolean isDigit = true;
				for (int i = 0; i < syear.length(); i++) {
					if(!(Character.isDigit(syear.charAt(i)))) {
						isDigit=false;
					}
					
				}
				if(isDigit==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

					ResultSet r5 = stmt.executeQuery("select name From car");
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

				if (!(sname.isEmpty()) && !(smodel.isEmpty()) && !(syear.isEmpty()) && !(str.isEmpty())) {

					ArrayList<String> textFields = new ArrayList<>();
					textFields.add(sname);
					textFields.add(smodel);
					textFields.add(syear);
					textFields.add(str);

					int count = 0;

					for (int i = 0; i < textFields.size(); i++) {
						String columnName = textFields.get(i);
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
							q2.append(")");}				
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					String query = "INSERT INTO car " + q2.toString() + " VALUES " + values.toString();

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
					//----------------------------
				buildData(null); 
				displayInfoAlert("Inserted succesfully");

					
					
				System.out.println("build dataaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				        
					
				} else {
					displayInfoAlert("Enter all the element !");
					System.out.println("Enter all the element ");
					
				}
				}
				}	
				} catch (Exception e2) {}	});}
	
	static // ________________________________UPDATE car________________________________________________
	VBox carHboxupdate = new VBox();
	static String updaetCarNameStr;
	static String updaetCarMadeStr;
	public static void updateCar() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		carHboxupdate.getChildren().clear();

		Label name = new Label("name");
		setFont(name);
		Label model = new Label("model");
		setFont(model);
		
		TextField tmodel = new TextField();
		Label year = new Label("year");
		setFont(year);
		
		TextField tyear = new TextField();
		Label made = new Label("made");
		setFont(made);
		
		Button b = new Button("update");
		setFontb(b);

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();

		ResultSet r = stmt.executeQuery("select name From car");
		ArrayList<String> arr = new ArrayList<>();

		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);}
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxName = new ComboBox<>(manu);

		Statement stmt1 = con.createStatement();

		ResultSet rMade = stmt1.executeQuery("select name From manufacture ");
		ArrayList<String> arrMade = new ArrayList<>();

		while (rMade.next()) {
			String x = rMade.getString(1);
			arrMade.add(x);}
		ObservableList<String> manu1 = FXCollections.observableArrayList(arrMade);
		ComboBox<String> comboBoxMade = new ComboBox<>(manu1);

		carHboxupdate.getChildren().addAll(name, comboBoxName, model, tmodel, year, tyear, made, comboBoxMade, b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(carHboxupdate);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBoxName.setOnAction(event -> {
			updaetCarNameStr = comboBoxName.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetCarNameStr);
		});
		comboBoxMade.setOnAction(event -> {
			updaetCarMadeStr = comboBoxMade.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + updaetCarMadeStr);
		});

		b.setOnAction(e -> {
			try {

				//carHboxupdate.getChildren().clear();

				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				StringBuilder update = new StringBuilder("UPDATE car ");
				StringBuilder set = new StringBuilder("SET ");
				StringBuilder where = new StringBuilder("WHERE ");

				String smodel = tmodel.getText();
				String syear = tyear.getText();
				
				
				boolean isDigit = true;
				for (int i = 0; i < syear.length(); i++) {
					if(!(Character.isDigit(syear.charAt(i)))) {
						isDigit=false;
					}
					
				}
				if(isDigit==false) {
					displayInfoAlert("Enter The Element AS Integer !");
				}
				else {

				if (!(smodel.isEmpty()) && !(syear.isEmpty()) && !(updaetCarNameStr.isEmpty())
						&& !(updaetCarMadeStr.isEmpty())) {

					set.append("name= '" + updaetCarNameStr + "' , model= '" + smodel + "' , year= '" + syear
							+ "' , made= '" + updaetCarMadeStr + "' ");
					where.append("name= '" + updaetCarNameStr + "' ;");

					String query = update.toString() + set.toString() + where.toString();

					System.out.println("update car    " + query);

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
			}});}
	//________________________________DELET CAR_______________________________________
	static VBox DeleteCarName=new VBox();
	static String CarNameIDStr;		
	public static void DeleteCar() throws ClassNotFoundException, SQLException {
		AssSecondUpdate.vvbox.getChildren().clear();
		DeleteCarName.getChildren().clear();
		
		Label name = new Label("name");
		setFont(name);
		Button b=new Button("Delete");
		setFontb(b);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
		Statement stmt = con.createStatement();
		ResultSet r = stmt.executeQuery("select name From car");
		ArrayList<String> arr = new ArrayList<>();
		while (r.next()) {
			String x = r.getString(1);
			arr.add(x);}	
		ObservableList<String> manu = FXCollections.observableArrayList(arr);
		ComboBox<String> comboBoxName = new ComboBox<>(manu);
		DeleteCarName.getChildren().addAll(name,comboBoxName,b);
		Platform.runLater(() -> {// HERE THE HBOX SHOWSSSSSSSS
			AssSecondUpdate.vvbox.getChildren().add(DeleteCarName);
			AssSecondUpdate.vvbox.layout(); // Update the layout to reflect the changes
		});
		comboBoxName.setOnAction(event -> {
			CarNameIDStr = comboBoxName.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + CarNameIDStr);
		});
		b.setOnAction(e -> {
			try {

				//DeleteCarName.getChildren().clear();
				
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass2", "root", "");
				
				StringBuilder update = new StringBuilder("DELETE FROM car ");
				StringBuilder where = new StringBuilder("WHERE ");
				where.append("name ='"+CarNameIDStr+"';");
				String query = update.toString() + where.toString();
				System.out.println("update car    " + query);
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
