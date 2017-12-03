package eg.edu.alexu.csd.oop.draw.cs49.models;

import javafx.scene.paint.Color;

public class ColorAdapter {
    public static Color getFxColor(java.awt.Color color) {
        return new Color(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
    }

    public static java.awt.Color getAwtColor(final Color value) {
        return new java.awt.Color((int) (value.getRed() * 255),
                (int) (value.getGreen() * 255),
                (int) (value.getBlue() * 255),
                (int) (value.getOpacity() * 255));
    }
}
