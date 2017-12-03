package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;

public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(Shape... shapes) {
        super(shapes);
    }

    @Override
    public void execute(Receiver receiver, boolean notifiable) {
        receiver.updateShape(shapes[0], shapes[1], notifiable);
    }

    @Override
    public void neutralize(Receiver receiver) {
        receiver.updateShape(shapes[1], shapes[0], false);
    }
}
