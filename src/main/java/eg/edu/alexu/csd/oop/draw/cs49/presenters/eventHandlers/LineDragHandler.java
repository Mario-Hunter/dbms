package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.input.MouseEvent;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import static eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes.Line.X2;
import static eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes.Line.Y2;

public class LineDragHandler extends ShapeDragHandler {
    public LineDragHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
    }

    @Override
    public void handle(final MouseEvent event) {
        Map<String,Double> prop = shapes.get(0).getProperties();
        prop.put(X2,event.getX());
        prop.put(Y2,event.getY());
    }
}
