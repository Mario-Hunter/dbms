package eg.edu.alexu.csd.oop.draw.cs49.models.command;

public class UndoCommand extends AbstractCommand {
    @Override
    public void execute(Receiver receiver,boolean notifiable) {
        receiver.undo();
    }

    @Override
    public void neutralize(Receiver receiver) {
        receiver.redo();
    }
}
