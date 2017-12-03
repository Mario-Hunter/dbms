package eg.edu.alexu.csd.oop.draw.cs49.models.polygons;

public class Square extends Rectangle {
    public Square(final int x, final int y, final int edgeLength) {
        super(x, y, edgeLength, edgeLength);
    }

    public Square() {
        super(Rectangle.DEFAULT_WIDTH, Rectangle.DEFAULT_WIDTH);
    }

    public Square(final Square square) {
        super(square);
    }

    @Override
    protected void populateDefaults() {
        super.populateDefaults();
        defaultProperties.put(HEIGHT, (double) DEFAULT_HEIGHT);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Square(this);
    }
}
