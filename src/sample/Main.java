package sample;

import file.MyFile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Logic;
import objects.MyObject;
import objects.level.Level;
import objects.level.map.Brick;
import objects.level.map.Decor;
import objects.level.map.HWall;
import objects.level.map.VWall;
import objects.level.map.moovig_objects.characters.*;
import objects.level.map.moovig_objects.other_objects.*;
import objects.menu_objects.MyButton;
import objects.menu_objects.MyLabel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    static Logic logic = new Logic();
    ////////////////////////////////////////////////////////////////
    private static Scene mainMenuScene;
    //все кнопки в главном меню
    private static MyButton buttonStart,buttonSettings,buttonQuit,buttonLeft,buttonRight,buttonBack;
    //label в главном меню, музыка, звук
    private static MyLabel message, music, sound;
    //музыка
    private MediaPlayer musicPlayer;
    //ползунок для регулирования музыки
    private static Slider sliderMusic, sliderSound;
    private static double musicVolume = 0.05, soundVolume = 0.3;
    ////////////////////////////////////////////////////////////////
    private static Scene gameScene;
    private MainCharacter hero;
    private final ArrayList<String> input = new ArrayList<>();
    private AnimationTimer game;
    private Level currentLevel;
    private List<Level>levels;
    private Stage primaryStage;
    private boolean startNewLevel = true;
    private long levelStartTime;
    private long levelEndTime;
    private MyFile fileHero, fileSound, fileSave, fileStat;
    private boolean continueEnable = false;
    ////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("recourses/images/characters/Main character/skin42/down1.png"));
        primaryStage.setTitle("Pickman");
        //разворачивает приложение на весь экран
        primaryStage.setMaximized(true);
        //делает невозможным изменить размер окна
        primaryStage.setResizable(false);
        //убирает кнопки свернуть/максимизировать/закрыть
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();
        initFiles();
        gameScene = initGameScene();
        mainMenuScene = initMainScene();

        primaryStage.setScene(mainMenuScene);
    }

    private void initFiles() throws IOException {
        fileHero = new MyFile("hero");
        if(!fileHero.exists())
            fileHero.createNewFile();
        fileSound = new MyFile("snd");
        if(!fileSound.exists())
            fileSound.createNewFile();
        fileSave = new MyFile("save");
        if(!fileSave.exists())
            fileSave.createNewFile();
        if(fileSave.length() == 0)
            continueEnable = false;
        else
            continueEnable = true;
        fileStat = new MyFile("stat");
        if(!fileSave.exists())
            fileSave.createNewFile();
    }

    Scene initGameScene() {
        //hero
        hero = MainCharacter.getMainCharacter();
        hero.setSkin("/recourses/images/characters/Main character/skin1/down1.png");
        if(fileHero.exists()) {
            try {
                fileHero.readMainCharacter(hero);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initLevels();
        if(fileSave.length() == 0) {
            hero.setX(levels.get(0).getX());
            hero.setY(levels.get(0).getX());
            try {
                fileSave.writeCurrentLevel(levels.get(0), hero);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //из файла лвл
        if(fileSave.exists()) {
            try {
                currentLevel = fileSave.readCurrentLevel(levels,hero);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            currentLevel = levels.get(0);
            currentLevel.replaceHeroToThisLevel(hero);
        }
        //создаем сцену размером в окно
        Scene sc = new Scene(currentLevel.root(),primaryStage.getWidth(),primaryStage.getHeight());
        sc.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));
        sc.setFill(Color.BLACK);
        //задаем сцене нажатия кнопок
        EventHandler<KeyEvent> keyListenerPressed = event -> {
            if(event.getCode() == KeyCode.W) {
                String code = event.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
            }
            if(event.getCode() == KeyCode.S){
                String code = event.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
            }
            if(event.getCode() == KeyCode.D){
                String code = event.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
            }
            if(event.getCode() == KeyCode.A){
                String code = event.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
            }
            event.consume();
        };
        EventHandler<KeyEvent> keyListenerReleased = event -> {
            if(event.getCode() == KeyCode.W) {
                String code = event.getCode().toString();
                input.remove(code);
            }
            if(event.getCode() == KeyCode.S){
                String code = event.getCode().toString();
                input.remove(code);
            }
            if(event.getCode() == KeyCode.D){
                String code = event.getCode().toString();
                input.remove(code);
            }
            if(event.getCode() == KeyCode.A){
                String code = event.getCode().toString();
                input.remove(code);
            }
            if(event.getCode() == KeyCode.SPACE && hero.getBullets() > 0)
            {
                hero.setBullets(hero.getBullets() - 1);
                currentLevel.setLabelFireball(String.valueOf(hero.getBullets()));
                String code = event.getCode().toString();
                input.remove(code);
                Fireball fireball;
                switch (hero.direction()){
                    case "up":
                        fireball = new Fireball("up");
                        fireball.setX(hero.getX());
                        fireball.setY(hero.getY());
                        break;
                    case "right":
                        fireball = new Fireball("right");
                        fireball.setX(hero.getX());
                        fireball.setY(hero.getY());
                        break;
                    case "left":
                        fireball = new Fireball("left");
                        fireball.setX(hero.getX());
                        fireball.setY(hero.getY());
                        break;
                    case "down":
                        fireball = new Fireball("down");
                        fireball.setX(hero.getX() - 15);
                        fireball.setY(hero.getY());
                        break;
                    default:
                        fireball = new Fireball("down");
                        break;
                }
                currentLevel.root().getChildren().add(fireball);
                currentLevel.addFireball(fireball);
            }
            event.consume();
        };
        sc.setOnKeyPressed(keyListenerPressed);
        sc.setOnKeyReleased(keyListenerReleased);

        initAnimation();
        return sc;
    }

    private double opacity = 0.99;

    private void initAnimation()
    {
        game = new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                if(hero.getHp() == 0){
                    loseTheGame();
                }
                opacity -= 0.01;
                for(MyLabel label : currentLevel.getLabels()) {
                    label.opacityProperty().set(opacity);
                }
                opacity += 0.01;
                if(startNewLevel){
                    levelStartTime = currentNanoTime;
                    startNewLevel = false;
                }
                levelEndTime = currentNanoTime;
                List<Enemy> enemies = currentLevel.getEnemies();
                List<MyObject>barriers = currentLevel.getBarriers();
                List<Upgrade>upgrades = currentLevel.getUpgrades();
                List<Fireball>fireballs = currentLevel.getFireballs();
                List<Coin>coins = currentLevel.getCoins();
                if(input.contains("W")) {
                    logic.moveUp(hero);
                    for (MyObject barrier : barriers){
                        if (barrier.intersects(hero))
                            hero.setY(hero.getY() + hero.getStep());
                    }
                }
                if(input.contains("A")){
                    logic.moveLeft(hero);
                    for(MyObject barrier : barriers) {
                        if (barrier.intersects(hero))
                            hero.setX(hero.getX() + hero.getStep());
                    }
                }
                if(input.contains("S")){
                    logic.moveDown(hero);
                    for(MyObject barrier : barriers) {
                        if (barrier.intersects(hero))
                            hero.setY(hero.getY() - hero.getStep());
                    }
                }
                if(input.contains("D")){
                    logic.moveRight(hero);
                    for(MyObject barrier : barriers) {
                        if (barrier.intersects(hero))
                            hero.setX(hero.getX() - hero.getStep());
                    }
                }
                if(enemies.isEmpty()){
                    nextLevel();
                }
                else {
                    for (Enemy enemy : enemies) {
                        if (enemy.intersects(hero) && enemy.isVisible()) {
                            if(!hero.isGotHit()) {      //если до текущего момента не получил хит
                                hero.getHit(soundVolume);
                                hero.setStartHit(currentNanoTime / 1000_000);
                                currentLevel.setLabelHp(String.valueOf(hero.getHp()));
                            }
                            else{
                                hero.setEndHit(currentNanoTime / 1000_000);
                                if((hero.getEndHit() - hero.getStartHit()) > hero.hitTime()){
                                    hero.endHit();
                                }
                            }
                        }
                        for (MyObject barier : barriers)
                            if (enemy.intersects(barier))
                                logic.changeMovingEnemy(enemy);
                        if(enemy.isVisible())
                            logic.movingEnemy(enemy);
                    }
                    for (Upgrade upgrade : upgrades) {
                        if (upgrade.intersects(hero)) {
                            upgrade.use(hero);
                            upgrade.setStartTimeUpgrade(currentNanoTime);
                            currentLevel.removeUpgrade(upgrade);
                        }
                        if(upgrade.isVisible()) {
                            logic.movingUpgrade(upgrade);
                        }
                    }
                    for (Upgrade upgrade : hero.getUpgrades()) {
                        logic.checkEndUpgrade(upgrade,hero,enemies,currentNanoTime);
                    }
                }
                for(Fireball fireball : fireballs){
                    for (Enemy enemy : enemies) {
                        if (enemy.intersects(fireball) && enemy.isVisible()) {
                            currentLevel.removeFireball(fireball);
                            enemy.hpDec();
                            if(enemy.getHp() == 0) {
                                currentLevel.removeEnemy(enemy);
                                hero.addOneKilledEnemy();
                            }
                        }
                    }
                    for(MyObject barrier : barriers){
                        if(fireball.intersects(barrier)) {
                            currentLevel.removeFireball(fireball);
                        }
                    }
                    logic.movingFireball(fireball);
                }
                if(coins.isEmpty()){
                    nextLevel();
                }
                else {
                    for (Coin coin : coins) {
                        if (coin.intersects(hero)) {
                            currentLevel.removeCoin(coin);
                            hero.setCollectedCoins(hero.getCollectedCoins() + 1);
                            hero.setBullets(hero.getBullets() + hero.getBulletsForCoin());
                            currentLevel.setLabelFireball(String.valueOf(hero.getBullets()));
                        }
                        if(coin.isVisible()) {
                            logic.movingCoin(coin);
                        }
                    }
                }
            }
        };
    }

    private void initLabelsInLevel(Level level){
        String text;
        MyLabel hp = new MyLabel("");
        hp.setMinWidth(100);
        hp.setMinHeight(Region.USE_PREF_SIZE);
        hp.setTextFill(Color.WHITE);
        text = String.valueOf(hero.getHp());
        hp.setText(text);
        hp.setPrefWidth(20 * text.length());
        hp.setLayoutX(primaryStage.getWidth() / 4 + 2 * 20);
        level.setLabelHp(hp);

        MyLabel fireballs = new MyLabel("");
        fireballs.setMinWidth(100);
        fireballs.setMinHeight(Region.USE_PREF_SIZE);
        fireballs.setTextFill(Color.WHITE);
        text = String.valueOf(hero.getBullets());
        fireballs.setPrefWidth(20 * text.length());
        fireballs.setText(text);
        fireballs.setLayoutX(primaryStage.getWidth() / 4 + primaryStage.getWidth() / 2 + 7 * 20);
        level.setLabelFireball(fireballs);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Scene initMainScene() {
        String musicFile = "src/recourses/sound/epic.mp3";
        /*if(Math.random() > 0.5)
            musicFile = "src/recourses/sound/opa chirik.mp3";
        else
            musicFile = "src/recourses/sound/opa chirik.mp3";*/
        Media sound_ = new Media(new File(musicFile).toURI().toString());
        musicPlayer = new MediaPlayer(sound_);
        musicPlayer.play();
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        Pane root = new Pane();
        Scene mainMenuScene = new Scene(root,primaryStage.getWidth(),primaryStage.getHeight());
        initBackground(root);
        initLabel(root);
        initSliders(root);
        initButtons(root);
        music.setVisible(false);
        sound.setVisible(false);
        musicPlayer.setVolume(musicVolume);
        mainMenuScene.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));
        return mainMenuScene;
    }

    private void initBackground(Pane root) {
        String imagePath = "/recourses/images/backgrounds/main menu background.png";
        Image image = new Image(getClass().getResource(imagePath).toExternalForm(),root.getWidth(),root.getHeight(),false,true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }

    private void initLabel(Pane root) {
        message = new MyLabel("");
        // Х - посередине экрана, Y - 1/6 экрана
        message.setCoords((primaryStage.getWidth() - message.getWidth()) / 2,primaryStage.getHeight() / 6);
        music = new MyLabel("Музыка");
        // Х - посередине экрана, Y - 1/6 экрана
        music.setCoords(primaryStage.getWidth() / 2 - 45,primaryStage.getHeight() / 3 - 50);
        sound = new MyLabel("Звук");
        // Х - посередине экрана, Y - 1/6 экрана
        sound.setCoords(primaryStage.getWidth() / 2 - 30,primaryStage.getHeight() / 3 + 50);
        root.getChildren().addAll(message, music, sound);
    }

    private void initButtons(Pane root) {
        initButtonStart(root);
        initButtonQuit(root);
        initButtonSettings(root);
        initButtonBack(root);
        initButtonsLeftRight(root);
        MyButton.setVolume(soundVolume);
    }

    private void initButtonQuit(Pane root) {
        buttonQuit = new MyButton("Выход");
        buttonQuit.setCoords((primaryStage.getWidth()/3 - buttonStart.getPrefWidth()) / 2 + 2 *primaryStage.getWidth()/3, primaryStage.getHeight() / 3 - buttonStart.getPrefHeight());
        buttonQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exit();
                changeButtons();
                message.setText("Вы уверены, что хотите выйти?");
                message.setPrefWidth(551.87988);
                message.setCoords((primaryStage.getWidth() - message.getPrefWidth()) / 2,message.getLayoutY());
                message.setVisible(true);
                buttonLeft.setText("Да");
                buttonRight.setText("Нет");
                buttonLeft.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        changeButtons();
                        message.setVisible(false);
                        primaryStage.close();
                    }
                });
                buttonRight.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        changeButtons();
                        message.setVisible(false);
                    }
                });
            }
        });
        root.getChildren().add(buttonQuit);
    }

    private void exit() {
        try {
            fileHero.writeMainCharacter(hero);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileSound.writeSound(musicVolume,soundVolume);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(continueEnable)
                fileSave.writeCurrentLevel(currentLevel,hero);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtonStart(Pane root) {
        buttonStart = new MyButton("Играть");
        buttonStart.setCoords((primaryStage.getWidth()/3 - buttonStart.getPrefWidth()) / 2, primaryStage.getHeight() / 3 - buttonStart.getPrefHeight());
        buttonStart.setOnAction(actionEvent -> {
            changeButtons();
            buttonLeft.setText("Продолжить");
            if(continueEnable)
                buttonLeft.setDisable(false);
            else
                buttonLeft.setDisable(true);
            buttonRight.setText("Новая игра");
            buttonLeft.setOnAction(actionEvent14 -> {
                changeButtons();
                //продолжить игру
                game.start();
                primaryStage.setScene(gameScene);
                for(Level level:levels){
                    level.setOnAction(primaryStage,mainMenuScene,game);
                }

            });
            buttonRight.setOnAction(actionEvent13 -> {
                buttonLeft.setDisable(false);
                message.setText("Вы уверены, что хотите начать новую игру?");
                message.setPrefWidth(791.83);
                message.setCoords((primaryStage.getWidth() - message.getPrefWidth()) / 2,message.getLayoutY());
                message.setVisible(true);
                buttonLeft.setText("Да");
                buttonRight.setText("Нет");
                buttonLeft.setOnAction(actionEvent1 -> {
                    changeButtons();
                    message.setVisible(false);
                    newGame();
                    game.start();
                });
                buttonRight.setOnAction(actionEvent12 -> {
                    changeButtons();
                    message.setVisible(false);
                });
            });
        });

        root.getChildren().add(buttonStart);
    }

    private void initButtonSettings(Pane root) {
        buttonSettings = new MyButton("Настройки");
        buttonSettings.setCoords((primaryStage.getWidth()/3 - buttonStart.getPrefWidth()) / 2 + primaryStage.getWidth()/3, primaryStage.getHeight() / 3 - buttonStart.getPrefHeight());
        buttonSettings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hideButtons();
                buttonBack.setVisible(true);
                sliderMusic.setVisible(true);
                sliderSound.setVisible(true);
                music.setVisible(true);
                sound.setVisible(true);
            }
        });
        root.getChildren().add(buttonSettings);
    }


    private void initButtonBack(Pane root) {
        buttonBack = new MyButton("Назад");
        buttonBack.setScaleX(buttonBack.getScaleX()/2);
        buttonBack.setScaleY(buttonBack.getScaleY()/2);
        buttonBack.setCoords(50, primaryStage.getHeight() - buttonBack.getPrefHeight() - 20);
        buttonBack.setVisible(false);
        buttonBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                hideAll();
                changeButtons();
            }
        });
        root.getChildren().add(buttonBack);
    }

    private void initButtonsLeftRight(Pane root) {
        buttonLeft = new MyButton("");
        buttonRight = new MyButton("");
        buttonLeft.setCoords((primaryStage.getWidth()/2 - buttonStart.getPrefWidth()) / 2, primaryStage.getHeight() / 3 - buttonStart.getPrefHeight());
        buttonRight.setCoords((primaryStage.getWidth()/2 - buttonStart.getPrefWidth()) / 2 + primaryStage.getWidth()/2, primaryStage.getHeight() / 3 - buttonStart.getPrefHeight());
        buttonLeft.setVisible(false);
        buttonRight.setVisible(false);
        root.getChildren().addAll(buttonLeft,buttonRight);
    }

    private void hideButtons() {
        buttonSettings.setVisible(false);
        buttonQuit.setVisible(false);
        buttonStart.setVisible(false);
        buttonLeft.setVisible(false);
        buttonRight.setVisible(false);
        buttonBack.setVisible(false);
    }

    private void changeButtons() {

        if(buttonStart.isVisible()) {
            buttonLeft.setVisible(true);
            buttonRight.setVisible(true);
            buttonSettings.setVisible(false);
            buttonStart.setVisible(false);
            buttonQuit.setVisible(false);
        }
        else {
            buttonLeft.setVisible(false);
            buttonRight.setVisible(false);
            buttonSettings.setVisible(true);
            buttonStart.setVisible(true);
            buttonQuit.setVisible(true);
        }
    }

    private void initSliders(Pane root) {
        try {
            Point2D point = fileSound.readSound();
            musicVolume = point.getX();
            soundVolume = point.getY();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sliderMusic = new Slider(0,1,musicVolume);
        sliderMusic.setPrefWidth(300);
        sliderMusic.setLayoutX(primaryStage.getWidth() / 2 - sliderMusic.getPrefWidth() / 2);
        sliderMusic.setLayoutY(primaryStage.getHeight() / 3);
        sliderMusic.setVisible(false);
        sliderMusic.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                musicPlayer.setVolume((Double) newValue);
                musicVolume = (double) newValue;
            }
        });

        sliderSound = new Slider(0,1,soundVolume);
        sliderSound.setPrefWidth(300);
        sliderSound.setLayoutX(primaryStage.getWidth() / 2 - sliderMusic.getPrefWidth() / 2);
        sliderSound.setLayoutY(primaryStage.getHeight() / 3 + 100);
        sliderSound.setVisible(false);
        sliderSound.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                soundVolume = (double) newValue;
                MyButton.setVolume(soundVolume);
            }
        });

        root.getChildren().addAll(sliderMusic,sliderSound);
    }

    private void hideSliders() {
        sliderSound.setVisible(false);
        sliderMusic.setVisible(false);
    }

    private void hideAll() {
        hideButtons();
        hideSliders();
        music.setVisible(false);
        sound.setVisible(false);

    }

    public static void main(String[] args) {
        launch(args);
    }

    void nextLevel(){
        game.stop();
        currentLevel.setLevelTime((levelEndTime-levelStartTime)/1_000_000_000);
        hero.setGameTime(hero.getGameTime() + currentLevel.getLevelTime());
        for(int i = 0; i < levels.size(); i++){
            if(currentLevel.id() == levels.get(i).id() && levels.size() == i + 1){
                winTheGame();
            }
            else if(currentLevel.id() == levels.get(i).id() && levels.size() != i + 1){
                i++;
                if(i < 5){
                    hero.setBulletsForCoin(5 - i);
                }

                Brick black = new Brick();
                black.setSkin("/recourses/images/black.png");
                black.setScale(10);
                currentLevel.root().getChildren().add(black);
                MyLabel label = new MyLabel("Следующий уровень");
                label.setScaleX(1.5);
                label.setScaleY(1.5);
                label.setTextFill(Color.WHITE);
                label.setCoords(primaryStage.getWidth() / 2 - 170, primaryStage.getHeight() / 2 - 90);
                MyLabel label1 = new MyLabel("(для продолжения нажмите ENTER)");
                label1.setTextFill(Color.WHITE);
                label1.setCoords(primaryStage.getWidth() / 2 - 310, primaryStage.getHeight() / 2);
                Pane pane = new Pane();
                pane.getChildren().addAll(black,label,label1);
                Scene scene = new Scene(pane,primaryStage.getWidth(),primaryStage.getHeight());
                scene.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));
                primaryStage.setScene(scene);
                EventHandler<KeyEvent> keyListenerPressed = event1234 -> {
                    if (event1234.getCode() == KeyCode.ENTER) {
                        primaryStage.setScene(gameScene);
                        game.start();
                        currentLevel.setLabelHp(String.valueOf(hero.getHp()));
                        currentLevel.setLabelFireball(String.valueOf(hero.getBullets()));
                    }
                };
                scene.setOnKeyPressed(keyListenerPressed);

                currentLevel = levels.get(i);
                currentLevel.replaceHeroToThisLevel(hero);
                currentLevel.setThisLevelToScene(gameScene);
                for(Coin tempCoin : currentLevel.getCoins()){
                    tempCoin.setVisible(true);
                }
                for(Enemy tempEnemy : currentLevel.getEnemies()){
                    tempEnemy.setVisible(true);
                }
                for(Upgrade tempUpgrade : currentLevel.getUpgrades()){
                    tempUpgrade.setVisible(true);
                }
            }
        }
        input.clear();
    }

    void newGame(){
        fileSave = new MyFile("save");
        if(!fileSave.exists()) {
            try {
                fileSave.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        continueEnable = true;
        hero.setBullets(0);
        hero.setCollectedCoins(0);
        hero.setKilledEnemies(0);
        hero.clearUpgrades();
        hero.setBulletsForCoin(5);
        hero.setGameTime(0);
        hero.setHp(3);
        levels.clear();
        initLevels();
        currentLevel = levels.get(0);
        currentLevel.replaceHeroToThisLevel(hero);
        currentLevel.setThisLevelToScene(gameScene);
        primaryStage.setScene(gameScene);
        for(Coin tempCoin : currentLevel.getCoins()){
            tempCoin.setVisible(true);
        }
        for(Enemy tempEnemy : currentLevel.getEnemies()){
            tempEnemy.setVisible(true);
        }
        for(Upgrade tempUpgrade : currentLevel.getUpgrades()){
            tempUpgrade.setVisible(true);
        }
    }

    void winTheGame(){
        input.removeAll(input);
        input.clear();

        Pane root = new Pane();

        Scene winScene = new Scene(root,mainMenuScene.getWidth(), mainMenuScene.getHeight());

        double x,y;

        initBackground(root);

        winScene.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));

        TextField textField = textField();
        MyButton button1 = new MyButton("Далее");
        root.getChildren().addAll(button1);
        button1.setOnAction(actionEvent -> {
            Pane rootNext = new Pane();

            Scene winSceneNext = new Scene(rootNext,mainMenuScene.getWidth(), mainMenuScene.getHeight());

            initBackground(rootNext);

            winSceneNext.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));

            MyButton button12 = new MyButton("Меню");
            MyButton button22 = new MyButton("Выход");
            rootNext.getChildren().addAll(button12,button22);

            button22.setOnAction(actionEvent51 -> {
                        newGame();
                        primaryStage.close();
                    }
            );

            try {
                fileStat.writeStatistic(hero, textField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }

            button12.setOnAction(actionEvent412 -> primaryStage.setScene(mainMenuScene));
            double d = primaryStage.getWidth()/4 - 100;
            double s = primaryStage.getHeight()*7/8;
            button12.setCoords(d,s);
            d += primaryStage.getWidth()/2;
            button22.setCoords(d,s);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setLayoutX(100);
            scrollPane.setLayoutY(100);
            scrollPane.setPrefSize(primaryStage.getWidth() - 200, primaryStage.getHeight() - 300);
            Pane interiorPane = new Pane();
            fileStat.readStatistic(interiorPane);
            scrollPane.setContent(interiorPane);

            rootNext.getChildren().addAll(scrollPane);

            primaryStage.setScene(winSceneNext);        //new Scene(interiorPane,primaryStage.getWidth(), primaryStage.getHeight())
        });
        x = primaryStage.getWidth()/2 - 100;
        y = primaryStage.getHeight()*7/8;
        button1.setCoords(x,y);
        //labels and text field
        root.getChildren().addAll(killedEnemiesLine(),collectedCoins(),victory(),gameTime(),enterName(),textField);
        primaryStage.setScene(winScene);

        hero.setBullets(0);
        hero.setCollectedCoins(0);
        hero.setKilledEnemies(0);
        hero.clearUpgrades();
        hero.setBulletsForCoin(5);
        hero.setGameTime(0);
        hero.setHp(3);
        levels.clear();
        initLevels();
    }

    void loseTheGame(){
        fileSave.delete();
        continueEnable = false;
        String code = KeyCode.W.toString();
        input.remove(code);
        code = KeyCode.S.toString();
        input.remove(code);
        code = KeyCode.D.toString();
        input.remove(code);
        code = KeyCode.A.toString();
        input.remove(code);

        hero.setBullets(0);
        hero.setCollectedCoins(0);
        hero.setKilledEnemies(0);
        hero.clearUpgrades();
        hero.setBulletsForCoin(5);
        hero.setGameTime(0);
        hero.setHp(3);
        levels.clear();
        initLevels();
        for(MyLabel label : currentLevel.getLabels()) {
            label.opacityProperty().set(opacity);
        }

        Pane root = new Pane();
        Scene winScene = new Scene(root,mainMenuScene.getWidth(), mainMenuScene.getHeight());
        double x,y;
        initBackground(root);
        winScene.setCursor(new ImageCursor(new Image(getClass().getResource( "/recourses/images/cursor.png").toExternalForm())));
        MyButton button1 = new MyButton("Меню");
        MyButton button2 = new MyButton("Выход");
        root.getChildren().addAll(button1,button2);
        button2.setOnAction(actionEvent -> {
                    try {
                        fileHero.writeMainCharacter(hero);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileSound.writeSound(musicVolume,soundVolume);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MyFile fileStat = new MyFile("stat");
                    if(fileStat.exists()){

                    }
                    else {
                        try {
                            fileStat.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    newGame();
                    primaryStage.close();
                }
        );
        button1.setOnAction(actionEvent -> primaryStage.setScene(mainMenuScene));
        x = primaryStage.getWidth()/4 - button1.getWidth();
        y = primaryStage.getHeight()*2/3;
        button2.setCoords(x,y);
        x += primaryStage.getWidth()/3;
        button1.setCoords(x,y);
        //label lose
        MyLabel labelLose = new MyLabel("");
        String str = "Вы проиграли";
        labelLose.setText(str);
        labelLose.setPrefWidth(str.length() * 20 + 20);
        labelLose.setLayoutX(primaryStage.getWidth()/2 - labelLose.getPrefWidth()/2);
        labelLose.setLayoutY(primaryStage.getHeight()*2/5);
        root.getChildren().add(labelLose);
        primaryStage.setScene(winScene);
    }

    MyLabel victory(){
        MyLabel label = new MyLabel("");
        String str = "Победа!";
        label.setText(str);
        label.setPrefWidth(str.length() * 20);
        label.setLayoutX(primaryStage.getWidth()/2 - label.getPrefWidth()/2);
        label.setLayoutY(primaryStage.getHeight()*1/10);
        return label;
    }

    MyLabel killedEnemiesLine(){
        MyLabel label = new MyLabel("");
        String str = "Врагов убито: " + hero.getKilledEnemies();
        label.setText(str);
        label.setPrefWidth(str.length() * 20);
        label.setLayoutX(primaryStage.getWidth()/2 - label.getPrefWidth()/2);
        label.setLayoutY(primaryStage.getHeight()*2/10);
        return label;
    }

    MyLabel collectedCoins(){
        MyLabel label = new MyLabel("");
        String str = "Собрано монет: " + hero.getCollectedCoins();
        label.setText(str);
        label.setPrefWidth(str.length() * 20);
        label.setLayoutX(primaryStage.getWidth()/2 - label.getPrefWidth()/2);
        label.setLayoutY(primaryStage.getHeight()*3/10);
        return label;
    }

    MyLabel gameTime(){
        MyLabel label = new MyLabel("");
        long
                min = hero.getGameTime() / 60 % 60,
                sec = hero.getGameTime() % 60;
        String str = "Время игры: " + String.format("%02d:%02d",  min, sec);;
        label.setText(str);
        label.setPrefWidth(str.length() * 20);
        label.setLayoutX(primaryStage.getWidth()/2 - label.getPrefWidth()/2);
        label.setLayoutY(primaryStage.getHeight()*4/10);
        return label;
    }

    MyLabel enterName(){
        MyLabel label = new MyLabel("");
        String str = "Введите имя";
        label.setText(str);
        label.setPrefWidth(str.length() * 20 + 20);
        label.setLayoutX(primaryStage.getWidth()/2 - label.getPrefWidth()/2);
        label.setLayoutY(primaryStage.getHeight()*5/9);
        return label;
    }

    TextField textField(){
        TextField textField = new TextField("Name");
        textField.setPrefWidth(150);
        textField.setLayoutX(primaryStage.getWidth()/2 - textField.getPrefWidth()/2);
        textField.setLayoutY(primaryStage.getHeight()*6/9);
        return textField;
    }

    private Level makeLevel1(){
        String wallImage = "/recourses/images/obstacles/brick3.png";
        String backgroundImage = "/recourses/images/backgrounds/map background1.png";
        Level level = new Level(primaryStage.getWidth(),primaryStage.getHeight(), 1,backgroundImage,wallImage, 50 * 3,50 * 7);
        initLabelsInLevel(level);
        //добавляем противников
        String imageEnemy1 = "/recourses/images/characters/Enemy/skin9/down1.png";
        String imageEnemy2 = "/recourses/images/characters/Enemy/skin4/down1.png";
        Enemy enemy = new Enemy(level,50 * 7,50 * 2, imageEnemy1);
        enemy.setSpeed(enemy.getSpeed() * 0.4);
        enemy.setHp(3);
        Enemy enemy1 = new Enemy(level,50 * 6,50 * 12, imageEnemy2);
        Enemy enemy2 = new Enemy(level,50 * 14,50 * 6, imageEnemy1);
        enemy1.setSpeed(enemy1.getSpeed() * 0.6);
        enemy1.setHp(4);
        enemy2.setSpeed(enemy2.getSpeed() * 0.4);
        enemy2.setHp(3);
        Enemy enemy3 = new Enemy(level,50 * 26,50 * 2, imageEnemy2);
        enemy3.setSpeed(enemy3.getSpeed() * 0.6);
        enemy3.setHp(4);
        Enemy enemy4 = new Enemy(level,50 * 23,50 * 7 , imageEnemy1);
        enemy4.setSpeed(enemy4.getSpeed() * 0.4);
        enemy4.setHp(3);
        Enemy enemy5 = new Enemy(level,50 * 23,50 * 12, imageEnemy2);
        enemy5.setSpeed(enemy5.getSpeed() * 0.6);
        enemy5.setHp(4);
        Enemy enemy6 = new Enemy(level,50 * 28,50 * 9,imageEnemy1);
        enemy6.setSpeed(enemy6.getSpeed() * 0.4);
        enemy6.setHp(3);
        Enemy enemy7 = new Enemy(level,50 * 10,50 * 11,imageEnemy1);
        enemy7.setSpeed(enemy7.getSpeed() * 0.4);
        enemy7.setHp(3);
        //стены
        VWall wall1 = new VWall(level,wallImage,5, 50 * 3, 50);
        VWall wall2 = new VWall(level,wallImage,4, 50 * 6, 50 * 2);
        VWall wall3 = new VWall(level,wallImage,3, 50 * 9, 50);
        VWall wall4 = new VWall(level,wallImage,6, 50 * 8, 50 * 8);
        VWall wall5 = new VWall(level,wallImage,2, 50 * 11, 50 * 6);
        VWall wall6 = new VWall(level,wallImage,3, 50 * 11, 50 * 10);
        VWall wall7 = new VWall(level,wallImage,5, 50 * 16, 50 * 2);
        VWall wall8 = new VWall(level,wallImage,2, 50 * 19, 50 * 2);
        VWall wall9 = new VWall(level,wallImage,3, 50 * 23, 50);
        VWall wall10 = new VWall(level,wallImage,4, 50 * 20, 50 * 7);
        VWall wall11 = new VWall(level,wallImage,2, 50 * 26, 50 * 3);
        VWall wall12 = new VWall(level,wallImage,6, 50 * 25, 50 * 6);
        VWall wall13 = new VWall(level,wallImage,4, 50 * 28, 50 * 8);

        HWall wall01 = new HWall(level,wallImage,4,50 * 2, 50 * 8);
        HWall wall02 = new HWall(level,wallImage,6,50, 50 * 11);
        HWall wall03 = new HWall(level,wallImage,2,50 * 9, 50 * 6);
        HWall wall011 = new HWall(level,wallImage,3,50 * 10, 50 * 3);
        HWall wall04 = new HWall(level,wallImage,5,50 * 9, 50 * 8);
        HWall wall05 = new HWall(level,wallImage,6,50 * 12, 50 * 10);
        HWall wall06 = new HWall(level,wallImage,9,50 * 12, 50 * 12);
        HWall wall07 = new HWall(level,wallImage,8,50 * 17, 50 * 6);
        HWall wall08 = new HWall(level,wallImage,3,50 * 20, 50 * 3);
        HWall wall09 = new HWall(level,wallImage,2,50 * 21, 50 * 10);
        HWall wall010 = new HWall(level,wallImage,2,50 * 27, 50 * 3);
        HWall wall012 = new HWall(level,wallImage,2,50 * 23, 50 * 8);
        HWall wall013 = new HWall(level,wallImage,2,50 * 14, 50 * 5);
        //upgrades
        Boost boost = new Boost(level,50, 50 * 3);
        Boost boost1 = new Boost(level,50 * 7, 50 * 12);
        Boost boost3 = new Boost(level,50 * 22, 50 * 4);
        Life life = new Life(level, 50 * 17, 50 * 5);
        Freeze freeze = new Freeze(level, 50 * 16, 50 * 8);
        Freeze freeze1 = new Freeze(level, 50 * 29, 50 * 11);
        //decor
        Decor decor = new Decor(level,50 * 19, 50, "/recourses/images/obstacles/flower.png");
        //Decor decor1 = new Decor(level,50 * 13, 50 * 9, "/recourses/images/obstacles/stone3.png");
        //coins
        Coin coin = new Coin(level,50 * 2, 50);
        Coin coin1 = new Coin(level,50 * 8, 50);
        Coin coin2 = new Coin(level,50 * 10, 50);
        Coin coin3 = new Coin(level,50 * 29, 50);
        Coin coin4 = new Coin(level,50 * 22, 50 * 2);
        Coin coin5 = new Coin(level,50 * 25, 50 * 4);
        Coin coin6 = new Coin(level,50 * 19, 50 * 7);
        Coin coin7 = new Coin(level,50 * 24, 50 * 7);
        Coin coin8 = new Coin(level,50 * 9, 50 * 9);
        Coin coin9 = new Coin(level,50 * 29, 50 * 9);
        Coin coin10 = new Coin(level,50 * 12, 50 * 11);
        Coin coin11 = new Coin(level, 50, 50 * 13);
        Coin coin12 = new Coin(level,50 * 18, 50 * 13);
        for(Coin tempCoin : level.getCoins()){
            tempCoin.setVisible(false);
        }
        for(Enemy tempEnemy : level.getEnemies()){
            tempEnemy.setVisible(false);
        }
        for(Upgrade tempUpgrade : level.getUpgrades()){
            tempUpgrade.setVisible(false);
        }
        return level;
    }

    public Level makeLevel2(){
        String wallImage = "/recourses/images/obstacles/brick2.png";
        String backgroundImage = "/recourses/images/backgrounds/map background2.png";
        Level level = new Level(primaryStage.getWidth(), primaryStage.getHeight(),2, backgroundImage,wallImage,50 * 12,50 * 3);
        initLabelsInLevel(level);
        //добавляем противников
        String imageEnemy1 = "/recourses/images/characters/Enemy/skin9/down1.png";
        String imageEnemy2 = "/recourses/images/characters/Enemy/skin4/down1.png";
        String imageEnemy3 = "/recourses/images/characters/Enemy/skin5/down1.png";
        Enemy enemy = new Enemy(level,50 * 3,50, imageEnemy1);
        enemy.setSpeed(enemy.getSpeed() * 0.4);
        enemy.setHp(3);
        Enemy enemy1 = new Enemy(level,50 * 5,50 * 12, imageEnemy2);
        enemy1.setSpeed(enemy1.getSpeed() * 0.6);
        enemy1.setHp(4);
        Enemy enemy2 = new Enemy(level,50 * 15,50 * 11, imageEnemy3);
        enemy2.setSpeed(enemy2.getSpeed() * 0.3);
        enemy2.setHp(6);
        Enemy enemy3 = new Enemy(level,50 * 21,50 * 3, imageEnemy2);
        enemy3.setSpeed(enemy3.getSpeed() * 0.6);
        enemy3.setHp(4);
        Enemy enemy4 = new Enemy(level,50 * 23,50 * 10, imageEnemy2);
        enemy4.setSpeed(enemy4.getSpeed() * 0.6);
        enemy4.setHp(4);
        Enemy enemy5 = new Enemy(level,50 * 8,50 * 4, imageEnemy3);
        enemy5.setSpeed(enemy5.getSpeed() * 0.3);
        enemy5.setHp(6);
        Enemy enemy6 = new Enemy(level,50 * 26,50 * 7, imageEnemy2);
        enemy6.setSpeed(enemy6.getSpeed() * 0.6);
        enemy6.setHp(4);
        Enemy enemy7 = new Enemy(level,50 * 8,50 * 9, imageEnemy1);
        enemy7.setSpeed(enemy7.getSpeed() * 0.4);
        enemy7.setHp(3);
        Enemy enemy8 = new Enemy(level,50 * 2,50 * 7, imageEnemy2);
        enemy8.setSpeed(enemy8.getSpeed() * 0.6);
        enemy8.setHp(4);
        Enemy enemy9 = new Enemy(level,50 * 19,50 * 13, imageEnemy3);
        enemy9.setSpeed(enemy9.getSpeed() * 0.3);
        enemy9.setHp(6);
        Enemy enemy10 = new Enemy(level,50 * 17,50 * 8, imageEnemy3);
        enemy10.setSpeed(enemy10.getSpeed() * 0.3);
        enemy10.setHp(6);
        //стены
        VWall wall1 = new VWall(level,wallImage,2, 50 * 6, 50);
        VWall wall2 = new VWall(level,wallImage,3, 50 * 15, 50);
        VWall wall3 = new VWall(level,wallImage,3, 50 * 19, 50);
        VWall wall4 = new VWall(level,wallImage,4, 50 * 26, 50 * 2);
        VWall wall5 = new VWall(level,wallImage,3, 50 * 3, 50 * 3);
        VWall wall6 = new VWall(level,wallImage,7, 50 * 7, 50 * 5);
        VWall wall7 = new VWall(level,wallImage,4, 50 * 17, 50 * 4);
        VWall wall8 = new VWall(level,wallImage,6, 50 * 10, 50 * 8);
        VWall wall9 = new VWall(level,wallImage,2, 50 * 13, 50 * 10);
        VWall wall10 = new VWall(level,wallImage,4, 50 * 17, 50 * 10);
        VWall wall11 = new VWall(level,wallImage,4, 50 * 22, 50 * 9);
        VWall wall12 = new VWall(level,wallImage,3, 50 * 27, 50 * 9);
        VWall wall13 = new VWall(level,wallImage,5, 50 * 19, 50 * 7);

        HWall wall01 = new HWall(level,wallImage,3,50 * 7, 50 * 2);
        HWall wall02 = new HWall(level,wallImage,2,50 * 20, 50);
        HWall wall03 = new HWall(level,wallImage,4,50 * 22, 50 * 4);
        HWall wall04 = new HWall(level,wallImage,3,50 * 4, 50 * 5);
        HWall wall05 = new HWall(level,wallImage,9,50 * 8, 50 * 6);
        HWall wall06 = new HWall(level,wallImage,2,50 * 18, 50 * 6);
        HWall wall07 = new HWall(level,wallImage,3,50 * 21, 50 * 6);
        HWall wall08 = new HWall(level,wallImage,4,50, 50 * 9);
        HWall wall09 = new HWall(level,wallImage,5,50 * 3, 50 * 11);
        HWall wall010 = new HWall(level,wallImage,5,50 * 11, 50 * 8);
        HWall wall011 = new HWall(level,wallImage,2,50 * 26, 50 * 11);
        HWall wall012 = new HWall(level,wallImage,3,50 * 14, 50 * 10);

        //upgrades
        //Boost boost = new Boost(level,50, 50 * 3);
        //decor
        //Decor decor = new Decor(level,50 * 19, 50, "/recourses/images/obstacles/stone1.png");
        //Decor decor1 = new Decor(level,50 * 13, 50 * 9, "/recourses/images/obstacles/stone3.png");
        //coins
        Coin coin = new Coin(level,50 * 5, 50);
        Coin coin1 = new Coin(level,50 * 4, 50 * 6);
        Coin coin2 = new Coin(level,50 * 6, 50 * 10);
        Coin coin3 = new Coin(level,50 * 5, 50 * 13);
        Coin coin4 = new Coin(level,50 * 13, 50 * 7);
        Coin coin5 = new Coin(level,50 * 11, 50 * 9);
        Coin coin6 = new Coin(level,50 * 13, 50 * 12);
        Coin coin7 = new Coin(level,50 * 16, 50 * 11);
        Coin coin8 = new Coin(level,50 * 18, 50 * 7);
        Coin coin9 = new Coin(level,50 * 21, 50 * 5);
        Coin coin10 = new Coin(level,50 * 25, 50 * 3);
        Coin coin11 = new Coin(level, 50 * 26, 50 * 10);
        Coin coin12 = new Coin(level,50 * 28, 50 * 13);
        for(Coin tempCoin : level.getCoins()){
            tempCoin.setVisible(false);
        }
        for(Enemy tempEnemy : level.getEnemies()){
            tempEnemy.setVisible(false);
        }
        for(Upgrade tempUpgrade : level.getUpgrades()){
            tempUpgrade.setVisible(false);
        }
        return level;
    }

    public Level makeLevel3(){
        String imageEnemy2 = "/recourses/images/characters/Enemy/skin4/down1.png";
        String imageEnemy3 = "/recourses/images/characters/Enemy/skin5/down1.png";
        String imageEnemy4 = "/recourses/images/characters/Enemy/skin2/down1.png";
        String wallImage = "/recourses/images/obstacles/brick1.png";
        String backgroundImage = "/recourses/images/backgrounds/map background3.png";
        Level level = new Level(primaryStage.getWidth(), primaryStage.getHeight(),3,backgroundImage,wallImage,50 * 14,50 * 7);
        initLabelsInLevel(level);
        //добавляем противников
        Enemy enemy = new Enemy(level,50 * 2,50 * 3, imageEnemy4);
        enemy.setSpeed(enemy.getSpeed() * 0.8);
        enemy.setHp(7);
        Enemy enemy1 = new Enemy(level,50 * 5,50 * 8, imageEnemy2);
        enemy1.setSpeed(enemy1.getSpeed() * 0.6);
        enemy1.setHp(4);
        Enemy enemy2 = new Enemy(level,50 * 5,50 * 11, imageEnemy3);
        enemy2.setSpeed(enemy2.getSpeed() * 0.3);
        enemy2.setHp(6);
        Enemy enemy3 = new Enemy(level,50 * 9,50 * 6, imageEnemy4);
        enemy3.setSpeed(enemy3.getSpeed() * 0.8);
        enemy3.setHp(7);
        Enemy enemy4 = new Enemy(level,50 * 12,50 * 2, imageEnemy4);
        enemy4.setSpeed(enemy4.getSpeed() * 0.8);
        enemy4.setHp(7);
        Enemy enemy5 = new Enemy(level,50 * 9,50 * 13, imageEnemy3);
        enemy5.setSpeed(enemy5.getSpeed() * 0.3);
        enemy5.setHp(6);
        Enemy enemy6 = new Enemy(level,50 * 11,50 * 10, imageEnemy2);
        enemy6.setSpeed(enemy6.getSpeed() * 0.6);
        enemy6.setHp(4);
        Enemy enemy7 = new Enemy(level,50 * 18,50 * 4, imageEnemy4);
        enemy7.setSpeed(enemy7.getSpeed() * 0.8);
        enemy7.setHp(7);
        Enemy enemy8 = new Enemy(level,50 * 20,50, imageEnemy2);
        enemy8.setSpeed(enemy8.getSpeed() * 0.6);
        enemy8.setHp(4);
        Enemy enemy9 = new Enemy(level,50 * 20,50 * 9, imageEnemy3);
        enemy9.setSpeed(enemy9.getSpeed() * 0.3);
        enemy9.setHp(6);
        Enemy enemy10 = new Enemy(level,50 * 24,50 * 12, imageEnemy4);
        enemy10.setSpeed(enemy10.getSpeed() * 0.8);
        enemy10.setHp(7);
        Enemy enemy11 = new Enemy(level,50 * 25,50, imageEnemy4);
        enemy11.setSpeed(enemy11.getSpeed() * 0.8);
        enemy11.setHp(7);
        Enemy enemy12 = new Enemy(level,50 * 25,50 * 6, imageEnemy4);
        enemy12.setSpeed(enemy12.getSpeed() * 0.8);
        enemy12.setHp(7);
        Enemy enemy13 = new Enemy(level,50 * 26,50 * 9, imageEnemy4);
        enemy13.setSpeed(enemy13.getSpeed() * 0.8);
        enemy13.setHp(7);

        //стены
        VWall wall1 = new VWall(level,wallImage,2, 50 * 3, 50 * 4);
        VWall wall2 = new VWall(level,wallImage,1, 50 * 4, 50 * 5);
        VWall wall3 = new VWall(level,wallImage,5, 50 * 5, 50 * 2);
        VWall wall4 = new VWall(level,wallImage,2, 50 * 2, 50 * 7);
        VWall wall5 = new VWall(level,wallImage,2, 50 * 4, 50 * 9);
        VWall wall6 = new VWall(level,wallImage,2, 50 * 5, 50 * 12);
        VWall wall7 = new VWall(level,wallImage,6, 50 * 7, 50 * 7);
        VWall wall8 = new VWall(level,wallImage,4, 50 * 7, 50);
        VWall wall9 = new VWall(level,wallImage,2, 50 * 10, 50 * 4);
        VWall wall10 = new VWall(level,wallImage,1, 50 * 11, 50 * 5);
        VWall wall11 = new VWall(level,wallImage,5, 50 * 12, 50 * 5);
        VWall wall12 = new VWall(level,wallImage,3, 50 * 9, 50 * 9);
        VWall wall13 = new VWall(level,wallImage,2, 50 * 14, 50);
        VWall wall14 = new VWall(level,wallImage,2, 50 * 15, 50);
        VWall wall15 = new VWall(level,wallImage,2, 50 * 15, 50 * 8);
        VWall wall16 = new VWall(level,wallImage,2, 50 * 16, 50 * 5);
        VWall wall17 = new VWall(level,wallImage,2, 50 * 17, 50 * 5);
        VWall wall18 = new VWall(level,wallImage,6, 50 * 19, 50 * 3);
        VWall wall19 = new VWall(level,wallImage,3, 50 * 21, 50);
        VWall wall20 = new VWall(level,wallImage,2, 50 * 17, 50 * 11);
        VWall wall21 = new VWall(level,wallImage,3, 50 * 21, 50 * 8);
        VWall wall22 = new VWall(level,wallImage,2, 50 * 22, 50 * 7);
        VWall wall23 = new VWall(level,wallImage,2, 50 * 24, 50 * 3);
        VWall wall24 = new VWall(level,wallImage,2, 50 * 24, 50 * 7);
        VWall wall25 = new VWall(level,wallImage,2, 50 * 25, 50 * 11);
        VWall wall26 = new VWall(level,wallImage,1, 50 * 23, 50 * 13);
        VWall wall27 = new VWall(level,wallImage,3, 50 * 27, 50 * 11);
        VWall wall28 = new VWall(level,wallImage,2, 50 * 26, 50 * 3);
        VWall wall29 = new VWall(level,wallImage,2, 50 * 29, 50 * 4);
        VWall wall30 = new VWall(level,wallImage,3, 50 * 14, 50 * 4);
        VWall wall31 = new VWall(level,wallImage,1, 50 * 6, 50 * 8);

        HWall wall01 = new HWall(level,wallImage,4,50, 50 * 2);
        HWall wall02 = new HWall(level,wallImage,4,50 * 3, 50 * 7);
        HWall wall03 = new HWall(level,wallImage,3,50, 50 * 10);
        HWall wall04 = new HWall(level,wallImage,3,50 * 2, 50 * 12);
        HWall wall05 = new HWall(level,wallImage,2,50 * 8, 50 * 7);
        HWall wall06 = new HWall(level,wallImage,4,50 * 8, 50 * 2);
        HWall wall07 = new HWall(level,wallImage,2,50 * 10, 50 * 9);
        HWall wall08 = new HWall(level,wallImage,2,50 * 11, 50 * 12);
        HWall wall09 = new HWall(level,wallImage,3,50 * 12, 50 * 11);
        HWall wall010 = new HWall(level,wallImage,2,50 * 14, 50 * 10);
        HWall wall011 = new HWall(level,wallImage,3,50 * 13, 50 * 3);
        HWall wall012 = new HWall(level,wallImage,2,50 * 17, 50 * 2);
        HWall wall013 = new HWall(level,wallImage,3,50 * 16, 50 * 3);
        HWall wall014 = new HWall(level,wallImage,2,50 * 17, 50 * 8);
        HWall wall015 = new HWall(level,wallImage,3,50 * 15, 50 * 13);
        HWall wall016 = new HWall(level,wallImage,4,50 * 18, 50 * 11);
        HWall wall017 = new HWall(level,wallImage,3,50 * 19, 50 * 12);
        HWall wall018 = new HWall(level,wallImage,5,50 * 20, 50 * 5);
        HWall wall019 = new HWall(level,wallImage,2,50 * 23, 50 * 2);
        HWall wall020 = new HWall(level,wallImage,4,50 * 22, 50 * 10);
        HWall wall021 = new HWall(level,wallImage,4,50 * 26, 50 * 2);
        HWall wall022 = new HWall(level,wallImage,3,50 * 25, 50 * 7);
        HWall wall023 = new HWall(level,wallImage,3,50 * 27, 50 * 9);
        HWall wall024 = new HWall(level,wallImage,2,50 * 7, 50 * 5);

        //upgrades
        Boost boost1 = new Boost(level,50 * 15, 50 * 11);
        Boost boost2 = new Boost(level,50 * 4, 50 * 11);
        Boost boost3 = new Boost(level,50 * 19, 50 * 2);
        Life life = new Life(level, 50 * 3, 50 * 6);
        Freeze freeze1 = new Freeze(level, 50 * 25, 50 * 9);
        Freeze freeze2 = new Freeze(level, 50 * 6, 50 * 4);
        Freeze freeze3 = new Freeze(level, 50 * 24, 50);
        //decor
        //Decor decor = new Decor(level,50 * 19, 50, "/recourses/images/obstacles/stone1.png");
        //Decor decor1 = new Decor(level,50 * 13, 50 * 9, "/recourses/images/obstacles/stone3.png");
        //coins
        Coin coin = new Coin(level,50, 50);
        Coin coin1 = new Coin(level,50 * 4, 50 * 4);
        Coin coin2 = new Coin(level,50 * 4, 50 * 6);
        Coin coin3 = new Coin(level,50 * 4, 50 * 13);
        Coin coin4 = new Coin(level,50 * 8, 50);
        Coin coin5 = new Coin(level,50 * 11, 50 * 8);
        Coin coin6 = new Coin(level,50 * 16, 50 * 2);
        Coin coin7 = new Coin(level,50 * 14, 50 * 12);
        Coin coin8 = new Coin(level,50 * 18, 50 * 12);
        Coin coin9 = new Coin(level,50 * 22, 50 * 9);
        Coin coin10 = new Coin(level,50 * 23, 50 * 4);
        Coin coin11 = new Coin(level, 50 * 27, 50);
        Coin coin12 = new Coin(level,50 * 28, 50 * 13);
        for(Coin tempCoin : level.getCoins()){
            tempCoin.setVisible(false);
        }
        for(Enemy tempEnemy : level.getEnemies()){
            tempEnemy.setVisible(false);
        }
        for(Upgrade tempUpgrade : level.getUpgrades()){
            tempUpgrade.setVisible(false);
        }
        return level;
    }

    void initLevels(){
        levels = new ArrayList<>();
        levels.add(makeLevel1());
        levels.add(makeLevel2());
        levels.add(makeLevel3());

        for(Level level:levels){
            level.setOnAction(primaryStage,mainMenuScene,game);
        }
    }
}