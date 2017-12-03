package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Triangle;
import javafx.scene.input.MouseEvent;

import java.awt.Point;
import java.util.List;

import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.ORIGIN;

public class TriangleDragHandler extends ShapeDragHandler {

    public TriangleDragHandler(final List<ShapeFX> shapes, final Point startPosition) {
        super(shapes, startPosition);
    }

    @Override
    public void handle(final MouseEvent event) {
        int[] xs = ((Triangle) shapes.get(0)).getXs();
        int[] ys = ((Triangle) shapes.get(0)).getYs();

        Double xPos = Double.valueOf(startPosition.x);
        Double xEndDrag = event.getX();

        Double yPos = Double.valueOf(startPosition.y);
        Double yEndDrag = event.getY();

        Double width, height;

        if (xEndDrag - xPos >= 1) {
            width = xEndDrag - xPos;
        } else {
            width = 1d;
        }

        if (yEndDrag - yPos >= 1) {
            height = yEndDrag - yPos;
        } else {
            height = 1d;
        }


        xs[1] = (int) (ORIGIN - width /2);
        xs[2] = (int) (ORIGIN + width /2);

        ys[1] = (int) (ORIGIN +height);
        ys[2] = (int) (ORIGIN +height);
    }
}
