package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.ShapeFX;

public class LoadCommand extends AbstractCommand {
    private final String path;

    public LoadCommand(final String path) {
        this.path = path;
    }

    @Override
    public void neutralize(final Receiver receiver) {

    }

    @Override
    public void execute(final Receiver receiver, final boolean notifiable) {
        receiver.load(path);
    }
}
