package eg.edu.alexu.csd.oop.draw.cs49.models.Observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Notification notification);
}
