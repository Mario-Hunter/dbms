package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;

public abstract class AbstractCommand implements Command {
    protected Shape[] shapes;

    public AbstractCommand(ShapeFX... shapes) {
        this.shapes = shapes;
    }

    public AbstractCommand(final Shape... shape) {
        this.shapes = shape;
    }


    public void execute(Receiver receiver) {
        execute(receiver, true);
    }

    public abstract void neutralize(Receiver receiver);

    public abstract void execute(Receiver receiver, boolean notifiable);
}
