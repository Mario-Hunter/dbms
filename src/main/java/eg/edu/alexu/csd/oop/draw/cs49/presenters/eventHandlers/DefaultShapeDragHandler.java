package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.List;

public class DefaultShapeDragHandler extends ShapeDragHandler {
    public DefaultShapeDragHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
    }

    @Override
    public void handle(final MouseEvent event) {

    }
}
