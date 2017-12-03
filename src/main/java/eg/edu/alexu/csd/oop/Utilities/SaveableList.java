package eg.edu.alexu.csd.oop.Utilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SaveableList<T> {

    @XmlElement(name = "shapes")
    public List<T> elements;

    public SaveableList(final List<T> elements) {
        this.elements = elements;
    }

    public SaveableList() {
        this.elements = new ArrayList<>();
    }
}
