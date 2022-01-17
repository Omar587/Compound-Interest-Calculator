module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens InterestCalculator to javafx.fxml;
    exports InterestCalculator;
}