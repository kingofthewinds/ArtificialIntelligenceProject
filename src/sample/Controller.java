package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    Pane circuit;

    int hello = 10;

    @FXML
    private void mouseClicked()
    {
        System.out.println("hello");
        hello += 10;
        Rectangle x= new Rectangle(hello ,hello,10,10);
        circuit.getChildren().add(x);
    }





}
