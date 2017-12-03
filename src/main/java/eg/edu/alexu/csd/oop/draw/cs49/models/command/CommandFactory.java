package eg.edu.alexu.csd.oop.draw.cs49.models.command;

import eg.edu.alexu.csd.oop.draw.ShapeFX;

import java.util.List;

public final class CommandFactory {
    public enum Commands {
        add, remove, update,undo,redo
    }

    public static final AbstractCommand getCommand(Commands command, ShapeFX... shapes) {
        switch (command) {
            case add:
                return new AddComand(shapes);
            case remove:
                return new RemoveCommand(shapes);
            case update:
                return new UpdateCommand(shapes);
            case undo:
                return new UndoCommand();
            default:
                return null;
        }

    }
}
