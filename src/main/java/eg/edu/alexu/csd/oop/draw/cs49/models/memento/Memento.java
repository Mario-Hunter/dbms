package eg.edu.alexu.csd.oop.draw.cs49.models.memento;

public class Memento {

    private State state;

    public Memento(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }


}
