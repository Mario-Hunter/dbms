package eg.edu.alexu.csd.oop.draw.cs49.models.polygons;


import eg.edu.alexu.csd.oop.draw.ShapeFX;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

public class Triangle extends Polygon {

    private static final double BOUNDING_TOLERANCE = 50.0;

    public Triangle(int[] x, int[] y) {
        super();
        if (x.length != y.length) throw new AssertionError();
        xs = x;
        ys = y;
    }

    public Triangle() {
        this(new int[]{ORIGIN, ORIGIN - DEFAULT_WIDTH / 2, ORIGIN + DEFAULT_WIDTH / 2},
                new int[]{ORIGIN, ORIGIN + DEFAULT_HEIGHT, ORIGIN + DEFAULT_HEIGHT});
    }

    public Triangle(Triangle triangle) {
        super(triangle);
        this.xs = triangle.xs.clone();
        this.ys = triangle.ys.clone();
    }

    @Override
    protected void populateDefaults() {
        super.populateDefaults();
        addProperty(defaultProperties, Polygon.EDGES_NUMBER, 3d);
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.drawPolygon(Arrays.stream(xs).mapToDouble(n -> n + position.x)
                        .mapToInt(n -> (int) n).toArray(),
                Arrays.stream(xs).mapToDouble(n -> n + position.y)
                        .mapToInt(n -> (int) n).toArray(),
                properties.get(Polygon.EDGES_NUMBER).intValue());
    }

    @Override
    public void draw(final GraphicsContext canvas) {
        if(properties.get(VISIBILITY) == INVISIBLE){
            return;
        }
        super.draw(canvas);
        canvas.fillPolygon(Arrays.stream(xs).mapToDouble(n -> n + position.x).toArray(),
                Arrays.stream(ys).mapToDouble(n -> n + position.y).toArray(),
                properties.get(Polygon.EDGES_NUMBER).intValue());
        canvas.strokePolygon(Arrays.stream(xs).mapToDouble(n -> n + position.x).toArray(),
                Arrays.stream(ys).mapToDouble(n -> n + position.y).toArray(),
                properties.get(Polygon.EDGES_NUMBER).intValue());
    }

    public int[] getXs(){
        return xs;
    }

    public int[] getYs(){
        return ys;
    }

    public void setXs(int[] xs){
        this.xs = xs;
    }

    public void setYs(int[] ys){
        this.ys = ys;
    }

    @Override
    public boolean isBounding(final Point point) {
//        double[] x = Arrays.stream(xs).mapToDouble(n -> n + position.x)
//                .toArray();
//
//        double[] y = Arrays.stream(ys).mapToDouble(n -> n + position.y).toArray();
//
//
//        double ABC = Math.abs(x[0] * (y[1] - y[2])
//                + x[1] * (y[2] - y[0]) + x[2] * (y[0] - y[1]));
//
//        double ABP = Math.abs(x[0] * (y[1] - point.y)
//                + x[1] * (point.y - y[0]) + point.x * (y[0] - y[1]));
//
//        double APC = Math.abs(x[0] * (point.y - y[2])
//                + point.x * (y[2] - y[0]) + x[2] * (y[0] - point.y));
//
//        double PBC = Math.abs(point.x * (y[1] - y[2])
//                + x[1] * (y[2] - point.y) + x[2] * (point.y - y[1]));
//
//        return ABP + APC + PBC == ABC;
        return false;
    }



    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Triangle(this);
    }

    @Override
    public ShapeFX shrink(double shrinkFactor) {
        ShapeFX shrinkedShape = null;
        try {
            shrinkedShape = (ShapeFX) this.clone();
            Point pos = shrinkedShape.getPosition();
            shrinkedShape.setPosition(new Point((int) (pos.x * shrinkFactor), (int) (pos.y * shrinkFactor)));
            int[] xs = ((Triangle)shrinkedShape).getXs();
            int[] ys = ((Triangle)shrinkedShape).getYs();
            xs =Arrays.stream(xs).mapToDouble(n->n*shrinkFactor).mapToInt(n->(int)n).toArray();
            ys =Arrays.stream(ys).mapToDouble(n->n*shrinkFactor).mapToInt(n->(int)n).toArray();
            ((Triangle)shrinkedShape).setXs(xs);
            ((Triangle)shrinkedShape).setYs(ys);
            Map<String, Double> prop = shrinkedShape.getProperties();
            Double stroke = prop.get(STROKE_WIDTH);
            prop.put(STROKE_WIDTH, stroke * shrinkFactor);
            prop.put(VISIBILITY,VISIBILE);
            shrinkedShape.setProperties(prop);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return shrinkedShape;
    }
}
