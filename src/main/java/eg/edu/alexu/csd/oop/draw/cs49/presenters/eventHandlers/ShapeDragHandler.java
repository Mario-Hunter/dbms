package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public abstract class ShapeDragHandler implements EventHandler<MouseEvent> {
    protected final Point startPosition;
    protected List<ShapeFX> shapes;

    public ShapeDragHandler(List<ShapeFX> shapes, Point startPosition) {
        this.shapes = new ArrayList<>();
        this.shapes.addAll(shapes);//=new ArrayList<>();
//        for(ShapeFX shape : shapes){
//            try {
//                this.shapes.add((ShapeFX) shape.clone());
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
        this.startPosition = startPosition;
    }



    public List<ShapeFX> getShapes() {
        return shapes;
    }
}
