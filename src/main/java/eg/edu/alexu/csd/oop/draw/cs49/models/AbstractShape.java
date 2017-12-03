package eg.edu.alexu.csd.oop.draw.cs49.models;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.ShrinkableShape;
import javafx.scene.canvas.GraphicsContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractShape implements ShapeFX, Serializable, ShrinkableShape {
    public static final String X_CLICKED = "x_clicked";
    public static final String VISIBILITY = "visibility";
    public static final String Y_CLICKED = "y_clicked";
    public static final String X_DRAG_START = "x_drag_start";
    public static final String Y_DRAG_START = "y_drag_start";
    public static final String X_DRAG_END = "X_drag_end";
    public static final String Y_DRAG_END = "y_drag_end";
    public static final String STROKE_WIDTH = "stroke_width";
    public static final int ORIGIN = 0;
    protected static final int DEFAULT_WIDTH = 0;
    protected static final int DEFAULT_HEIGHT = 0;
    private static final Double DEFAULT_STROKE_WIDTH = 0.0;
    public static final Double VISIBILE = 1.0;
    public static final Double INVISIBLE = 0.0;

    protected Map<String, Double> properties;
    protected final Map<String, Double> defaultProperties = new HashMap<>();
    protected Point position;
    protected Color color;
    protected Color fillColor;

    public AbstractShape() {
        this.properties = new HashMap<>();
        this.position = new Point(0, 0);
        this.fillColor = new Color(0, 255, 255);
        this.color = new Color(0, 0, 0);
        populateDefaults();
        supplyMissingProperties();
    }


    public AbstractShape(AbstractShape shape) {
        this.properties = cloneProperties(shape.properties);
        setPosition(new Point(shape.position.x, shape.position.y));
        this.color = new Color(shape.color.getRed(),
                shape.color.getGreen(),
                shape.color.getBlue(),
                shape.color.getAlpha());
        this.fillColor = new Color(shape.fillColor.getRed(),
                shape.fillColor.getGreen(),
                shape.fillColor.getBlue(),
                shape.fillColor.getAlpha());
        populateDefaults();
        supplyMissingProperties();
    }

    private Map<String, Double> cloneProperties(Map<String, Double> properties) {
        Map<String, Double> prop = new HashMap<>();
        for (Map.Entry<String, Double> entry : properties.entrySet()) {
            prop.put(entry.getKey(), entry.getValue());
        }
        return prop;
    }

    protected void populateDefaults() {
        defaultProperties.put(STROKE_WIDTH, DEFAULT_STROKE_WIDTH);
        defaultProperties.put(X_CLICKED, (double) ORIGIN);
        defaultProperties.put(Y_CLICKED, (double) ORIGIN);
        defaultProperties.put(VISIBILITY, VISIBILE);
    }


    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    @XmlElement
    @XmlJavaTypeAdapter(PointXMLAdapter.class)
    public Point getPosition() {
        return position;
    }

    public void addProperty(Map<String, Double> propertiesMap, String key, Double value) {
        propertiesMap.put(key, value);
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        supplyMissingProperties(properties, true);
        supplyMissingProperties();
    }


    @Override
    @XmlElement
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    @XmlElement
    @XmlJavaTypeAdapter(ColorXMLAdapter.class)
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    @XmlElement
    @XmlJavaTypeAdapter(ColorXMLAdapter.class)
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public abstract void draw(Graphics canvas);

    @Override
    public void draw(GraphicsContext canvas) {

        canvas.setFill(ColorAdapter.getFxColor(fillColor));
        canvas.setStroke(ColorAdapter.getFxColor(color));
        if (properties != null && properties.get(STROKE_WIDTH) != null) {
            canvas.setLineWidth(properties.get(STROKE_WIDTH));

        }

    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;

    private void supplyMissingProperties() {
        supplyMissingProperties(defaultProperties, false);
    }

    protected void supplyMissingProperties(Map<String, Double> newProperities,
                                           boolean
                                                   forceUpdate) {
        for (Map.Entry<String, Double> entry : newProperities.entrySet()) {
            if (forceUpdate || !properties.containsKey(entry.getKey())) {
                properties.put(entry.getKey(), entry.getValue());
            }
        }
    }


    private static class ColorXMLAdapter extends XmlAdapter<ColorXMLAdapter
            .ColorValueType, Color> {

        @Override
        public Color unmarshal(ColorValueType v) throws Exception {
            return new Color(v.red, v.green, v.blue);
        }

        @Override
        public ColorValueType marshal(Color v) throws Exception {
            return new ColorValueType(v.getRed(), v.getGreen(), v.getBlue());
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class ColorValueType {
            private int red;
            private int green;
            private int blue;

            public ColorValueType() {
            }

            public ColorValueType(int red, int green, int blue) {
                this.red = red;
                this.green = green;
                this.blue = blue;
            }
        }
    }

    private static class PointXMLAdapter extends XmlAdapter<PointXMLAdapter
            .PointValueType, Point> {


        @Override
        public Point unmarshal(final PointValueType v)
                throws Exception {
            return new Point(v.x, v.y);
        }

        @Override
        public PointValueType marshal(final Point v)
                throws Exception {
            return new PointValueType(v.x, v.y);
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class PointValueType {
            private int x;
            private int y;


            public PointValueType() {
            }

            public PointValueType(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }


}
