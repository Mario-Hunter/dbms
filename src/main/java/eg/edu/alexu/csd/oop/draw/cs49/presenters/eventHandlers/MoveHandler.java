package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.input.MouseEvent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MoveHandler extends ShapeDragHandler {
    private Point updatedPosition;

    public MoveHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
        updatedPosition = (Point) startPosition.clone();
    }

    @Override
    public void handle(final MouseEvent event) {
        int xOffset = (int) event.getX() - updatedPosition.x;
        int yOffset = (int) event.getY() - updatedPosition.y;
        updatedPosition = new Point((int) event.getX(), (int) event.getY());
        List<ShapeFX> newShapes = new ArrayList<>();
        for (ShapeFX shape : shapes) {
            try {
                ShapeFX newShape = (ShapeFX) shape.clone();
                Point shapePosition = shape.getPosition();
                Point pos = new Point(shapePosition.x + xOffset, shapePosition.y + yOffset);
                newShape.setPosition(pos);
                newShapes.add(newShape);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        shapes = newShapes;
    }
}
