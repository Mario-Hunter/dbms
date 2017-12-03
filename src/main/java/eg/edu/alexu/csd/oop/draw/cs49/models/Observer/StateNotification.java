package eg.edu.alexu.csd.oop.draw.cs49.models.Observer;

public class StateNotification implements Notification {
    private final Roll roll;

    public enum Roll{
        forward,backward
    }

    public StateNotification(Roll roll) {
        this.roll = roll;
    }

    @Override
    public Roll getNotification() {
        return roll;
    }

}
