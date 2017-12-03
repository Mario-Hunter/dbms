package eg.edu.alexu.csd.oop.draw.cs49.models.Observer;

import eg.edu.alexu.csd.oop.draw.cs49.models.command.Command;

public class CommandNotification implements Notification {


    private Command command;

    public CommandNotification(Command command) {
        this.command = command;
    }

    @Override
    public Command getNotification() {
        return command;
    }

}
