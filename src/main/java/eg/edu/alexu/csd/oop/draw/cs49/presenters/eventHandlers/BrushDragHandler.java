package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes.Brush;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.List;

public class BrushDragHandler extends ShapeDragHandler {
    public BrushDragHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
    }

    @Override
    public void handle(final MouseEvent event) {
        Brush shape = (Brush) shapes.get(0);
        shape.setNewPoint(event.getX(),event.getY());
    }
}
