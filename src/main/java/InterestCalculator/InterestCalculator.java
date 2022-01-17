package InterestCalculator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class InterestCalculator extends Application implements EventHandler<ActionEvent> {


    // all ui elements are placed here
   private  Button calculate = new Button("Calculate");
   private  TextField principleTf = new TextField();
   private  TextField annualInterestTf = new TextField();
   private TextField yearsTf = new TextField();
   private  ComboBox comboBox = new ComboBox();
   private TableView<User> table;
   private BarChart<String, Number> barChart;


    @Override
    public void start(Stage stage) throws IOException {

        Group group = new Group();
        createTable();
        createBarChart();
        group.getChildren().addAll(stagesText(),userInputs(),table, barChart);

        Scene scene = new Scene(group, 1000, 700);
        stage.setTitle("Interest Calculator");

        stage.setScene(scene);
        stage.show();


}

    public static void main(String[] args) {


        launch();

    }


    /**
     * This is a method that places all the text labels onto the Ui
     *
     * @return a group of text labels
     */
    private  Group stagesText(){

        //set the text positions and names

        Text text = new Text();
        text.setText("Compound Interest Calculator");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));


        Text text1 = new Text();
        text1.setText("Principle($): ");
        text1.setFont(new Font("Verdana",15));

        Text text2 = new Text();
        text2.setText("Annual Interest (%): ");
        text2.setFont(new Font("Verdana",15));

        Text text3 = new Text();
        text3.setText("Compounding Frequency: ");
        text3.setFont(new Font("Verdana",15));

        Text text4 = new Text();
        text4.setText("Number of Years:  ");
        text4.setFont(new Font("Verdana",15));

        VBox vb1 = new VBox(30);
        HBox hb = new HBox(50);

        vb1.getChildren().addAll(text,text1, text2, text3,text4);
        hb.getChildren().addAll(vb1);

        vb1.setLayoutY(0);
        vb1.setLayoutX(5);

        //Creating a Group object
        Group group = new Group(vb1);


        return group;

    }


    /**
     * Helper function that palces the tectfiels and combobox to
     *the UI. These are the user inputs which will generate the calculations needed
     *
     * @return a Group aboject of text fields a long with a comboBox.
     */

    private Group userInputs(){

        VBox vb1 = new VBox(25);
        HBox hb = new HBox(50);

        comboBox.getItems().add("Monthly");
        comboBox.getItems().add("Semi-Annually        ");
        comboBox.getItems().add("Quarterly");
        comboBox.getItems().add("Annually");

        //all the labels, comboBox, textArea, and text fields are added to the UI

        calculate.setOnAction(this);
        //comboBox.setScaleX(20);
        vb1.getChildren().addAll(principleTf, annualInterestTf,  comboBox, yearsTf, calculate);
        hb.getChildren().addAll(vb1);


        vb1.setLayoutY(50);
        vb1.setLayoutX(250);

        Group group = new Group(vb1);

        return group;


    }

    /**
     * Responsible for setting up the barchart Chart on the Ui
     *
     */

   private void createBarChart(){

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Closing Balance");
        barChart = new BarChart(xAxis, yAxis);
        barChart.setTitle("Balance Growth Over Year");
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);
        barChart.setLayoutY(300);
        barChart.setLayoutX(50);
        barChart.setPrefWidth(900);


    }

    /**
     * This is method draws on the chart
     *
     * @param data  takes an observable list of User's.
     *
     *
     */

    private void drawChart(ObservableList<User> data) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (User user : data) {
            String interest = user.getClosingBalance().replace(",","").replace("$","");
            series.getData().add(new XYChart.Data(String.valueOf(user.getYears()), Double.parseDouble(interest)));
        }

        barChart.getData().add(series);
    }


    /**
     * places a tableview on the Ui
     *
     */

    private void createTable() {

        //years
        TableColumn<User,Integer>  yearColumn= new TableColumn("Year");
        yearColumn.setMinWidth(50);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("years"));

        //opening balance
        TableColumn<User,String>  openingBalance= new TableColumn("Opening Balance");
        openingBalance.setMinWidth(100);
        openingBalance.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));

        //interest
        TableColumn<User,String>  interestGainedOnYear = new TableColumn("Interest");
        interestGainedOnYear.setMinWidth(100);


        interestGainedOnYear.setCellValueFactory(new PropertyValueFactory<>("interest"));

        //closing balance
        TableColumn<User,String>  closingBalance = new TableColumn("Closing Balance");
        closingBalance.setMinWidth(120);
        closingBalance.setCellValueFactory(new PropertyValueFactory<>("closingBalance"));

       // table = new TableView<>();
        table = new TableView<>();
        table.getColumns().addAll(yearColumn, openingBalance, interestGainedOnYear, closingBalance);
        table.setPrefSize(440, 250);
        table.setLayoutX(440);
        table.setLayoutY(45);





    }


    /**
     * This is a helper method that creates an observable list which will later be fed into
     *the methods that will create the bar chat and table view.
     *
     * @param user
     * @return observable list of users
     */

    private ObservableList<User> getUserInfo(User user){
        ObservableList<User> summary = FXCollections.observableArrayList();
        for (int i = 1; i <= user.getYears(); i++){


            User userInfoPerYear = new User(user.getPrinciple(), user.getAnnualInterest(),user.getCompoundingFrequency(), i);

            summary.add(userInfoPerYear);
        }

        return summary;


    }

    /**
     * helper method used to show the user
     */

    private void alertBox(String errorMessage){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error window");
        Text text = new Text();
        text.setText(errorMessage);
        text.setFont(new Font("Verdana",12));

        VBox layout = new VBox(10);

        layout.getChildren().add(text);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,400,100);


        window.setScene(scene);
        window.showAndWait();


    }

    /**
     * This method is responsible for handling all the action events that takes place
     * from user inputs.
     *
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == calculate){

            //checks if combo box and textfields are empty
            if (principleTf.getText().equals("") || annualInterestTf.getText().equals("") || yearsTf.getText().equals("")
                    || comboBox.getValue() == null ){
                barChart.getData().clear();
                table.getItems().clear();
                alertBox("Please enter values into the text fields and combo box." +
                        "\nThey can not be empty!");
            }



            else {

                try {
                    //places text fields into the user class
                    User u = new User(Double.parseDouble(principleTf.getText()), Integer.parseInt(annualInterestTf.getText()),
                            comboBox.getValue().toString().replaceAll("\\s+",""), Integer.parseInt(yearsTf.getText()));

                    table.setItems(getUserInfo(u));
                    drawChart(getUserInfo(u));
                }
                catch (Exception ex){
                    alertBox("Invalid input please enter numeric values only!");

                }



            }





        }

    }
}