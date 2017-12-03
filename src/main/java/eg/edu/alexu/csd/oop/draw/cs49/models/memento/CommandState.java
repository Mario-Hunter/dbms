package eg.edu.alexu.csd.oop.draw.cs49.models.memento;

import eg.edu.alexu.csd.oop.draw.cs49.models.command.AbstractCommand;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.Command;

public class CommandState implements State {
    private Command command;

    public CommandState(Command command) {
        this.command = command;
    }

    @Override
    public Command getState() {
        return command;
    }

}
