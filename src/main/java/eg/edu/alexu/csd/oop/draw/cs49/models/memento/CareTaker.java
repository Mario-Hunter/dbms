package eg.edu.alexu.csd.oop.draw.cs49.models.memento;


import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.CommandNotification;
import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.Notification;
import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.Observer;
import eg.edu.alexu.csd.oop.draw.cs49.models.Observer.StateNotification;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.Command;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.LoadCommand;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.SaveCommand;

import java.util.Stack;

public class CareTaker implements Observer {
    private static final int DEFAULT_LIMIT = 20;
    private static final int FIRST_ELEMENT = 0;
    private final int limit;
    private Stack<Memento> undoMementos;
    private Stack<Memento> redoMementos;
    private Originator originator;


    public CareTaker(Originator originator, final int operationsLimit) {
        this.originator = originator;
        undoMementos = new Stack<>();
        redoMementos = new Stack<>();
        this.limit = operationsLimit;
    }

    public CareTaker(final Originator originator) {
        this(originator,DEFAULT_LIMIT);
    }

    public void addMemento(Memento memento) {
        undoMementos.push(memento);
        redoMementos.clear();
        if(undoMementos.size() > limit){
            undoMementos.remove(FIRST_ELEMENT);
        }
    }

    public void rollBack() {
        if (undoMementos.empty()) return;
        redoMementos.push(undoMementos.pop());
        originator.setState(redoMementos.peek(), true);
    }

    public void stepForward() {
        if (redoMementos.isEmpty()) return;
        Memento memento = redoMementos.pop();
        undoMementos.push(memento);
        originator.setState(memento, false);
    }

    @Override
    public void notify(Notification notification) {
        if (notification instanceof StateNotification) {
            changeState(((StateNotification) notification).getNotification());
        }
        if (notification instanceof CommandNotification) {
            handleCommandNotification(((CommandNotification) notification)
                    .getNotification());
        }
    }

    private void handleCommandNotification(final Command command) {
        if (command instanceof LoadCommand) {
            clearMementos();
        } else if (!(command instanceof SaveCommand)) {
            addMemento(new Memento(new CommandState(command)));
        }
    }

    private void clearMementos() {
        undoMementos.clear();
        redoMementos.clear();
    }

    private void changeState(StateNotification.Roll roll) {
        if (roll == StateNotification.Roll.backward) {
            rollBack();
        } else {
            stepForward();
        }
    }
}
