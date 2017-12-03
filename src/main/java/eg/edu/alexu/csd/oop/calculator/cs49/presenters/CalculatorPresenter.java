package eg.edu.alexu.csd.oop.calculator.cs49.presenters;

import eg.edu.alexu.csd.oop.calculator.Calculator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorPresenter implements EventHandler<KeyEvent>, Initializable {
    @FXML
    private Button btn_division;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuLoad;

    @FXML
    private MenuItem menuClose;

    @FXML
    private Button btn_backspace;

    @FXML
    private Button btn_prev;

    @FXML
    private Button btn_next;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_6;

    @FXML
    private Button btn_5;

    @FXML
    private Button btn_1;

    @FXML
    private Button btn_multiply;

    @FXML
    private Button btn_4;

    @FXML
    private Button btn_3;

    @FXML
    private Button btn_9;

    @FXML
    private Button btn_8;

    @FXML
    private Button btn_point;

    @FXML
    private Button btn_7;

    @FXML
    private Button btn_enter;

    @FXML
    private Button btn_2;

    @FXML
    private Button btn_0;

    @FXML
    private Button btn_plus;

    @FXML
    private Button btn_minus;

    @FXML
    private Label lbl_screen;
    private Calculator calculator;


    public void handleButtonAction(final ActionEvent event) {
        try {
            if (event.getSource() == btn_0) {
                lbl_screen.setText(lbl_screen.getText() + "0");
            } else if (event.getSource() == btn_1) {
                lbl_screen.setText(lbl_screen.getText() + "1");
            } else if (event.getSource() == btn_2) {
                lbl_screen.setText(lbl_screen.getText() + "2");
            } else if (event.getSource() == btn_3) {
                lbl_screen.setText(lbl_screen.getText() + "3");
            } else if (event.getSource() == btn_4) {
                lbl_screen.setText(lbl_screen.getText() + "4");
            } else if (event.getSource() == btn_5) {
                lbl_screen.setText(lbl_screen.getText() + "5");
            } else if (event.getSource() == btn_6) {
                lbl_screen.setText(lbl_screen.getText() + "6");
            } else if (event.getSource() == btn_7) {
                lbl_screen.setText(lbl_screen.getText() + "7");
            } else if (event.getSource() == btn_8) {
                lbl_screen.setText(lbl_screen.getText() + "8");
            } else if (event.getSource() == btn_9) {
                lbl_screen.setText(lbl_screen.getText() + "9");
            } else if (event.getSource() == btn_plus) {
                lbl_screen.setText(lbl_screen.getText() + "+");
            } else if (event.getSource() == btn_minus) {
                lbl_screen.setText(lbl_screen.getText() + "-");
            } else if (event.getSource() == btn_multiply) {
                lbl_screen.setText(lbl_screen.getText() + "*");
            } else if (event.getSource() == btn_division) {
                lbl_screen.setText(lbl_screen.getText() + "/");
            } else if (event.getSource() == btn_enter) {
                calculator.input(lbl_screen.getText());
                lbl_screen.setText(calculator.getResult());
            } else if (event.getSource() == btn_clear) {
                lbl_screen.setText("");
            } else if (event.getSource() == btn_next) {
                setScreen(calculator.next());
            } else if (event.getSource() == btn_prev) {
                setScreen(calculator.prev());
            } else if (event.getSource() == btn_backspace) {
                removeOneCharfromScreen();
            }
        } catch (Exception e) {
            lbl_screen.setText("An error occurred");
        }
    }

    private void setScreen(final String formula) {
        if (formula != null) {
            lbl_screen.setText(formula);
        } else {
            lbl_screen.setText("");
        }
    }


    @FXML
    @Override
    public void handle(final KeyEvent event) {
        try {
            switch (event.getCharacter()) {
                case "0":
                    lbl_screen.setText(lbl_screen.getText() + "0");
                    break;
                case "1":
                    lbl_screen.setText(lbl_screen.getText() + "1");
                    break;
                case "2":
                    lbl_screen.setText(lbl_screen.getText() + "2");
                    break;
                case "3":
                    lbl_screen.setText(lbl_screen.getText() + "3");
                    break;
                case "4":
                    lbl_screen.setText(lbl_screen.getText() + "4");
                    break;
                case "5":
                    lbl_screen.setText(lbl_screen.getText() + "5");
                    break;
                case "6":
                    lbl_screen.setText(lbl_screen.getText() + "6");
                    break;
                case "7":
                    lbl_screen.setText(lbl_screen.getText() + "7");
                    break;
                case "8":
                    lbl_screen.setText(lbl_screen.getText() + "8");
                    break;
                case "9":
                    lbl_screen.setText(lbl_screen.getText() + "9");
                    break;
                case "+":
                    lbl_screen.setText(lbl_screen.getText() + "+");
                    break;
                case "/":
                    lbl_screen.setText(lbl_screen.getText() + "/");
                    break;
                case "*":
                    lbl_screen.setText(lbl_screen.getText() + "*");
                    break;
                case "-":
                    lbl_screen.setText(lbl_screen.getText() + "-");
                    break;
                case ".":
                    lbl_screen.setText(lbl_screen.getText() + ".");
                    break;
                case "\r":
                    calculator.input(lbl_screen.getText());
                    lbl_screen.setText(calculator.getResult());
                    break;
                case "\b":
                    removeOneCharfromScreen();
                    break;
            }
        } catch (Exception e) {
            lbl_screen.setText("An error Occured");
        }
    }

    private void removeOneCharfromScreen() {
        String screen = lbl_screen.getText();
        if (screen.length() <= 0) return;
        lbl_screen.setText(screen.substring(0, screen.length() - 1));

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        calculator = new eg.edu.alexu.csd.oop.calculator.cs49.models.Calculator();
    }


    public void menuItemClicked(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == menuClose) {
            System.exit(0);
        } else if (actionEvent.getSource() == menuLoad) {
            calculator.load();
        } else if (actionEvent.getSource() == menuSave) {
            calculator.save();
        }
    }
}
