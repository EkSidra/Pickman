package objects.menu_objects;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyButton extends Button {

    private final String fontPath = "src/recourses/fonts/font2.ttf";
    private final String usualStyle = "-fx-background-color: transparent; -fx-background-image: url('/recourses/images/buttons/buttonLarge.png')";
    private final String pressedStyle = "-fx-background-color: transparent; -fx-background-image: url('/recourses/images/buttons/buttonLarge2.png')";
    private final String mouseEntered = "-fx-background-color: transparent; -fx-background-image: url('/recourses/images/buttons/buttonLarge3.png')";;
    private int fontSize = 21;
    private static double soundVolume;

    public static void setVolume(double value) {
        soundVolume = value;
    }

    public MyButton(String text) {
        setText(text);
        try {
            setFont(Font.loadFont(new FileInputStream(fontPath),fontSize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",fontSize));
        }
        setPrefSize(196,70);
        setStyle(usualStyle);
        buttonListeners();
    }

    private void buttonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(pressedStyle);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(usualStyle);
                String musicFile;
                musicFile = "src/recourses/sound/click.wav";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(mouseEntered);
                setEffect(new DropShadow());
                String musicFile = "src/recourses/sound/mouseEntered.mp3";
                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(soundVolume);
                mediaPlayer.play();
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStyle(usualStyle);
                setEffect(null);
            }
        });
    }

    public void setCoords(double X, double Y) {
        setLayoutX(X);
        setLayoutY(Y);
    }

}
