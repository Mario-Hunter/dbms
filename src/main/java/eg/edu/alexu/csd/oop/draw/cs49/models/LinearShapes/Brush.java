package eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;

public class Brush extends AbstractShape {
    private static final double EQUALITY_TOLERANCE = 5;
    private ArrayList<Point> pathPoints;

    public Brush() {
        super();
        pathPoints = new ArrayList<>();
    }

    public Brush(final Brush shape) {
        super(shape);
        pathPoints = new ArrayList<>();
        shape.pathPoints.forEach(n -> pathPoints.add(new Point(n.x, n.y)));
    }

    @Override
    public void draw(final Graphics canvas) {

    }

    @Override
    public void draw(final GraphicsContext canvas) {
        if (properties.get(VISIBILITY) == INVISIBLE) {
            return;
        }
        super.draw(canvas);
        canvas.beginPath();
        // canvas.moveTo(position.x, position.y);
        canvas.stroke();
        pathPoints.forEach(n -> {
            canvas.lineTo(n.x + position.x, n.y + position.y);
            canvas.stroke();

        });
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Brush(this);
    }

    @Override
    public boolean isBounding(final Point point) {
        Point clickedNormalized = new Point(point.x - position.x, point.y - position.y);
        return pathPoints.stream().anyMatch(testPoint -> testPoint.distance(clickedNormalized) <= EQUALITY_TOLERANCE);
    }

    @Override
    public ShapeFX shrink(final double shrinkFactor) {
        Brush shape = null;
        try {
            shape = (Brush) this.clone();
            shape.pathPoints.forEach(n -> {
                n.x *= shrinkFactor;
                n.y *= shrinkFactor;
            });
            shape.position.x *= shrinkFactor;
            shape.position.y *= shrinkFactor;
            shape.properties.put(STROKE_WIDTH, shape.properties.get(STROKE_WIDTH) * shrinkFactor);
            shape.properties.put(VISIBILITY,VISIBILE);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return shape;
    }

    public void setNewPoint(final double x, final double y) {
        pathPoints.add(new Point((int) x - position.x, (int) y - position.y)); //Normalizing the points coordinates
    }
}
