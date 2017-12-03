package eg.edu.alexu.csd.oop.draw.cs49.models.command;

public interface Command {

    void execute(Receiver receiver);

    void neutralize(Receiver receiver);

    void execute(Receiver receiver, boolean notifiable);
}
