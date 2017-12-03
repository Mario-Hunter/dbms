package eg.edu.alexu.csd.oop.draw.cs49.models.polygons;

import eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Polygon extends AbstractShape {
    public static final String EDGES_NUMBER = "edgesNumber";
    @XmlElement
    protected int[] xs;
    @XmlElement
    protected int[] ys;

    public Polygon(AbstractShape shape) {
        super(shape);
    }

    public Polygon() {
        super();
    }
}
