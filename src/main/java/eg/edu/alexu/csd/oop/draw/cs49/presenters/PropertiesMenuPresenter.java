package eg.edu.alexu.csd.oop.draw.cs49.presenters;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape;
import eg.edu.alexu.csd.oop.draw.cs49.models.ColorAdapter;
import eg.edu.alexu.csd.oop.draw.cs49.models.polygons.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.STROKE_WIDTH;

public class PropertiesMenuPresenter implements Initializable, Observer {
    @FXML
    private TextField xPosition;

    @FXML
    private ColorPicker fillColor;

    @FXML
    private TextField yPosition;

    @FXML
    private TextField strokeWidth;

    @FXML
    private ColorPicker strokeColor;

    @FXML
    private ToolBar rectangleToolbar;

    @FXML
    private TextField recWidth;


    @FXML
    private TextField recHeight;

    private List<ShapeFX> focusedShapes;
    private List<Observer> observerList;
    private List<ToolBar> shapeToolbars;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observerList = new ArrayList<>();
        shapeToolbars = new ArrayList<>();
        focusedShapes = new ArrayList<>();
        populateToolbars();
        hideAllToolbars();
    }

    private void populateToolbars() {
        shapeToolbars.add(rectangleToolbar);
    }

    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    public void updateToolbar(ShapeFX shape) {
        try {
            focusedShapes.clear();
            focusedShapes.add((ShapeFX) shape.clone());
        } catch (CloneNotSupportedException e) {
            return;
        }
        xPosition.setText(String.valueOf(shape.getPosition().x));
        yPosition.setText(String.valueOf(shape.getPosition().y));
        java.awt.Color fill = shape.getFillColor();
        fillColor.setValue(ColorAdapter.getFxColor(fill));
        strokeColor.setValue(ColorAdapter.getFxColor(shape.getColor()));
        strokeWidth.setText(String.valueOf(shape.getProperties().get
                (STROKE_WIDTH)));
        updateShapeToolbar(shape);
    }

    public void updateToolbar(List<ShapeFX> shapes) {
        if (shapes.size() == 1) {
            updateToolbar(shapes.get(0));
            return;
        }
        focusedShapes.clear();
        shapes.stream().forEach(n -> {
            try {
                focusedShapes.add((ShapeFX) n.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        xPosition.setText("");
        yPosition.setText("");
        java.awt.Color fill = Color.GRAY;
        fillColor.setValue(ColorAdapter.getFxColor(fill));
        strokeColor.setValue(ColorAdapter.getFxColor(Color.GRAY));
        strokeWidth.setText("");
        rectangleToolbar.setVisible(false);
    }

    private void updateShapeToolbar(final ShapeFX shape) {
        hideAllToolbars();
        rectangleToolbar.setVisible(true);
        rectangleToolbar.getItems().clear();
        for (Map.Entry<String, Double> entry : shape.getProperties().entrySet()) {
            String entryKey = entry.getKey();
            if (entryKey.equals(AbstractShape.X_CLICKED) ||
                    entryKey.equals(AbstractShape.Y_CLICKED) ||
                    entryKey.equals(AbstractShape.STROKE_WIDTH) ||
                    entryKey.equals(AbstractShape.X_DRAG_START) ||
                    entryKey.equals(AbstractShape.Y_DRAG_START) ||
                    entryKey.equals(AbstractShape.X_DRAG_END) ||
                    entryKey.equals(AbstractShape.Y_DRAG_END) ||
                    entryKey.equals(Rectangle.EDGES_NUMBER))
                continue;
            Label lbl = new Label();
            lbl.setText(entryKey);
            TextField txt = new TextField();
            txt.setText(entry.getValue().toString());
            txt.setPrefColumnCount(3);
            txt.setOnAction(this::onTextChange);
            txt.setId(entryKey);
            lbl.setLabelFor(txt);
            rectangleToolbar.getItems().add(lbl);
            rectangleToolbar.getItems().add(txt);
        }


    }

    private void hideAllToolbars() {
        for (ToolBar toolbar : shapeToolbars) {
            toolbar.setVisible(false);
        }
    }

    @FXML
    void onTextChange(ActionEvent event) {
        for (ShapeFX focusedShape : focusedShapes) {
            Map<String, Double> prop = focusedShape.getProperties();
            if (event.getSource() == xPosition || event.getSource() == yPosition) {
                Point position = new Point(
                        Double.valueOf(xPosition.getText()).intValue(),
                        Double.valueOf(yPosition.getText()).intValue());
                focusedShape.setPosition(position);
            } else if (event.getSource() == strokeWidth)
                prop.put(STROKE_WIDTH, Double.valueOf(strokeWidth.getText()));
            else {
                TextField txt = (TextField) event.getSource();
                prop.put(txt.getId(), Double.valueOf(txt.getText()));
            }
            focusedShape.setProperties(prop);
        }
        notifyObservers(focusedShapes);

    }

    private void notifyObservers(final List<ShapeFX> focusedShapes) {
        for (Observer observer : observerList) {
            observer.notify(focusedShapes);
        }
    }

    @FXML
    void onColorChange(ActionEvent event) {
        if (event.getSource() == fillColor) {
            focusedShapes.stream().forEach(n -> n.setFillColor(ColorAdapter
                    .getAwtColor(fillColor.getValue())));
        }
        if (event.getSource() == strokeColor) {
            focusedShapes.stream().forEach(n -> n.setColor(ColorAdapter
                    .getAwtColor(strokeColor.getValue())));
        }
        notifyObservers(focusedShapes);
    }


    @Override
    public void notify(final List<ShapeFX> focusedShapes) {
        updateToolbar(focusedShapes);
    }
}
