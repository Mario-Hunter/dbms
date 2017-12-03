package eg.edu.alexu.csd.oop.draw;


import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public interface ShapeFX extends Shape {
     void draw(GraphicsContext canvas);

    boolean isBounding(Point point);
}
