import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ChangeListener;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class Ass_searchAndInsert extends Application {
	private TabPane tabPane = new TabPane();
	private static VBox vvbox = new VBox();
	// static Statement stmt ;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW TABLES");

			while (rs.next()) {
				String tableName = rs.getString(1);
				TableView<ObservableList<String>> table = buildData(tableName);
				Tab tab = new Tab(tableName, table);
				tab.setOnSelectionChanged(event -> showSearchFields(tableName, table));// show selection table
				tabPane.getTabs().add(tab);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error connecting to the database");
		}

		// vvbox.getChildren().remove(carHboxinsert);
		
           
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
					if (newValue != null) {

						String selectedTableName = newValue.getText();
						System.out.println("Selected Table Name: " + selectedTableName);
						// _________________ADDRESS_______________________________
						if (selectedTableName.equals("address")) {

							try {
								insertAddress();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						// ______________________CAR_PART__________________________
						if (selectedTableName.equals("car_part")) {

							try {
								insertCarPart();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						//_________________________CAR___________________________
						if (selectedTableName.equals("car")) {

							try {
								insertCar();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						//___________________CUSTOMER___________________________
						if (selectedTableName.equals("customer")) {

							try {
								insertCustomer();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						//___________________DEVICE___________________________
						if (selectedTableName.equals("device")) {

							try {
								insertDevice();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						//___________________MANUFACTURE___________________________
						if (selectedTableName.equals("manufacture")) {

							try {
								 insertManufacture();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						//___________________ORDERS___________________________
						if (selectedTableName.equals("orders")) {

							try {
								 insertOrders();
								System.out.println("kjkjkj");

							} catch (ClassNotFoundException | SQLException e) {
								System.out.println("saaaaaaaaaaaaaaaaa");
								e.printStackTrace();
							}

						}
						
					}
				});

		// insertAddress();

		VBox vbox = new VBox();
		vbox.getChildren().addAll(vvbox, tabPane);
		Scene scene = new Scene(vbox);

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

	private TableView<ObservableList<String>> buildData(String tableName) {
		TableView<ObservableList<String>> tableview = new TableView<>();
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn<ObservableList<String>, String> col = new TableColumn<>(
						rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(param -> new SimpleStringProperty(
						param.getValue().get(j) != null ? param.getValue().get(j) : ""));
				tableview.getColumns().add(col);
				System.out.println("Column [" + i + "] ");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}

		return tableview;
	}

	private void showSearchFields(String tableName, TableView<ObservableList<String>> table) {
		try {
			vvbox.getChildren().clear();

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

			ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<Label> labels = new ArrayList<>();
			List<TextField> textFields = new ArrayList<>();

			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				labels.add(new Label(columnName));
				textFields.add(new TextField());
			}

			HBox searchBox = new HBox();
			for (int i = 0; i < columnCount; i++) {
				searchBox.getChildren().addAll(labels.get(i), textFields.get(i));
			}

			Button searchButton = new Button("Search");
			searchButton.setOnAction(event -> search(tableName, textFields, table));
			searchBox.getChildren().add(searchButton);

			vvbox.getChildren().add(searchBox);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error showing search fields");
		}
	}

	private void search(String tableName, List<TextField> textFields, TableView<ObservableList<String>> table) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();

			StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
			ResultSetMetaData metaData = (ResultSetMetaData) stmt.executeQuery("SELECT * FROM " + tableName)
					.getMetaData();

			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnName(i);
				String value = textFields.get(i - 1).getText();
				if (!(value.isEmpty())) {
					query.append(columnName).append("='").append(value).append("'");

					query.append(" AND ");
				}

			}
			StringBuilder q = new StringBuilder("");
			for (int j = 0; j < query.length() - 5; j++) {
				q.append(query.charAt(j));

			}
			System.out.println("saaaaaaaq" + q);

			System.out.println("fffffffffffffffffffffffffffffffffff" + query.toString());

			ResultSet rs = stmt.executeQuery(q.toString());

			// add the rows
			ObservableList<ObservableList<String>> searchData = FXCollections.observableArrayList();
			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				searchData.add(row);
				System.out.println("Row [1] added " + row);
			}

			table.setItems(searchData);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error during search");
		}
	}

	private void displayMessage(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	static // ________________________________INSERT// ADDRESS________________________________________________

	HBox addressHboxinsert = new HBox();

	public static void insertAddress() throws ClassNotFoundException, SQLException {

		vvbox.getChildren().clear();

		Label id = new Label("id");
		TextField tid = new TextField();

		Label building = new Label("building");
		TextField tbuilding = new TextField();

		Label street = new Label("street");
		TextField tstreet = new TextField();

		Label city = new Label("city");
		TextField tcity = new TextField();

		Label country = new Label("country");
		TextField tcountry = new TextField();

		Button b = new Button("insert");
		addressHboxinsert.getChildren().addAll(id, tid, building, tbuilding, street, tstreet, city, tcity, country,
				tcountry, b);
		// HERE THE HBOX SHOWSSSSSSSS
		Platform.runLater(() -> {
			vvbox.getChildren().add(addressHboxinsert);
			vvbox.layout(); // Update the layout to reflect the changes
		});
		b.setOnAction(e -> {
			try {
				vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM address");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");
				String sid = tid.getText();
				String sbuilding = tbuilding.getText();
				String sstreet = tstreet.getText();
				String scity = tcity.getText();
				String scountry = tcountry.getText();

				if (!(sid.isEmpty()) && !(sbuilding.isEmpty()) && !(sstreet.isEmpty()) && !(scity.isEmpty())
						&& !(scountry.isEmpty())) {

					ArrayList<TextField> textFields = new ArrayList<>();
					textFields.add(tid);
					textFields.add(tbuilding);
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
				} else {
					displayInfoAlert("Enter all the element ");
					System.out.println("Enter all the element ");
				}

			} catch (Exception e2) {

			}

		});

	}

	static // ______________________________INSERT CAR__________________________________________________________________

	HBox carHboxinsert = new HBox();
	static String str;

	static void insertCar() throws SQLException, ClassNotFoundException {

		Label name = new Label("name");
		TextField tname = new TextField();

		Label model = new Label("model");
		TextField tmodel = new TextField();

		Label year = new Label("year");
		TextField tyear = new TextField();

		Label made = new Label("made");

		Button b = new Button("insert");

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
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

		// HERE THE HBOX SHOWSSSSSSSS
		Platform.runLater(() -> {
			vvbox.getChildren().add(carHboxinsert);
			vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBox.setOnAction(event -> {
			str = comboBox.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + str);

		});

		b.setOnAction(e -> {
			try {
				vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
				Statement stmt1 = con1.createStatement();

				ResultSet rs = stmt1.executeQuery("SELECT * FROM car");
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

				StringBuilder columns = new StringBuilder("(");
				StringBuilder values = new StringBuilder("(");

				String sname = tname.getText();
				String smodel = tmodel.getText();
				String syear = tyear.getText();

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
				} else {
					displayInfoAlert("Enter all the element ");
					System.out.println("Enter all the element ");
				}

			} catch (Exception e2) {

			}

		});

	}

	// ______________________________PART_CAR INSERT________________________________________________________

	static HBox CarPartHboxinsert = new HBox();
	static String CarPartStr_car;
	static String CarPartStr_part;

	static void insertCarPart() throws SQLException, ClassNotFoundException {

		Label car = new Label("car");
		Label part = new Label("part");

		Button b = new Button("insert");

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
		Statement stmt = con.createStatement();

		// car

		ResultSet r = stmt.executeQuery("select name From car");
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

		// HERE THE HBOX SHOWSSSSSSSS
		Platform.runLater(() -> {
			vvbox.getChildren().add(CarPartHboxinsert);
			vvbox.layout(); // Update the layout to reflect the changes
		});

		comboBox_car.setOnAction(event -> {
			CarPartStr_car = comboBox_car.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + str);

		});
		comboBox_part.setOnAction(event -> {
			CarPartStr_part = comboBox_part.getSelectionModel().getSelectedItem();
			System.out.println("Selected value: " + str);

		});

		b.setOnAction(e -> {
			try {
				vvbox.getChildren().clear();
				Class.forName("com.mysql.jdbc.Driver");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
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
				} else {
					displayInfoAlert("Enter all the element ");
					System.out.println("Enter all the element ");
				}

			} catch (Exception e2) {

			}

		});

	}

	static // _______________________CUSTOMER_____________________________________________

		HBox cusromerHboxinsert = new HBox();
		static String customerStr;

		static void insertCustomer() throws SQLException, ClassNotFoundException {

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
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();

			ResultSet r = stmt.executeQuery("select id From address");
			ArrayList<String> arr = new ArrayList();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);
			}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBox = new ComboBox<>(manu);

			cusromerHboxinsert.getChildren().addAll(id, tid, f_name, tf_name, l_name, tl_name, address, comboBox,job,tjob, b);

			// HERE THE HBOX SHOWSSSSSSSS
			Platform.runLater(() -> {
				vvbox.getChildren().add(cusromerHboxinsert);
				vvbox.layout(); // Update the layout to reflect the changes
			});

			comboBox.setOnAction(event -> {
				customerStr = comboBox.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + customerStr);

			});

			b.setOnAction(e -> {
				try {
					vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM customer");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sid = tid.getText();
					String sFname = tf_name.getText();
					String sLname = tl_name.getText();
					String sjob = tjob.getText();

					if (!(sid.isEmpty()) && !(sFname.isEmpty()) && !(sLname.isEmpty()) && !(sjob.isEmpty())&& !(customerStr.isEmpty())) {

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
					} else {
						displayInfoAlert("Enter all the element ");
						System.out.println("Enter all the element ");
					}

				} catch (Exception e2) {
					System.out.println("lllllllllllllllllllllllllllllll");

				}

			});

		}
		
		//___________________________________________device____________________________________________________
		static 
		HBox deviceHboxinsert = new HBox();
		static String deviceStr;

		static void insertDevice() throws SQLException, ClassNotFoundException {

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
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();

			ResultSet r = stmt.executeQuery("select name From manufacture");
			ArrayList<String> arr = new ArrayList();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);
			}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBox = new ComboBox<>(manu);

			deviceHboxinsert.getChildren().addAll(no, tno,name, tname,price,tprice, weight, tweight, made, comboBox, b);

			// HERE THE HBOX SHOWSSSSSSSS
			Platform.runLater(() -> {
				vvbox.getChildren().add(deviceHboxinsert);
				vvbox.layout(); // Update the layout to reflect the changes
			});

			comboBox.setOnAction(event -> {
				deviceStr = comboBox.getSelectionModel().getSelectedItem();
				System.out.println("Selected value: " + deviceStr);

			});

			b.setOnAction(e -> {
				try {
					vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM device");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sno = tno.getText();
					String sname = tname.getText();
					String sprice = tprice.getText();
					String sweight = tweight.getText();

					if (!(sno.isEmpty()) && !(sname.isEmpty()) && !(sprice.isEmpty()) && !(sweight.isEmpty())&& !(deviceStr.isEmpty())) {

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
							// TODO Auto-generated catch block
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
					} else {
						displayInfoAlert("Enter all the element ");
						System.out.println("Enter all the element ");
					}

				} catch (Exception e2) {
					System.out.println("lllllllllllllllllllllllllllllll");

				}

			});

		}
		//_____________________manufacture_______________________________
		static HBox manufactureHboxinsert = new HBox();

		static void insertManufacture() throws SQLException, ClassNotFoundException {

			Label name = new Label("name");
			TextField tname = new TextField();

			Label type = new Label("type");
			TextField ttype = new TextField();

			Label city = new Label("city");
			TextField tcity = new TextField();

			Label country = new Label("country");
			TextField tcountry = new TextField();

			Button b = new Button("insert");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");


			manufactureHboxinsert.getChildren().addAll(name, tname, type, ttype, city, tcity, country, tcountry, b);

			// HERE THE HBOX SHOWSSSSSSSS
			Platform.runLater(() -> {
				vvbox.getChildren().add(manufactureHboxinsert);
				vvbox.layout(); // Update the layout to reflect the changes
			});

			b.setOnAction(e -> {
				try {
					vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM manufacture");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sname = tname.getText();
					String stype = ttype.getText();
					String scity = tcity.getText();
					String scountry = tcountry.getText();

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
					} else {
						displayInfoAlert("Enter all the element ");
						System.out.println("Enter all the element ");
					}

				} catch (Exception e2) {

				}

			});

		}
		//__________________________________ORDER__________________________________________-
		static HBox orderHboxinsert = new HBox();
		static String CustomerStr_order;
		static String CarStr_order;

		static void insertOrders() throws SQLException, ClassNotFoundException {

			Label id = new Label("id");
			TextField tid=new TextField();
			
			Label date = new Label("date");
			TextField tdate=new TextField();
			
			Label customer = new Label("customer");
			Label car = new Label("car");

			Button b = new Button("insert");

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
			Statement stmt = con.createStatement();

			// car

			ResultSet r = stmt.executeQuery("select id From customer ");
			ArrayList<String> arr = new ArrayList();

			while (r.next()) {
				String x = r.getString(1);
				arr.add(x);
			}
			ObservableList<String> manu = FXCollections.observableArrayList(arr);
			ComboBox<String> comboBox_customer = new ComboBox<>(manu);
			
			// customer
			ResultSet r2 = stmt.executeQuery("select name From car ");
			ArrayList<String> arr2 = new ArrayList();

			while (r2.next()) {
				String x = r2.getString(1);
				arr2.add(x);
			}
			ObservableList<String> manu1 = FXCollections.observableArrayList(arr2);
			ComboBox<String> comboBox_car = new ComboBox<>(manu1);

			orderHboxinsert.getChildren().addAll(id,tid,date,tdate, customer, comboBox_customer, car, comboBox_car, b);

			// HERE THE HBOX SHOWSSSSSSSS
			Platform.runLater(() -> {
				vvbox.getChildren().add(orderHboxinsert);
				vvbox.layout(); // Update the layout to reflect the changes
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
					vvbox.getChildren().clear();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ass", "root", "");
					Statement stmt1 = con1.createStatement();

					ResultSet rs = stmt1.executeQuery("SELECT * FROM manufacture");
					ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

					StringBuilder columns = new StringBuilder("(");
					StringBuilder values = new StringBuilder("(");

					String sid = tid.getText();
					String sdate = tdate.getText();
					
					if (!(sid.isEmpty()) && !(sdate.isEmpty()) && !(CustomerStr_order.isEmpty()) && !(CarStr_order.isEmpty())) {

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
							// TODO Auto-generated catch block
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
					} else {
						displayInfoAlert("Enter all the element ");
						System.out.println("Enter all the element ");
					}

				} catch (Exception e2) {

				}

			});

		}

		
		


	private static void displayInfoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

}
