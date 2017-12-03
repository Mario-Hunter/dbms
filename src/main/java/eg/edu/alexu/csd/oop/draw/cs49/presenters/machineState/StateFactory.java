package eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState;

import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.awt.*;

public class StateFactory {
    public static State getState(State.state state) {
        switch (state) {
            case drawing:
                return new DrawingState();
            case moving:
                return new MovingState();
            default:
                return new IdleState();
        }
    }
}
