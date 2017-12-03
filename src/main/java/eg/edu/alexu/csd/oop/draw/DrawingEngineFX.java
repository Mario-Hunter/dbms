package eg.edu.alexu.csd.oop.draw;


import javafx.scene.canvas.GraphicsContext;

public interface DrawingEngineFX extends DrawingEngine {
    void refresh(GraphicsContext canvas);
}
