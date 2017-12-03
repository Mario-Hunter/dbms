package eg.edu.alexu.csd.oop.draw.cs49.presenters;

import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.ShrinkableShape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.INVISIBLE;
import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.VISIBILE;
import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.VISIBILITY;

public class LayersPresenter implements Initializable, Observer {
    private List<ShapeFX> shapes;
    @FXML
    private VBox vpane;
    private double canvasHeight;
    private PaintPresenter paintPresenter;

    public void setPaintPresenter(final PaintPresenter paintPresenter) {
        this.paintPresenter = paintPresenter;
    }

    public void setCanvasHeight(final double canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public void setCanvasWidth(final double canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    private double canvasWidth;

    public void setShapes(final List<ShapeFX> shapes) {
        this.shapes = shapes;
        drawLayers();
    }

    public LayersPresenter() {
        shapes = new ArrayList<>();
    }


    private void drawLayers() {
        vpane.getChildren().clear();
        for (ShapeFX shape : shapes) {
            HBox pane = new HBox();
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
            {
                Map<String, Double> prop = shape.getProperties();
                if (newValue) {
                    prop.put(VISIBILITY, VISIBILE);
                } else {
                    prop.put(VISIBILITY, INVISIBLE);
                }
                paintPresenter.refresh();
            });
            pane.setOnMouseClicked(event -> {
                vpane.getChildren().forEach(n -> n.getStyleClass().clear());
                pane.getStyleClass().clear();
                pane.getStyleClass().add("pane-selected");
                paintPresenter.selectShape(shape);
            });
            checkBox.setSelected(Double.compare(VISIBILE, shape.getProperties().get(VISIBILITY)) == 0);
            Label name = new Label();
            name.setText(shape.getClass().getSimpleName());
            Separator leftBound = new Separator();
            leftBound.setOrientation(Orientation.VERTICAL);
            Separator rightBound = new Separator();
            rightBound.setOrientation(Orientation.VERTICAL);
            Canvas canvas = new Canvas();
            canvas.setWidth(50);
            canvas.setHeight(canvasHeight * 50 / canvasWidth);
            canvas.setLayoutX(0);
            canvas.setLayoutY(0);
            ShapeFX shrinked = ((ShrinkableShape) shape).shrink(50 / canvasWidth);
            shrinked.draw(canvas.getGraphicsContext2D());
            pane.setSpacing(2d);
            pane.getStyleClass().add("layerPane");
            pane.setPrefHeight(canvasHeight * 50 / canvasWidth);
            pane.setMinHeight(canvasHeight * 50 / canvasWidth);
            pane.getChildren().addAll(checkBox, leftBound, canvas, rightBound, name);
            vpane.getChildren().add(pane);
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            vpane.getChildren().add(separator);
        }
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        vpane.getStyleClass().add("vpane");

    }

    @Override
    public void notify(final List<ShapeFX> focusedShapes) {
        setShapes(focusedShapes);
    }
}
