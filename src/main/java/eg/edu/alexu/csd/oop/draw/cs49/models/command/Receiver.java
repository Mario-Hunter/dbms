package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Point;
import java.util.List;


public interface Receiver {
    void addShape(Shape shape);

    void removeShape(Shape shape);

    void updateShape(Shape oldShape, Shape newShape);

    void addShape(Shape shape, boolean notifiable);

    void removeShape(Shape shape, boolean notifiable);

    void updateShape(Shape oldShape, Shape newShape, boolean notifiable);

    void refresh(GraphicsContext canvas);

    void addSupportedShapes(List<Class<? extends Shape>> classes);

    void undo();

    void redo();

    ShapeFX getShapeBounding(Point point);

    List<Class<? extends Shape>> getSupportedShapes();

    void save(String path);

    void load(String path);
}
