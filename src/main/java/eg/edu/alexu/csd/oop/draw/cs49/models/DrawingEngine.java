package eg.edu.alexu.csd.oop.draw.cs49.models;


import com.google.gson.reflect.TypeToken;
import eg.edu.alexu.csd.oop.Utilities.ObjectParser;
import eg.edu.alexu.csd.oop.draw.DrawingEngineFX;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.*;
import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.Observer;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.*;

import java.io.File;

import eg.edu.alexu.csd.oop.Utilities.reflections.ReflectionInterfaceScanner;
import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DrawingEngine implements DrawingEngineFX, Subject, Receiver {
    private TypeToken<List<ShapeFX>> gsonTypeToken;
    private ArrayList<ShapeFX> shapes;
    private List<Class<? extends Shape>> supportedShapes;
    private transient Collection<Observer> observers;


    /**
     * For the damn online tester.
     */
    private transient DrawingEngineRemoteControl remoteControl;
    private static ArrayList<Shape> savedShapes;

    /**
     * For the damn online tester.
     */
    public DrawingEngine() {
        shapes = new ArrayList<>();
        observers = new ArrayList<>();
        supportedShapes = new ArrayList<>();
        getSupportedShapes();
        remoteControl = new DrawingEngineRemoteControl(this);
        gsonTypeToken = new TypeToken<List<ShapeFX>>() {
        };
    }

    public DrawingEngine(final Collection<ShapeFX> shapes) {
        this.shapes = new ArrayList<>(shapes);
    }

    private void cloneFromEngine(List<ShapeFX> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
    }

    @Override
    public void refresh(final Graphics canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);
        }
    }

    @Override
    public void refresh(GraphicsContext canvas) {
        canvas.clearRect(0, 0, canvas.getCanvas().getWidth(), canvas.getCanvas().getHeight());
        for (ShapeFX shape : shapes) {
            shape.draw(canvas);
        }

    }

    @Override
    public void addShape(final Shape shape) {
        addShape(shape, true);
    }

    public void addShape(final Shape shape, boolean notifiable) {
        if (shape == null) {
            return;
        }
        shapes.add((ShapeFX) shape);
        if (notifiable) {
            notifyObservers(new CommandNotification(new AddComand((ShapeFX) shape)));
        }
    }

    @Override
    public void removeShape(final Shape shape) {
        removeShape(shape, true);

    }

    public void removeShape(final Shape shape, boolean notifiable) {
        if (shapes.contains(shape)) {
            shapes.remove(shape);
            if (notifiable) {
                notifyObservers(new CommandNotification(new RemoveCommand(shape)));
            }
        }
    }

    @Override
    public void updateShape(final Shape oldShape, final Shape newShape) {
        updateShape(oldShape, newShape, true);
    }

    public void updateShape(final Shape oldShape, final Shape newShape, boolean notifiable) {
        if (oldShape == null || newShape == null) {
            return;
        }
        int index = shapes.indexOf(oldShape);
        if (index == -1) return;
        shapes.set(index, (ShapeFX) newShape);
        if (notifiable) {
            notifyObservers(new CommandNotification(new UpdateCommand(oldShape, newShape)));
        }
    }

    @Override
    public Shape[] getShapes() {
        Shape[] shapesArray = new Shape[shapes.size()];
        for (int i = 0; i < shapes.size(); i++) {
            shapesArray[i] = shapes.get(i);
        }
        return shapesArray;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {


        if (supportedShapes.isEmpty()) {
            ReflectionInterfaceScanner<Shape> utils = new ReflectionInterfaceScanner<>(Shape.class);

            supportedShapes = utils.loadClassFrom("eg",
                    "google", "presenters", "resources", "META-INF",
                    "views","junit","org");
        }

        return supportedShapes;

    }

    @Override
    public void addSupportedShapes(final List<Class<? extends Shape>> classes) {
        for (Class<? extends Shape> cls : classes) {
            supportedShapes.add(cls);
        }
    }

    @Override
    public void undo() {
        notifyObservers(new StateNotification(StateNotification.Roll.backward));
    }

    @Override
    public void redo() {
        notifyObservers(new StateNotification(StateNotification.Roll.forward));
    }

    @Override
    public ShapeFX getShapeBounding(final Point point) {
        for (Shape shape : shapes) {
            if (((ShapeFX) shape).isBounding(point)) {
                return (ShapeFX) shape;
            }
        }
        return null;
    }

    @Override
    public void save(final String path) {
        File file = new File(path);
        ObjectParser<ShapeFX> parser = new ObjectParser(ShapeFX.class);
        if (path.endsWith(".json")) {
            parser.serialize(ObjectParser.JSON_PARSE, this.shapes, file,
                    convertClasses(supportedShapes), gsonTypeToken);
        } else if (path.endsWith("xml") || path.endsWith(".XmL") || path.endsWith("Xml")) {
            parser.serialize(ObjectParser.XML_PARSE, this.shapes, file,
                    convertClasses(supportedShapes));

        } else {
            throw new RuntimeException(path);
        }

    }

    private List<Class> convertClasses(List<Class<? extends Shape>> classes) {
        List<Class> classesAdaptee = new ArrayList<>();
        for (Class<? extends Shape> cls : classes) {
            classesAdaptee.add(cls);
        }
        return classesAdaptee;
    }

    @Override
    public void load(final String path) {
        File file = new File(path);
        ObjectParser<ShapeFX> parser = new ObjectParser(ShapeFX.class);
        if (path.endsWith(".json")) {
            List<ShapeFX> shapes = parser.deserialize(ObjectParser.JSON_DECODE,
                    file, convertClasses(supportedShapes), gsonTypeToken);
            cloneFromEngine(shapes);
            notifyObservers(new CommandNotification(new LoadCommand(path)));
        } else if (path.endsWith(".xml") || path.endsWith(".XmL") || path.endsWith("Xml")) {

            List<ShapeFX> shapes = parser.deserialize(ObjectParser
                            .XML_DECODE, file,
                    convertClasses(supportedShapes));
            cloneFromEngine(shapes);

            notifyObservers(new CommandNotification(new LoadCommand(path)));
        } else {
            throw new RuntimeException(path);
        }

    }

//    private void cloneFromEngine(final List<Shape> shapes) {
//        this.shapes.clear();
//        for (Shape shape : shapes) {
//            if (ShapeFX.class.isAssignableFrom(shape.getClass())) {
//                addShape(shape);
//            }
//        }
//    }

    private void cloneFromEngine(final Shape[] shapes) {
        this.shapes.clear();
        for (Shape shape : shapes) {
            addShape(shape, false);
        }
    }


    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Notification notification) {
        for (Observer observer : observers) {
            observer.notify(notification);
        }
    }
}
