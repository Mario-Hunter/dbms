package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.util.ArrayList;
import java.util.List;

public class AddSupportedShapeCommand extends AbstractCommand {

    private final List<Class<? extends Shape>> classes;



    public AddSupportedShapeCommand(final List<Class<? extends Shape>> shapesClasses) {
        this.classes = shapesClasses;

    }

    @Override
    public void neutralize(final Receiver receiver) {

    }

    @Override
    public void execute(final Receiver receiver, final boolean notifiable) {
        receiver.addSupportedShapes(classes);
    }
}
