package eg.edu.alexu.csd.oop.draw.cs49.models.polygons;


import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.canvas.GraphicsContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.Map;
import java.util.Properties;

@XmlRootElement
public class Rectangle extends Polygon {
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";


    public Rectangle(int x, int y, int width, int height) {
        super();
        xs = new int[]{x};
        ys = new int[]{y};
        addProperty(properties, Rectangle.WIDTH, Double.valueOf(width));
        addProperty(properties, Rectangle.HEIGHT, Double.valueOf(height));
    }


    public Rectangle() {
        this(ORIGIN, ORIGIN, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Rectangle(final int width, final int height) {
        this(ORIGIN, ORIGIN, width, height);
    }

    public Rectangle(Rectangle rectangle) {
        super(rectangle);
        this.xs = rectangle.xs;
        this.ys = rectangle.ys;
    }

    @Override
    protected void populateDefaults() {
        super.populateDefaults();
        addProperty(defaultProperties, Polygon.EDGES_NUMBER, 4d);
        addProperty(defaultProperties, Rectangle.WIDTH, (double) DEFAULT_WIDTH);
        addProperty(defaultProperties, Rectangle.HEIGHT, (double) DEFAULT_HEIGHT);
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.drawRect(getProperties().get(X_CLICKED).intValue(),
                getProperties().get(Y_CLICKED).intValue(),
                getProperties().get(WIDTH).intValue(),
                getProperties().get(HEIGHT).intValue());
    }

    public void draw(final GraphicsContext canvas) {
        if(properties.get(VISIBILITY) == INVISIBLE){
            return;
        }
        super.draw(canvas);
        canvas.fillRect(position.x,
                position.y,
                getProperties().get(WIDTH).intValue(),
                getProperties().get(HEIGHT).intValue());
        canvas.strokeRect(position.x,
                position.y,
                getProperties().get(WIDTH).intValue(),
                getProperties().get(HEIGHT).intValue());
    }

    @Override
    public boolean isBounding(final Point point) {
        return point.x >= position.x
                && point.x <= position.x + getProperties().get(WIDTH)
                && point.y >= position.y
                && point.y <= position.y + getProperties().get(HEIGHT);
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Rectangle(this);
    }



    @Override
    public ShapeFX shrink(double shrinkFactor) {
        ShapeFX shrinkedShape = null;
        try {
            shrinkedShape = (ShapeFX) this.clone();
            Point pos = shrinkedShape.getPosition();
            shrinkedShape.setPosition(new Point((int) (pos.x * shrinkFactor), (int) (pos.y * shrinkFactor)));
            Map<String, Double> prop = shrinkedShape.getProperties();
            Double width = prop.get(WIDTH);
            Double height = prop.get(HEIGHT);
            Double stroke = prop.get(STROKE_WIDTH);
            prop.put(WIDTH, width * shrinkFactor);
            prop.put(HEIGHT, height * shrinkFactor);
            prop.put(STROKE_WIDTH, stroke * shrinkFactor);
            prop.put(VISIBILITY,VISIBILE);
            shrinkedShape.setProperties(prop);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return shrinkedShape;
    }
}
