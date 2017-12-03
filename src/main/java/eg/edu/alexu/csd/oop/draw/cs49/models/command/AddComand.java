package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.util.List;

public class AddComand extends AbstractCommand {

    public AddComand(ShapeFX... shapes) {
        super(shapes);
    }

    public AddComand(final Shape shape) {
        super(shape);
    }

    @Override
    public void execute(Receiver receiver,boolean notifiable) {
        receiver.addShape(shapes[0],notifiable);
    }

    @Override
    public void neutralize(Receiver receiver) {
        receiver.removeShape(shapes[0],false);
    }
}
