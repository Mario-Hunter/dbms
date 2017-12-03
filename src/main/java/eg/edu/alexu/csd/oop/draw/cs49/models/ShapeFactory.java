package eg.edu.alexu.csd.oop.draw.cs49.models;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes.Brush;
import eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes.Line;

import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Rectangle;
import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Square;
import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Triangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ShapeFactory {

    private static Map<String, ShapeFX> cache = load();

    public static ShapeFX getShape(final String typeName) {
        if (cache == null) load();
        try {
            return (ShapeFX) cache.get(typeName).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void injectShape(final List<Class<? extends Shape>> classes) {
        for (Class<? extends Shape> cls : classes) {
            try {
                Shape shp = cls.newInstance();
                injectShape(shp, cls.getSimpleName());
            } catch (InstantiationException e) {
                e.printStackTrace();
                continue;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static enum shapes {
        Ellipse, Circle, Rectangle, Square, Line, Triangle
    }


    private ShapeFactory() {
    }

    public static Map<String, ShapeFX> load() {
        Map<String,ShapeFX> map = new HashMap<>();
        ShapeFX rectangle = new Rectangle();
//        ShapeFX circle = new Circle();
//        ShapeFX ellipse = new Ellipse();
        ShapeFX square = new Square();
        ShapeFX triangle = new Triangle();
        ShapeFX line = new Line();
        ShapeFX brush = new Brush();
        map.put("Brush",brush);
//        cache.put("Circle", circle);
        map.put("Line", line);
        map.put("Rectangle", rectangle);
        map.put("Square", square);
        map.put("Triangle", triangle);
//        cache.put("Ellipse, ellipse);
        return map;
    }

    public static void injectShape(Shape shape, String name) {
        cache.put(name, (ShapeFX) shape);

    }

    public static ShapeFX getShape(shapes shape) {
        return getShape(shape.name());
    }
}
