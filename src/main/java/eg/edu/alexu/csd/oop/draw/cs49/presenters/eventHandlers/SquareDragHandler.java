package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.input.MouseEvent;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import static eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Rectangle.HEIGHT;
import static eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Rectangle.WIDTH;

public class SquareDragHandler extends ShapeDragHandler {
    public SquareDragHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
    }

    @Override
    public void handle(final MouseEvent event) {
        Map<String, Double> properties = shapes.get(0).getProperties();
        Double xPos = Double.valueOf(startPosition.x);
        Double xEndDrag = event.getX();
        if (Double.max(xPos, xEndDrag) == xPos) {
            properties.put(WIDTH, 1d);
            properties.put(HEIGHT, 1d);

        } else {
            properties.put(WIDTH, xEndDrag - xPos);
            properties.put(HEIGHT, xEndDrag - xPos);
        }


    }
}
