package eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers;

import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.awt.Point;
import java.util.List;


public class DragHandlerFactory {
    public static ShapeDragHandler getEventHandler(String shapeType, final List<ShapeFX> shapes,
                                                   final Point point) {
        switch (shapeType) {
            case "Rectangle":
                return new RectangleDragHandler(shapes, point);
            case "Square":
                return new SquareDragHandler(shapes, point);
            case "Line":
                return new LineDragHandler(shapes, point);
            case "Triangle":
                return new TriangleDragHandler(shapes, point);
            case "Moving":
                return new MoveHandler(shapes, point);
            case "Brush":
                return new BrushDragHandler(shapes,point);
            default:
                return new DefaultShapeDragHandler(shapes,point);
        }
    }
}
