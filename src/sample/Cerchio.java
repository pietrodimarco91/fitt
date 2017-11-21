package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jfxtras.scene.layout.CircularPane;

public class Cerchio implements EventHandler<MouseEvent> {

    Circle circle;
    int id;
    Main main;

    public Cerchio(int i, Color color, int id, Main main) {
        this.main=main;
        this.id=id;
        circle=new Circle(i,color);


    }

    public Node getCircle() {
        return circle;
    }

    @Override
    public void handle(MouseEvent event) {
        main.mousePressed();
        circle.setFill(new Color(0, 0, 0, 0.1686));
        circle.setOnMousePressed(null);
        main.startAlgo();

    }

    public void select() {
        circle.setOnMousePressed(this);
        circle.setFill(new Color(1, 0.1843, 0.251, 1));
    }

    public void changeDiam(int diam) {
        circle.setRadius(diam);
    }
}
