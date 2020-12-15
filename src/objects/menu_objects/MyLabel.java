package objects.menu_objects;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyLabel extends Label {

    private final String fontPath = "src/recourses/fonts/font2.ttf";
    private int fontSize = 30;

    public MyLabel(String text) {
        setText(text);
        try {
            setFont(Font.loadFont(new FileInputStream(fontPath),fontSize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",fontSize));
        }
    }

    public void setCoords(double X, double Y) {
        setLayoutX(X);
        setLayoutY(Y);
    }

}
