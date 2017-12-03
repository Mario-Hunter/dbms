package eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.ShapeDragHandler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

public interface State {
    void setStartingPosition(Point point);

    Point getStartPosition();

    void setShapes(List<ShapeFX> shapeFXES);

    void setUpdatedShapes(List<ShapeFX> shape);

    List<ShapeFX> getShapes();

    enum state {idle, drawing, moving}

    state getState();

    ShapeDragHandler getShapeDragHandler();
}
