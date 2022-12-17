module ivanbelyj.functiongraphs {
    requires javafx.controls;
    requires javafx.fxml;


    opens ivanbelyj.functiongraphs to javafx.fxml;
    exports ivanbelyj.functiongraphs;
    exports ivanbelyj.functiongraphs.drawing;
    opens ivanbelyj.functiongraphs.drawing to javafx.fxml;
}