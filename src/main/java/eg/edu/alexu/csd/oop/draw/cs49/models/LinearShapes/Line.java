package eg.edu.alexu.csd.oop.draw.cs49.models.LinearShapes;


import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Line extends AbstractShape {

    private static final String ANGLE = "angle";
    private static final String LENGTH = "length";
    private static final Double DEFAULT_ANGLE = 0.0;
    private static final Double DEFAULT_LENGTH = 50.0;
    private static final Double DEFAULT_STROKE = 5.0;
    private static final double TOLERANCE = 0.2;
    public static final String X2 = "x2";
    public static final String Y2 = "y2";

    public Line() {
        super();
    }

    public Line(final Line line) {
        super(line);
    }

    @Override
    protected void populateDefaults() {
        super.populateDefaults();
        addProperty(defaultProperties, ANGLE, DEFAULT_ANGLE);
        addProperty(defaultProperties, LENGTH, DEFAULT_LENGTH);
        addProperty(defaultProperties, STROKE_WIDTH, DEFAULT_STROKE);
        addProperty(defaultProperties, X2, (double) position.x);
        addProperty(defaultProperties, Y2, (double) position.y);
    }

    @Override
    public void draw(Graphics canvas) {

    }

    @Override
    public void draw(final GraphicsContext canvas) {
        if(properties.get(VISIBILITY) == INVISIBLE){
            return;
        }
        super.draw(canvas);

        double x1 = position.x;
        double y1 = position.y;

        double x2 = getProperties().get(X2);
        double y2 = getProperties().get(Y2);

        canvas.strokeLine(x1, y1, x2, y2);

    }

    @Override
    public boolean isBounding(final Point point) {
        double slope = (point.y - position.y) / (point.x - position.x);
        double distance = point.distance(position.x, position.y);
        double angle = getProperties().get(ANGLE);

        return Math.abs(slope - Math.tan(angle)) <= TOLERANCE &&
                distance <= getProperties().get(LENGTH);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Line(this);
    }

    @Override
    public ShapeFX shrink(double shrinkFactor) {
        Line shape = null;
        try {
            shape = (Line) this.clone();

            shape.position.x *= shrinkFactor;
            shape.position.y *= shrinkFactor;
            shape.properties.put(X2, shape.properties.get(X2) * shrinkFactor);
            shape.properties.put(Y2, shape.properties.get(Y2) * shrinkFactor);
            shape.properties.put(VISIBILITY,VISIBILE);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return shape;
    }
}
