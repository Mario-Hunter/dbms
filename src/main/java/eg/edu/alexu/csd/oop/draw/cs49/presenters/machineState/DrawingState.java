package eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.DragHandlerFactory;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.ShapeDragHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawingState implements State {
    private Point startingPosition;
    private List<ShapeFX> shapes;
    private List<ShapeFX> updatedShapes;
    private ShapeDragHandler shapeDragHandler;

    public DrawingState() {
        this.startingPosition = new Point();
        this.shapes = new ArrayList<>();
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
        this.shapes.clear();
        this.shapes.addAll(shapeFXES);
    }

    @Override
    public void setUpdatedShapes(final List<ShapeFX> shapes) {
        this.updatedShapes = shapes;
    }

    @Override
    public List<ShapeFX> getShapes() {
        return shapes;
    }

    @Override
    public state getState() {
        return state.drawing;
    }

    @Override
    public ShapeDragHandler getShapeDragHandler() {
        shapeDragHandler = DragHandlerFactory.getEventHandler(shapes.get(0).getClass().getSimpleName(),shapes,
                startingPosition);
        return shapeDragHandler;
    }
}
