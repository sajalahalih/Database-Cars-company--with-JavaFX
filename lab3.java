/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author developer
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 *
 * @author Narayan
 */
public class lab3 extends Application {

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data  = FXCollections.observableArrayList();;

    private TableView tableview = new TableView();

    //MAIN EXECUTOR
    public static void main(String[] args) {
        launch(args);
    }

    //CONNECTION DATABASE
    public void buildData(String sid,String sname,String scity) {
        Connection c;
        data = FXCollections.observableArrayList();
        try {

			Class.forName("com.mysql.jdbc.Driver");


			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saja", "root", "");



			Statement stmt = con.createStatement();
			//String condition ="name='saja' ";
			

			String q = new String("select * from saja " );
			String q2=new String("");
			if(sid.isEmpty() && sname.isEmpty() && scity.isEmpty()) {
				q2="select * from saja " ;
			}else {
				q+=" WHERE ";
				if(!sid.isEmpty()) {
					q+=" id = '"+sid +"' AND ";
				}
				if(!sname.isEmpty()) {
					q+=" name = '"+sname +"' AND ";
				}
				if(!scity.isEmpty()) {
					q+=" city = '"+scity +"' AND ";
				}
							if(q.endsWith("' AND " )) {
					for (int i = 0; i < q.length()-5; i++) {
						q2+=q.charAt(i);
						
					}
					System.out.println(q2);
					
				}
							}


			ResultSet rs = stmt.executeQuery(q2);
			tableview.getColumns().clear();
			tableview.getItems().clear();
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                System.out.println(col);
                System.out.println("Column [" + i + "] ");
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //TableView
        tableview  = new TableView();
       // buildData();
        VBox vbox=new VBox();
        HBox hbox1=new HBox();
        HBox hbox2=new HBox();
        HBox hbox3=new HBox();
        HBox hbox4=new HBox();
        
        Label id = new Label("id");
        TextField tid=new TextField();
        hbox1.getChildren().addAll(id,tid);
        
        Label name = new Label("name");
        TextField tname=new TextField();
        hbox2.getChildren().addAll(name,tname);
        
        Label city = new Label("city");
        TextField tcity=new TextField();
        hbox3.getChildren().addAll(city,tcity);
        
        Button b=new Button("search");
        
        vbox.getChildren().addAll(hbox1,hbox2,hbox3,b,tableview);
         
        b.setOnAction(e -> {
        	
        	String sid = tid.getText();
        	String sname=tname.getText();
        	String scity=tcity.getText();
        	buildData(sid, sname, scity);
        	
        });
        
        

        //Main Scene
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
   
}
