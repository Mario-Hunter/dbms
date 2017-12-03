package eg.edu.alexu.csd.oop.draw.cs49.presenters;

import com.sun.javafx.css.Style;
import eg.edu.alexu.csd.oop.Utilities.reflections.ReflectionInterfaceScanner;
import eg.edu.alexu.csd.oop.Utilities.reflections.ReflectionUtils;
import eg.edu.alexu.csd.oop.draw.ShapeFX;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape;
import eg.edu.alexu.csd.oop.draw.cs49.models.DrawingEngineRemoteControl;
import eg.edu.alexu.csd.oop.draw.cs49.models.ShapeFactory;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.AddSupportedShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.CommandFactory;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.CommandFactory.Commands;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.LoadCommand;
import eg.edu.alexu.csd.oop.draw.cs49.models.command.SaveCommand;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.DragHandlerFactory;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.eventHandlers.ShapeDragHandler;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState.State;
import eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState.StateFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.X_DRAG_END;
import static eg.edu.alexu.csd.oop.draw.cs49.models.AbstractShape.Y_DRAG_END;
import static eg.edu.alexu.csd.oop.draw.cs49.models.command.CommandFactory.Commands.*;
import static eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState.State.state.drawing;
import static eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState.State.state.idle;
import static eg.edu.alexu.csd.oop.draw.cs49.presenters.machineState.State.state.moving;

public class PaintPresenter implements Initializable,
        Observer {
    private Button selectedControl;

    @FXML
    private PropertiesMenuPresenter toolbarController;

    @FXML
    private LayersPresenter layersController;

    @FXML
    private MenuItem loadFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private Button moveAction;

    @FXML
    private GridPane buttonPane;

    @FXML
    private Button recControl;

    @FXML
    private MenuItem loadClass;

    @FXML
    private Canvas canvas_id;

    @FXML
    private Button undoControl;

    @FXML
    private Button redoControl;

    private List<ShapeFX> shapesInFocus;
    private DrawingEngineRemoteControl remoteControl;
    private Stage stage;
    private Set<KeyCode> pressedKeys;
    private ShapeDragHandler shapeDragHandler;
    private State currentState;
    private Stack<Integer> numUndos;
    private Stack<Integer> numRedos;
    private List<ShapeFX> copiedShapes;

    private EventHandler<MouseEvent> dragDetectedHandler = event -> {
        currentState.setStartingPosition(new Point((int) event.getX(), (int) event.getY()));
        if (currentState.getState() == drawing) {
            createShape(currentState.getStartPosition());
            currentState.setShapes(shapesInFocus);
            shapeDragHandler = currentState.getShapeDragHandler();
        }
        if (currentState.getState() == moving) {
            currentState.setShapes(shapesInFocus);
            shapeDragHandler = currentState.getShapeDragHandler();

        }
    };

    private EventHandler<MouseEvent> draggingHandler = event -> {
        shapeDragHandler.handle(event);
        currentState.setUpdatedShapes(shapeDragHandler.getShapes());
        toolbarController.updateToolbar(shapeDragHandler.getShapes());
        for (int i = 0; i < shapesInFocus.size(); i++) {
            command(false, update, shapesInFocus.get(i), shapeDragHandler.getShapes().get(i));
            shapesInFocus.remove(i);
            shapesInFocus.add(i, shapeDragHandler.getShapes().get(i));
        }
    };

    private EventHandler<MouseEvent> mouseClickedHandler = event -> {
        if (currentState.getState() == moving) {
            selectShape(event.getX(), event.getY(), !pressedKeys.contains(KeyCode.CONTROL));
        }
    };

    private EventHandler<? super MouseEvent> dragReleaseHandler = new
            EventHandler<MouseEvent>() {
                @Override
                public void handle(final MouseEvent event) {
                    System.out.println("Drag Released");
                    int numUndos = 0;
                    for (int i = 0; i < shapesInFocus.size(); i++) {
                        if (currentState.getState() == drawing) {
                            command(false, remove, shapesInFocus.get(i));
                            command(true, add, shapesInFocus.get(i));
                            numUndos++;
                        }
                        if (currentState.getState() == moving && !event.isStillSincePress()) {
                            command(false, update, shapesInFocus.get(i), currentState
                                    .getShapes().get(i));
                            command(true, update, currentState.getShapes().get(i), shapesInFocus.get(i));
                            numUndos++;
                        }
                    }
                    if (numUndos > 0) {
                        PaintPresenter.this.numUndos.push(numUndos);
                        numRedos.clear();
                        layersController.setShapes(remoteControl.getShapes());
                    }
                }
            };


    private EventHandler<KeyEvent> onKeyPressedHandler = event -> {
        pressedKeys.add(event.getCode());
        if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.C) && pressedKeys.size() == 2) {
            copiedShapes.clear();
            copiedShapes.addAll(shapesInFocus);
        }
    };

    private EventHandler<KeyEvent> onKeyReleasedHandler = event -> {
        if (event.getCode() == KeyCode.DELETE) {
            int numUndos = 0;
            for (ShapeFX shape : shapesInFocus) {
                command(true, remove, shape);
                numUndos++;
            }
            this.numUndos.push(numUndos);
        }
        if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.V) && pressedKeys.size() == 2) {
            int numUndos = 0;
            List<ShapeFX> newCopy = new ArrayList<>();
            for (ShapeFX shape : copiedShapes) {
                try {
                    ShapeFX copy = (ShapeFX) shape.clone();
                    Point pos = copy.getPosition();
                    pos.x += 25;
                    pos.y += 25;
                    copy.setPosition(pos);
                    newCopy.add(copy);
                    command(true, add, copy);
                    numUndos++;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            copiedShapes.clear();
            copiedShapes.addAll(newCopy);
            this.numUndos.push(numUndos);
        }
        if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.Z) && pressedKeys.size() == 2) {
            command(true, undo);
        }
        if (pressedKeys.contains(KeyCode.CONTROL) && pressedKeys.contains(KeyCode.Z) && pressedKeys.contains(KeyCode.SHIFT)
                && pressedKeys.size() == 3) {
            command(true, true, undo);
        }
        pressedKeys.remove(event.getCode());
        layersController.setShapes(remoteControl.getShapes());

    };

    private void command(final boolean notifiable, final boolean neutralize, final Commands command,
                         final ShapeFX... shapes) {
        remoteControl.setCommand(CommandFactory.getCommand(command, shapes));
        if (neutralize) {
            remoteControl.neutralizeCommand();
        } else {
            remoteControl.fireCommand(notifiable);
        }
        remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
    }

    private void command(boolean notifiable, Commands command, final ShapeFX... shapes) {
        command(notifiable, false, command, shapes);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        remoteControl = new DrawingEngineRemoteControl();
        selectedControl = recControl;
        pressedKeys = new LinkedHashSet<>();
        shapesInFocus = new ArrayList<>();
        numUndos = new Stack<>();
        numRedos = new Stack<>();
        copiedShapes = new ArrayList<>();
        canvas_id.setOnMouseReleased(dragReleaseHandler);
        canvas_id.setOnMouseClicked(mouseClickedHandler);
        canvas_id.setOnMouseDragged(draggingHandler);
        canvas_id.setOnMousePressed(dragDetectedHandler);
        loadShapeButtons();
        toolbarController.addObserver(this);
        layersController.setCanvasHeight(canvas_id.getHeight());
        layersController.setCanvasWidth(canvas_id.getWidth());
        layersController.setPaintPresenter(this);
    }

    public void loadShapeButtons() {
        ArrayList<Class<? extends Shape>> classes =
                (ArrayList<Class<? extends Shape>>)
                        remoteControl.getRecieverSupportedShapes();
        buttonPane.getChildren().clear();
        int numRows = buttonPane.getRowConstraints().size();
        int i = 0;
        for (Class<? extends Shape> cls : classes) {
            Button btn = new Button();
            btn.setText(cls.getSimpleName().substring(0, 1));
            btn.setId(cls.getSimpleName() + "Control");
            btn.setOnAction(this::onControlClicked);
            btn.getStyleClass().add(cls.getSimpleName());
            buttonPane.addRow(numRows + i, btn);
            i++;
        }
    }

    @FXML
    void onActionClicked(ActionEvent event) {
        if (event.getSource() == moveAction) {
            selectedControl = moveAction;
            currentState = StateFactory.getState(moving);
            event.consume();
        }
    }

    @FXML
    void onControlClicked(ActionEvent event) {
        if (event.getSource() == undoControl && !numUndos.empty()) {
            currentState = StateFactory.getState(idle);
            int undoSteps = numUndos.pop();
            numRedos.push(undoSteps);
            for (int i = 0; i < undoSteps; i++) {
                remoteControl.setCommand(CommandFactory.getCommand(undo));
                remoteControl.fireCommand();
                remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
            }
            layersController.setShapes(remoteControl.getShapes());

        } else if (event.getSource() == redoControl && !numRedos.empty()) {
            currentState = StateFactory.getState(idle);
            int redoSteps = numRedos.pop();
            numUndos.push(redoSteps);
            for (int i = 0; i < redoSteps; i++) {
                remoteControl.setCommand(CommandFactory.getCommand(undo));
                remoteControl.neutralizeCommand();
                remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
            }
            layersController.setShapes(remoteControl.getShapes());


        } else {
            selectedControl = (Button) event.getSource();
            currentState = StateFactory.getState(drawing);
        }


    }

    @FXML
    void onMenuItemClicked(ActionEvent event) {
        if (event.getSource() == loadClass) {
            loadClassFromFile();
        }
        if (event.getSource() == saveFile) {
            saveEngineToFile();
        }
        if (event.getSource() == loadFile) {
            loadEngineFromFile();
        }
    }

    private void loadEngineFromFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open");
        File file = chooser.showOpenDialog(stage);
        remoteControl.setCommand(new LoadCommand(file.getPath()));
        remoteControl.fireCommand();
        remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
        layersController.setShapes(remoteControl.getShapes());
    }

    private void saveEngineToFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save");
        File file = chooser.showSaveDialog(stage);
        remoteControl.setCommand(new SaveCommand(file.getPath()));
        remoteControl.fireCommand();
    }

    private void loadClassFromFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load the class jar");
        File file = chooser.showOpenDialog(stage);
        List<Class> classes = ReflectionUtils.loadClassesFromJar(file);
        ReflectionInterfaceScanner<Shape> scanner = new
                ReflectionInterfaceScanner<>(Shape.class);
        List<Class<? extends Shape>> shapesClasses = scanner
                .loadClassesFrom(classes);
        ShapeFactory.injectShape(shapesClasses);
        remoteControl.setCommand(new AddSupportedShapeCommand(shapesClasses));
        remoteControl.fireCommand();
        loadShapeButtons();
    }

    private void selectShape(final double x, final double y,
                             boolean uniqueSelection) {
        if (uniqueSelection) shapesInFocus.clear();
        ShapeFX shapeBounding = remoteControl.getShapeBounding(new Point((int) x, (int) y));
        if (!shapesInFocus.contains(shapeBounding)) shapesInFocus.add(shapeBounding);
        toolbarController.updateToolbar(shapesInFocus);

    }

    public void selectShape(ShapeFX shape) {
        if (!pressedKeys.contains(KeyCode.CONTROL)) shapesInFocus.clear();
        if (!shapesInFocus.contains(shape)) shapesInFocus.add(shape);
        toolbarController.updateToolbar(shapesInFocus);
    }

    private void createShape(final Point startPosition) {
        ShapeFX shape = getShape();
        shape.setPosition(startPosition);
        setShapeInFocus(shape);
        toolbarController.updateToolbar(shape);
        remoteControl.setCommand(CommandFactory.getCommand(add, shape));
        remoteControl.fireCommand(false);
        remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
    }

    private ShapeFX getShape() {
        return ShapeFactory.getShape(selectedControl.getId().replace("Control", ""));
    }

    public void notify(ShapeFX oldShape, ShapeFX newShape, boolean notifiable) {
        remoteControl.setCommand(CommandFactory.getCommand(update, oldShape, newShape));
        remoteControl.fireCommand(notifiable);
        remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
    }


    @Override
    public void notify(final List<ShapeFX> focusedShapes) {
        notify(focusedShapes, true);
    }

    private void notify(final List<ShapeFX> newShapes, boolean notifiable) {
        int i;
        for (i = 0; i < newShapes.size(); i++) {
            notify(shapesInFocus.get(i), newShapes.get(i), notifiable);
        }
        if (i > 0) {
            numUndos.push(i);
            numRedos.clear();
            layersController.setShapes(remoteControl.getShapes());
        }
        this.shapesInFocus.clear();
        this.shapesInFocus.addAll(newShapes);
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    public void setShapeInFocus(final ShapeFX shapeInFocus) {
        this.shapesInFocus.clear();
        this.shapesInFocus.add(shapeInFocus);
    }

    public EventHandler<KeyEvent> getOnKeyPressedHandler() {
        return onKeyPressedHandler;
    }

    public EventHandler<KeyEvent> getOnKeyReleasedHandler() {
        return onKeyReleasedHandler;
    }

    public void refresh() {
        remoteControl.refreshReceiver(canvas_id.getGraphicsContext2D());
    }
}
