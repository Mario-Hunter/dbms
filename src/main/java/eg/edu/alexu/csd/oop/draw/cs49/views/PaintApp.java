package eg.edu.alexu.csd.oop.draw.cs49.views;

import eg.edu.alexu.csd.oop.draw.cs49.presenters.PaintPresenter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PaintApp extends Application {


    private Set<KeyCode> pressedKeys;

    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage primaryStage) throws IOException, ExecutionException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("dynamicPaint.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);

       ((PaintPresenter)loader.getController()).setStage(primaryStage);
        scene.setOnKeyPressed(((PaintPresenter) loader.getController()).getOnKeyPressedHandler());
        scene.setOnKeyReleased(((PaintPresenter) loader.getController()).getOnKeyReleasedHandler());


        primaryStage.show();
    }


}
