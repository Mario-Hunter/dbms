package eg.edu.alexu.csd.oop.draw.cs49.models;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.*;
import java.util.List;

import static eg.edu.alexu.csd.oop.draw.cs49.models.ShapeFactory.shapes.*;
import static org.junit.Assert.*;


public class DrawingEngineTest {

    private DrawingEngine drawingEngineFX;

    @Before
    public void setUp() {
        drawingEngineFX =
                new eg.edu.alexu.csd.oop.draw.cs49.models.DrawingEngine();
    }

    @Test
    public void testAddShape() throws Exception {
        assertNotNull(drawingEngineFX.getShapes());
        assertEquals(0, drawingEngineFX.getShapes().length);

        final Shape rectangle = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(rectangle);

        assertEquals(1, drawingEngineFX.getShapes().length);
        assertEquals(rectangle, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testAddNullShape() throws Exception {
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.addShape(null);

        assertEquals(0, drawingEngineFX.getShapes().length);
    }

    @Test
    public void testAddMultipleShapes() throws Exception {
        assertNotNull(drawingEngineFX.getShapes());
        assertEquals(0, drawingEngineFX.getShapes().length);

        final Shape rectangle = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(rectangle);

        assertEquals(1, drawingEngineFX.getShapes().length);
        assertEquals(rectangle, drawingEngineFX.getShapes()[0]);

        final Shape line = ShapeFactory.getShape(Line);
        drawingEngineFX.addShape(line);

        assertEquals(2, drawingEngineFX.getShapes().length);
        assertEquals(line, drawingEngineFX.getShapes()[1]);
        assertEquals(rectangle, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testRemoveExistingShape() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);

        assertEquals(1, drawingEngineFX.getShapes().length);

        final Shape shape2 = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape2);
        assertEquals(2, drawingEngineFX.getShapes().length);

        drawingEngineFX.removeShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSame(shape2, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.removeShape(shape2);
        assertEquals(0, drawingEngineFX.getShapes().length);
    }

    @Test
    public void testRemoveNotExistingShape() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);

        assertEquals(1, drawingEngineFX.getShapes().length);

        final Shape shape2 = ShapeFactory.getShape(Rectangle);

        drawingEngineFX.removeShape(shape2);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSame(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testRemoveNull() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);

        drawingEngineFX.removeShape(null);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSame(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUpdateShape() throws Exception {
        Shape shape = ShapeFactory.getShape(Rectangle);
        final Point point = new Point(0, 0);
        shape.setPosition(point);
        final Color color = Color.BLACK;
        shape.setColor(color);
        final Color fillColor = Color.darkGray;
        shape.setFillColor(fillColor);
        final Map<String, Double> prop = new HashMap<>();
        prop.put("test", 0d);
        shape.setProperties(prop);

        drawingEngineFX.addShape(shape);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        Shape newShape = (Shape) shape.clone();
        newShape.setPosition(new Point(1, 1));
        drawingEngineFX.updateShape(shape, newShape);
        assertSameShape(newShape, drawingEngineFX.getShapes()[0]);

        shape = (Shape) newShape.clone();
        shape.setColor(Color.GRAY);
        drawingEngineFX.updateShape(newShape, shape);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        newShape = (Shape) shape.clone();
        newShape.setFillColor(Color.BLUE);
        drawingEngineFX.updateShape(shape, newShape);
        assertSameShape(newShape, drawingEngineFX.getShapes()[0]);

        shape = (Shape) newShape.clone();
        prop.put("test", 2d);
        prop.put("test2", 0d);
        shape.setProperties(prop);
        drawingEngineFX.updateShape(newShape, shape);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);


    }

    @Test
    public void testUpdateShapeWithDifferentShape() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        final Point point = new Point(0, 0);
        shape.setPosition(point);
        final Color color = Color.BLACK;
        shape.setColor(color);
        final Color fillColor = Color.darkGray;
        shape.setFillColor(fillColor);
        final Map<String, Double> prop = new HashMap<>();
        prop.put("test", 0d);
        shape.setProperties(prop);

        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        final Shape newShape = ShapeFactory.getShape(Line);
        shape.setPosition(new Point(1, 1));
        shape.setColor(Color.YELLOW);
        shape.setFillColor(Color.YELLOW);
        final Map<String, Double> newProp = new HashMap<>();
        newProp.put("test", 2d);
        newProp.put("test2", 1d);
        newShape.setProperties(newProp);

        drawingEngineFX.updateShape(shape, newShape);
        assertSameShape(newShape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUpdateNull() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);

        final Shape newShape = ShapeFactory.getShape(Line);
        drawingEngineFX.updateShape(null, newShape);

        assertSameShape(shape, drawingEngineFX.getShapes()[0]);
        assertNotSame(newShape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUpdateWithNull() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);

        drawingEngineFX.updateShape(shape, null);
        assertNotNull(drawingEngineFX.getShapes()[0]);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testGetShapes() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        final Shape shape1 = ShapeFactory.getShape(Square);
        final Shape shape2 = ShapeFactory.getShape(Line);
        final Shape shape3 = ShapeFactory.getShape(Triangle);

        final List<Shape> shapes = new ArrayList<>();
        shapes.add(shape);
        shapes.add(shape1);
        shapes.add(shape2);
        shapes.add(shape3);

        drawingEngineFX.addShape(shape);
        drawingEngineFX.addShape(shape1);
        drawingEngineFX.addShape(shape2);
        drawingEngineFX.addShape(shape3);

        checkSameTwoListsWithoutOrder(shapes,
                Arrays.asList(drawingEngineFX.getShapes()));

    }

    @Test
    public void testGetShapesWithNull() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        final Shape shape1 = ShapeFactory.getShape(Square);
        final Shape shape2 = ShapeFactory.getShape(Line);
        final Shape shape3 = ShapeFactory.getShape(Triangle);

        final List<Shape> shapes = new ArrayList<>();
        shapes.add(shape);
        shapes.add(shape1);
        shapes.add(shape2);
        shapes.add(shape3);

        drawingEngineFX.addShape(shape);
        drawingEngineFX.addShape(shape1);
        drawingEngineFX.addShape(shape2);
        drawingEngineFX.addShape(shape3);
        drawingEngineFX.addShape(null);

        checkSameTwoListsWithoutOrder(shapes,
                Arrays.asList(drawingEngineFX.getShapes()));

    }


    private void checkSameTwoListsWithoutOrder(final Collection expected,
                                               final Collection actual) {
        //noinspection unchecked,unchecked
        assertTrue(expected.containsAll(actual) &&
                actual.containsAll(expected));
    }

    @Test
    public void testUndoRedoLimit() throws Exception {
        for (int i = 0; i < 21; i++) {
            drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
            assertEquals(i + 1, drawingEngineFX.getShapes().length);
        }

        for (int i = 0; i < 21; i++) {
            drawingEngineFX.undo();
        }
        assertEquals(1, drawingEngineFX.getShapes().length);
        for (int i = 0; i < 21; i++) {
            drawingEngineFX.redo();
        }
        assertEquals(21, drawingEngineFX.getShapes().length);
        for (int i = 0; i < 21; i++) {
            drawingEngineFX.undo();
        }
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        drawingEngineFX.undo();
        drawingEngineFX.undo();
        assertEquals(1, drawingEngineFX.getShapes().length);


    }

    @Test
    public void testUndoRedoAddShape() throws Exception {
        assertEquals(0, drawingEngineFX.getShapes().length);
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);

        drawingEngineFX.undo();
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        this.assertSameShape(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUndoRedoAddNull() throws Exception {
        assertEquals(0, drawingEngineFX.getShapes().length);
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        drawingEngineFX.addShape(null);
        assertEquals(1, drawingEngineFX.getShapes().length);

        drawingEngineFX.undo();
        drawingEngineFX.addShape(null);
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        this.assertSameShape(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUndoRedoRemoveShape() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        drawingEngineFX.removeShape(shape);
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.undo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.redo();
        assertEquals(0, drawingEngineFX.getShapes().length);
    }

    @Test
    public void testUndoRemoveNull() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        drawingEngineFX.addShape(null);
        drawingEngineFX.removeShape(shape);
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.undo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.redo();
        assertEquals(0, drawingEngineFX.getShapes().length);
    }

    @Test
    public void testUndoRedoUpdate() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        shape.setColor(Color.BLACK);
        shape.setFillColor(Color.BLUE);
        shape.setPosition(new Point(0, 0));

        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        final Shape newShape = ShapeFactory.getShape(Line);
        newShape.setColor(Color.GREEN);
        newShape.setFillColor(Color.RED);
        newShape.setPosition(new Point(1, 2));

        drawingEngineFX.updateShape(shape, newShape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(newShape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.undo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(newShape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testUndoRedoUpdateWithNullAsParameter() throws Exception {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        shape.setColor(Color.BLACK);
        shape.setFillColor(Color.BLUE);
        shape.setPosition(new Point(0, 0));

        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        final Shape newShape = ShapeFactory.getShape(Line);
        newShape.setColor(Color.GREEN);
        newShape.setFillColor(Color.RED);
        newShape.setPosition(new Point(1, 2));

        drawingEngineFX.updateShape(shape, null);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.undo();
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.updateShape(null, newShape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);

        drawingEngineFX.undo();
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);
        assertSameShape(shape, drawingEngineFX.getShapes()[0]);
    }

    @Test
    public void testXML() {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        final Shape shape1 = ShapeFactory.getShape(Square);
        drawingEngineFX.addShape(shape1);
        assertEquals(2, drawingEngineFX.getShapes().length);
        final Shape shape2 = ShapeFactory.getShape(Triangle);
        drawingEngineFX.addShape(shape2);
        assertEquals(3, drawingEngineFX.getShapes().length);

        drawingEngineFX.save("testSave.xml");
        drawingEngineFX.removeShape(shape);
        assertEquals(2, drawingEngineFX.getShapes().length);


        drawingEngineFX.load("testSave.xml");
        assertEquals(3, drawingEngineFX.getShapes().length);
        checkListContains(shape,
                Arrays.asList(drawingEngineFX.getShapes()));
        checkListContains(shape1,
                Arrays.asList(drawingEngineFX.getShapes()));
        checkListContains(shape2,
                Arrays.asList(drawingEngineFX.getShapes()));

    }

    private void checkListContains(final Shape shape,
                                   final List<Shape> shapes) {
        assertTrue(shapes.stream().
                anyMatch(shapeElement -> checkSameShape(shape, shapeElement)));
    }

    private boolean checkSameShape(final Shape expected,
                                   final Shape actual) {

        return expected.getPosition().equals(actual.getPosition())
                && expected.getColor().equals(actual.getColor())
                && expected.getFillColor().equals(actual.getFillColor())
                && expected.getProperties().equals(actual.getProperties());
    }

    private void assertSameShape(final Shape expected, final Shape actual) {
        assertSame(expected, actual);
        assertSame(expected.getPosition(), actual.getPosition());
        assertSame(expected.getColor(), actual.getColor());
        assertSame(expected.getFillColor(), actual.getFillColor());
        assertSame(expected.getProperties(), actual.getProperties());
    }

    @Test
    public void testJson() {
        final Shape shape = ShapeFactory.getShape(Rectangle);
        drawingEngineFX.addShape(shape);
        assertEquals(1, drawingEngineFX.getShapes().length);
        final Shape shape1 = ShapeFactory.getShape(Square);
        drawingEngineFX.addShape(shape1);
        assertEquals(2, drawingEngineFX.getShapes().length);
        final Shape shape2 = ShapeFactory.getShape(Triangle);
        drawingEngineFX.addShape(shape2);
        assertEquals(3, drawingEngineFX.getShapes().length);

        drawingEngineFX.save("testSave.json");
        drawingEngineFX.removeShape(shape);

        drawingEngineFX.load("testSave.json");
        assertEquals(3, drawingEngineFX.getShapes().length);

        assertEquals(3, drawingEngineFX.getShapes().length);
        checkListContains(shape,
                Arrays.asList(drawingEngineFX.getShapes()));
        checkListContains(shape1,
                Arrays.asList(drawingEngineFX.getShapes()));
        checkListContains(shape2,
                Arrays.asList(drawingEngineFX.getShapes()));

    }

    @Test
    public void testUndo() {
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        assertEquals(1, drawingEngineFX.getShapes().length);

        drawingEngineFX.undo();
        assertEquals(0, drawingEngineFX.getShapes().length);
    }

    @Test
    public void testSaveUnknownFormat() throws Exception {
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        try {
            drawingEngineFX.save("testjson");
            fail();
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }

        try {
            drawingEngineFX.save("testxml");
            fail();
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveNull() throws Exception {
        drawingEngineFX.addShape(null);
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.save("testjson.json");
        drawingEngineFX.load("testjson.json");
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.save("testxml.json");
        drawingEngineFX.load("testxml.json");
        assertEquals(0, drawingEngineFX.getShapes().length);

    }

    @Test
    public void testLoadNotExistingFile() throws Exception {
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));

        assertEquals(3,drawingEngineFX.getShapes().length);
        try{
            drawingEngineFX.load("notExisting.json");
            fail();
        }catch(final RuntimeException e){
            e.printStackTrace();
            assertEquals(3,drawingEngineFX.getShapes().length);
        }

        try{
            drawingEngineFX.load("notExisting.xml");
            fail();
        }catch(final RuntimeException e){
            e.printStackTrace();
            assertEquals(3,drawingEngineFX.getShapes().length);
        }
    }

    @Test
    public void testGetSupportedShapes() throws Exception {
        final List<Class<? extends Shape>> classes =
                drawingEngineFX.getSupportedShapes();
        assertNotNull(classes);
        assertNotSame(0, classes.size());
        assertEquals(4, classes.size());   //updatable value
    }


    @Test
    public void testRedo() {
        drawingEngineFX.addShape(ShapeFactory.getShape(Rectangle));
        assertEquals(1, drawingEngineFX.getShapes().length);

        final HashMap<String, Double> prop =
                (HashMap<String, Double>) drawingEngineFX.getShapes()[0]
                        .getProperties();

        drawingEngineFX.undo();
        assertEquals(0, drawingEngineFX.getShapes().length);

        drawingEngineFX.redo();
        assertEquals(1, drawingEngineFX.getShapes().length);

        assertTrue(drawingEngineFX.getShapes()[0] instanceof ShapeFX);
        assertEquals(prop, drawingEngineFX
                .getShapes()[0]
                .getProperties());
    }
}
