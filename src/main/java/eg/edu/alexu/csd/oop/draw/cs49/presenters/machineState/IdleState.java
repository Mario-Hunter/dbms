package eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.ShapeDragHandler;

import java.awt.*;
import java.util.List;

public class IdleState implements State {
    @Override
    public void setStartingPosition(final Point point) {

    }

    @Override
    public Point getStartPosition() {
        return null;
    }

    @Override
    public void setShapes(final List<ShapeFX> shapeFXES) {

    }

    @Override
    public void setUpdatedShapes(final List<ShapeFX> shape) {

    }

    @Override
    public List<ShapeFX> getShapes() {
        return null;
    }

    @Override
    public state getState() {
        return null;
    }

    @Override
    public ShapeDragHandler getShapeDragHandler() {
        return null;
    }
}
