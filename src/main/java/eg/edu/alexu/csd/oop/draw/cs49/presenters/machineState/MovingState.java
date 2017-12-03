package eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.DragHandlerFactory;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.ShapeDragHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovingState implements State {
    private Point startingPosition;
    private List<ShapeFX> shapes;
    private ShapeDragHandler shapeDragHandler;

    public MovingState() {
        startingPosition = new Point();
        shapes = new ArrayList<>();
    }

    @Override
    public void setStartingPosition(final Point point) {
        startingPosition = point;
    }

    @Override
    public Point getStartPosition() {
        return startingPosition;
    }

    @Override
    public void setShapes(final List<ShapeFX> shapeFXES) {
        shapes.clear();
        shapes.addAll(shapeFXES);

    }

    @Override
    public void setUpdatedShapes(final List<ShapeFX> shape) {

    }

    @Override
    public List<ShapeFX> getShapes() {
        return shapes;
    }

    @Override
    public state getState() {
        return state.moving;
    }

    @Override
    public ShapeDragHandler getShapeDragHandler() {
        shapeDragHandler = DragHandlerFactory.getEventHandler("Moving", shapes,
                startingPosition);
        return shapeDragHandler;
    }
}
