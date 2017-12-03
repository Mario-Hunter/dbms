package eg.edu.alexu.csd.oop.draw.cs49.models.command;

public abstract class RemoteControl {
    protected Receiver receiver;
    protected Command command;

    public RemoteControl(Receiver receiver) {
        this.receiver = receiver;
    }

    public RemoteControl() {

    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void fireCommand() {
        command.execute(receiver);
    }
    public void fireCommand(boolean notifiable) {
        command.execute(receiver,notifiable);
    }

    public void neutralizeCommand() {
        command.neutralize(receiver);
    }
}
