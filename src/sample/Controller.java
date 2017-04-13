package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    Pane circuit;


    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
        Rectangle x= new Rectangle(10,10,10,10);
        circuit.getChildren().add(x);
    }





}
