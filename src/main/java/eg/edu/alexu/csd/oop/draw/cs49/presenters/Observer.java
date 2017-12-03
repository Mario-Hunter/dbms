package eg.edu.alexu.csd.oop.draw.cs49.presenters;

import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.util.List;

public interface Observer {
    void notify(List<ShapeFX> focusedShapes);
}
