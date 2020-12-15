package objects.level;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.MyObject;
import objects.level.map.Brick;
import objects.level.map.HWall;
import objects.level.map.VWall;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;
import objects.level.map.moovig_objects.other_objects.Coin;
import objects.level.map.moovig_objects.other_objects.Fireball;
import objects.level.map.moovig_objects.other_objects.Upgrade;
import objects.menu_objects.MyButton;
import objects.menu_objects.MyLabel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level extends MyObject {
    private List<Enemy> enemies;
    private List<MyObject> barriers;
    private List<Upgrade> upgrades;
    private List<Fireball> fireballs;
    private List<Coin> coins;
    private List<MyLabel> labels;
    private Pane root;
    private double heroX;
    private double heroY;
    private long levelTime;
    private MyLabel labelFireball;
    private MyLabel labelHp;
    private MyButton menuButton;
    private int number;     //number of level

    public Level(double width, double height, int numberOfLevel, String backgroundImage, String wallImage, double startXForHero, double startYForHero) {
        enemies = new ArrayList<>();
        barriers = new ArrayList<>();
        upgrades = new ArrayList<>();
        fireballs = new ArrayList<>();
        coins = new ArrayList<>();
        labels = new ArrayList<>();
        root = new Pane();
        //номер уровня
        setNumberOfLevel(numberOfLevel);
        //подгоняем изображение по длине и ширине экрана
        setFitWidth(width);
        setFitHeight(height * 5 / 6);
        //фон и кнопка нижней панели для всех уровней одинаковая
        double x,y;
        Brick backgroundMenuImage = new Brick();
        backgroundMenuImage.setSkin("/recourses/images/backgrounds/main menu background.png");
        x = 0;
        y = height * 1 / 6;
        backgroundMenuImage.setX(x);
        backgroundMenuImage.setY(y);
        backgroundMenuImage.setFitWidth(width);
        backgroundMenuImage.setFitHeight(height);
        root.getChildren().add(backgroundMenuImage);
        //фон
        setSkin(backgroundImage);
        root.getChildren().add(this);
        menuButton = new MyButton("меню");
        menuButton.setPrefHeight(70);
        menuButton.setPrefWidth(196);
        x = width / 2 - menuButton.getPrefWidth() / 2;
        y = height - (height * 1 / 6) / 3 - menuButton.getPrefHeight() / 2;
        menuButton.setCoords(x,y);
        menuButton.setFocusTraversable(false);
        root.getChildren().add(menuButton);
        //////////главные стены
        VWall wall1 = new VWall(this,wallImage,15,0,0);
        VWall wall2 = new VWall(this,wallImage,15,50 * 31,0);
        HWall wall3 = new HWall(this,wallImage,31,50,0);
        HWall wall4 = new HWall(this,wallImage,31,50,50 * 14);

        heroX = startXForHero;
        heroY = startYForHero;
        levelTime = 0;
        //строки сверху кажого уровня(хп и фаерболы)
        String text;
        MyLabel hp = new MyLabel("");
        hp.setTextFill(Color.WHITE);
        text = "hp = ";
        hp.setText(text);
        hp.setPrefWidth(20 * text.length());
        hp.setLayoutX(width / 4 - hp.getPrefWidth()/2);
        this.addLabel(hp);

        MyLabel fireballs = new MyLabel("");
        fireballs.setTextFill(Color.WHITE);
        text = "fireballs = ";
        fireballs.setPrefWidth(20*text.length());
        fireballs.setText(text);
        fireballs.setLayoutX(width / 4 + width / 2 - hp.getPrefWidth()/2);
        addLabel(fireballs);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void addBarrier(MyObject barrier) {
        barriers.add(barrier);
    }
    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }
    public void addFireball(Fireball fireball) {
        fireballs.add(fireball);
    }
    public void addCoins(Coin coin) {
        coins.add(coin);
    }

    public void removeEnemy(Enemy enemy) {
        enemy.setVisible(false);
        for(Enemy curEnemy : enemies)
            if(curEnemy.id() == enemy.id()) {
                enemies.remove(enemy);
                return;
            }
    }
    public void removeBarrier(MyObject barrier) {
        barrier.setVisible(false);
        for(MyObject curBarrier : barriers)
            if(curBarrier.id() == barrier.id()) {
                barriers.remove(barrier);
                root.getChildren().remove(barrier);
                return;
            }
    }
    public void removeUpgrade(Upgrade upgrade) {
        upgrade.setVisible(false);
        /*for(Upgrade curUpgrade : upgrades)
            if(curUpgrade.id() == upgrade.id()) {
                upgrades.remove(upgrade);
                root.getChildren().remove(upgrade);
                return;
            }*/
        Iterator iterator = upgrades.iterator();

        while (iterator.hasNext()) {
            Upgrade element = (Upgrade) iterator.next();
            if (element.id() == upgrade.id()) {
                iterator.remove();
            }
        }
    }
    public void removeFireball(Fireball fireball) {
        fireball.setVisible(false);
        for(Fireball curFireball : fireballs)
            if(curFireball.id() == fireball.id()) {
                fireballs.remove(fireball);
                root.getChildren().remove(fireball);
                return;
            }
    }
    public void removeCoin(Coin coin) {
        coin.setVisible(false);
        for(Coin curCoin : coins) {
            if (curCoin.id() == coin.id()) {
                coins.remove(coin);
                root.getChildren().remove(coin);
                return;
            }
        }
    }

    public void removeLabel(MyLabel label) {
        label.setVisible(false);
        for(MyLabel curLabel : labels)
            if(curLabel == label) {
                labels.remove(label);
                root.getChildren().remove(label);
                return;
            }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<MyObject> getBarriers() {
        return barriers;
    }
    public List<Upgrade> getUpgrades() {
        return upgrades;
    }
    public List<Fireball> getFireballs() {
        return fireballs;
    }
    public List<Coin> getCoins() {
        return coins;
    }
    public List<MyLabel> getLabels() { return labels; }

    public Pane root(){
        return root;
    }

    public void setThisLevelToScene(Scene scene){
        scene.setRoot(root);
    }

    public void replaceHeroToThisLevel(MainCharacter hero){
        if(!root.getChildren().contains(hero))
            root.getChildren().add(hero);
        hero.setX(heroX);
        hero.setY(heroY);
    }

    public long getLevelTime() {
        return levelTime;
    }

    public void setLevelTime(long levelTime) {
        this.levelTime = levelTime;
    }

    public void addLabel(MyLabel label) {
        labels.add(label);
        root.getChildren().add(label);
    }

    public void setLabelFireball(MyLabel labelFireball) {
        this.labelFireball = labelFireball;
        addLabel(labelFireball);
    }

    public void setLabelFireball(String labelFireball) {
        this.labelFireball.setText(labelFireball);
    }

    public MyLabel getLabelFireball() {
        return labelFireball;
    }

    public MyLabel getLabelHp() {
        return labelHp;
    }

    public void setLabelHp(MyLabel labelHp) {
        this.labelHp = labelHp;
        addLabel(labelHp);
    }

    public void setLabelHp(String labelHp) {
        this.labelHp.setText(labelHp);
    }

    public void setOnAction(Stage primaryStage, Scene mainScene, AnimationTimer game){
        menuButton.setOnAction(actionEvent -> {
            primaryStage.setScene(mainScene);
            game.stop();
        });
    }

    public int getNumberOfLevel() {
        return number;
    }

    private void setNumberOfLevel(int number) {
        this.number = number;
    }
}
