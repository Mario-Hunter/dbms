package eg.edu.alexu.csd.oop.draw.cs49.models;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.Receiver;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.RemoteControl;
import eg.edu.alexu.csd.oop.draw.cs49.models.memento.CareTaker;
import eg.edu.alexu.csd.oop.draw.cs49.models.memento.CommandState;
import eg.edu.alexu.csd.oop.draw.cs49.models.memento.Memento;
import eg.edu.alexu.csd.oop.draw.cs49.models.memento.Originator;
import eg.edu.alexu.csd.oop.draw.Shape;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingEngineRemoteControl extends RemoteControl implements Originator {
    private CareTaker careTaker;

    public DrawingEngineRemoteControl() {
        super();
        this.receiver = (Receiver) new DrawingEngine();
        this.careTaker = new CareTaker(this);
        ((DrawingEngine) receiver).registerObserver(careTaker);
    }

    public DrawingEngineRemoteControl(Receiver receiver) {
        super();
        this.receiver = receiver;
        this.careTaker = new CareTaker(this);
        ((DrawingEngine) receiver).registerObserver(careTaker);
    }

    @Override
    public void setState(Memento memento, boolean neutralize) {
        if (neutralize) {
            ((CommandState) memento.getState()).getState().neutralize(receiver);

        } else {
            ((CommandState) memento.getState()).getState().execute(receiver, false);

        }

    }

    public void refreshReceiver(GraphicsContext canvas) {
        receiver.refresh(canvas);
    }


    public ShapeFX getShapeBounding(final Point point) {
        return receiver.getShapeBounding(point);
    }

    public List<Class<? extends Shape>> getRecieverSupportedShapes() {
        return receiver.getSupportedShapes();
    }

    public List<ShapeFX> getShapes() {
        List<ShapeFX> shapes = new ArrayList<>();
        for(Shape shape :((DrawingEngine) receiver).getShapes() ){
            shapes.add((ShapeFX) shape);
        }
        return shapes;
    }
}
