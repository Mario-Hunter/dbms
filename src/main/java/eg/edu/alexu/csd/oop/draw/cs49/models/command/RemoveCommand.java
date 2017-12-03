package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;

public class RemoveCommand extends AbstractCommand {
    public RemoveCommand(Shape... shapes) {
        super(shapes);
    }

    @Override
    public void execute(Receiver receiver,boolean notifiable) {
        receiver.removeShape(shapes[0],notifiable);
    }

    @Override
    public void neutralize(Receiver receiver) {
        receiver.addShape(shapes[0],false);
    }
}
