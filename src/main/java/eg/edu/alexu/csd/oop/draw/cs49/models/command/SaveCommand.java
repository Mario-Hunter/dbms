package eg.edu.alexu.csd.oop.draw.cs49.models.command;

public class SaveCommand extends AbstractCommand {
    private final String path;

    public SaveCommand(final String path) {
        this.path = path;
    }

    @Override
    public void neutralize(final Receiver receiver) {

    }

    @Override
    public void execute(final Receiver receiver, final boolean notifiable) {
        receiver.save(path);
    }
}
