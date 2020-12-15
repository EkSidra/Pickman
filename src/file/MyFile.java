package file;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import objects.level.Level;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;
import objects.level.map.moovig_objects.other_objects.*;
import objects.menu_objects.MyLabel;

import java.io.*;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.StringTokenizer;

public class MyFile extends File{                                          //System.getProperty("user.dir") //curr dir
    private static final String dir = System.getProperty("user.dir") + "/src/sys";
    public MyFile(String name) {
        super(dir,name + ".sys");
    }

    public String getDir(){
        return dir;
    }

    private void writeStringLn(String string) throws IOException {
        FileWriter fw = new FileWriter(this, false); // false for appending
        PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
        pw.println(string);
        pw.close();
    }

    private void writeString(String string) throws IOException {
        FileWriter fw = new FileWriter(this, false); // false for appending
        PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
        pw.print(string);
        pw.close();
    }

    private void appendString(String string) throws IOException {
        FileWriter fw = new FileWriter(this, true); // true for appending
        PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
        pw.print(string);
        pw.close();
    }
    private void appendStringLn(String string) throws IOException {
        FileWriter fw = new FileWriter(this, true); // true for appending
        PrintWriter pw = new PrintWriter(fw, true); // true for auto-flush
        pw.println(string);
        pw.close();
    }

    /* Main character filds:
    private int collectedCoins;
    private int bullets;
    private int bulletsForCoin;
    private int killedEnemies;
    private long gameTime;
    hp
    */
    public void writeMainCharacter(MainCharacter hero) throws IOException {
        writeStringLn(String.valueOf(hero.getCollectedCoins()));
        appendStringLn(String.valueOf(hero.getBullets()));
        appendStringLn(String.valueOf(hero.getBulletsForCoin()));
        appendStringLn(String.valueOf(hero.getKilledEnemies()));
        appendStringLn(String.valueOf(hero.getGameTime()));
        appendStringLn(String.valueOf(hero.getHp()));
    }

    private String readString(int number) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this));
        String temp = br.readLine();
        for(int i = 0; i < number; i++)
            temp = br.readLine();
        br.close();
        return temp;
    }

    public void readMainCharacter(MainCharacter hero) throws IOException {
        hero.setCollectedCoins(Integer.parseInt(readString(0)));
        hero.setBullets(Integer.parseInt(readString(1)));
        hero.setBulletsForCoin(Integer.parseInt(readString(2)));
        hero.setKilledEnemies(Integer.parseInt(readString(3)));
        hero.setGameTime(Long.parseLong(readString(4)));
        hero.setHp(Integer.parseInt(readString(5)));
    }

    /* Level filds:
    private int number;                 //number of level
    private double heroX;   private double heroY;
    private List<Enemy> enemies;
    private List<Upgrade> upgrades;
    private List<Coin> coins;

    private long levelTime;             //в данном случае не будем записывать время
                                          уровня(каждый раз когда будем заходить в игру,
                                          время уровня будет обнуляться, хотя на самом деле
                                          на этот уровень было затрачено определенное количесто времени
    */

    /*
    записываем:
        номер уровеня
        х у (MainCharacter)
        кол-во монет
            каждую монету
        кол-во врагов
            каждого врага
        кол-во upgrades
            каждый upgrade
     */
    public void writeCurrentLevel(Level level, MainCharacter hero) throws IOException {
        hero.clearUpgrades();

        writeStringLn(String.valueOf(level.getNumberOfLevel()));

        appendStringLn(hero.getX() + " " + hero.getY());

        appendStringLn(String.valueOf(level.getCoins().size()));
        for(Coin coin : level.getCoins()){
            appendStringLn(stringCoin(coin));
        }

        appendStringLn(String.valueOf(level.getEnemies().size()));
        for(Enemy enemy : level.getEnemies()){
            appendStringLn(stringEnemy(enemy));
        }

        appendStringLn(String.valueOf(level.getUpgrades().size()));
        for(Upgrade upgrade : level.getUpgrades()){
            appendStringLn(stringUpgrade(upgrade));
        }
    }

    private String stringCoin(Coin coin){
        return coin.getX() + " " + coin.getY();
    }

    /*
    запись врага:
        x y hp speed skin
     */
    private String stringEnemy(Enemy enemy){
        return enemy.getX() + " " + enemy.getY() + " " + enemy.getHp() + " " + enemy.getSpeed() + " " + enemy.getSkin();
    }

    /*
    запись upgrade:
        х у skin
     */
    private String stringUpgrade(Upgrade upgrade){
        return upgrade.getX() + " " + upgrade.getY() + " " + upgrade.getPath();
    }

    /*
        читаем:
            номер уровеня
            х у (MainCharacter)
            кол-во монет
                каждую монету
            кол-во врагов
                каждого врага
            кол-во upgrades
                каждый upgrade
         */
    public Level readCurrentLevel(List<Level>levels, MainCharacter hero) throws IOException {
        int numberOfLevel, i = 0;

        //читаем и устанавливаем номер текущего уровня
        String string = readString(i++);
        numberOfLevel = Integer.parseInt(string);

        Level currentLevel = levels.get(numberOfLevel - 1);

        //убираем все динамические объекты с этого уровня
        currentLevel.getUpgrades().removeAll(currentLevel.getUpgrades());
        currentLevel.getUpgrades().clear();
        currentLevel.getEnemies().removeAll(currentLevel.getEnemies());
        currentLevel.getEnemies().clear();
        currentLevel.getCoins().removeAll(currentLevel.getCoins());
        currentLevel.getCoins().clear();

        //ставим главного героя на место
        string = readString(i++);
        double x = 0, y = 0;
        String temp;

        StringTokenizer st = new StringTokenizer(string, " ");

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            x = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            y = Double.parseDouble(temp);
        }

        hero.setX(x);
        hero.setY(y);
        if(!currentLevel.root().getChildren().contains(hero))
            currentLevel.root().getChildren().add(hero);

        //расставляем все остальные динамические объекты
        //монеты
        string = readString(i++);   //количество монет
        for(int j = 0; j < Integer.parseInt(string); j++){
            readCoin(readString(i++), currentLevel);
        }

        //враги
        string = readString(i++);   //количество врагов
        for(int j = 0; j < Integer.parseInt(string); j++){
            readEnemy(readString(i++), currentLevel);
        }

        //upgrades
        string = readString(i++);   //количество upgrades
        for(int j = 0; j < Integer.parseInt(string); j++){
            readUpgrade(readString(i++), currentLevel);
        }

        return currentLevel;
    }

    /*
    чтение врага:
        x y hp speed skin
     */
    private Enemy readEnemy(String string, Level level){
        double x = 0, y = 0, speed = 0;
        int hp = 0;
        String skin = null, temp;

        StringTokenizer st = new StringTokenizer(string, " ");

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            x = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            y = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            hp = Integer.parseInt(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            speed = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            skin = st.nextToken();
        }

        Enemy enemy = new Enemy(level,x,y,skin);
        enemy.setSpeed(speed);
        enemy.setHp(hp);

        return enemy;
    }

    private Coin readCoin(String string, Level level){
        double x = 0, y = 0;
        String temp;

        StringTokenizer st = new StringTokenizer(string, " ");

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            x = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            y = Double.parseDouble(temp);
        }

        return new Coin(level, x, y);
    }

    /*
    чтение upgrade:
    х у skin
    */
    private Upgrade readUpgrade(String string, Level level){
        double x = 0, y = 0;
        String skin = null, temp;

        StringTokenizer st = new StringTokenizer(string, " ");

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            x = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            temp = st.nextToken();
            y = Double.parseDouble(temp);
        }

        if(st.hasMoreTokens()) {
            skin = st.nextToken();
        }

        int index = skin.lastIndexOf("clock");
        if (index != -1) {
            return new Freeze(level,x,y);
        }

        index = skin.lastIndexOf("heart");
        if (index != -1) {
            return new Life(level,x,y);
        }

        index = skin.lastIndexOf("rainbow");
        if (index != -1) {
            return new Boost(level,x,y);
        }

        return null;
    }

    public Point2D readSound() throws IOException {
        double music = Double.parseDouble(readString(0));
        double sound = Double.parseDouble(readString(1));
        return new Point2D(music, sound);
    }
    public void writeSound(double music, double sound) throws IOException {
        writeStringLn(String.valueOf(music));
        appendStringLn(String.valueOf(sound));
    }

    public void writeStatistic(MainCharacter hero, String winnerName) throws IOException {
        appendString("Монеты: " + hero.getCollectedCoins() + " Убито врагов: " + hero.getKilledEnemies());
        long
                min = hero.getGameTime() / 60 % 60,
                sec = hero.getGameTime() % 60;
        appendStringLn(" Время игры: " + min + ":" + sec + " Имя: " + winnerName);
    }

    private long startTime = 0, endTime = 0;
    public void readStatistic(Pane pane){
        double x = 0,y = 0;
        int i = 0;
        String tempString = null;
        startTime = System.currentTimeMillis();
        do{
            try {
                endTime = System.currentTimeMillis();
                tempString = readString(i++);
                if(tempString == null){
                    break;
                }
                MyLabel labelString = new MyLabel(tempString);
                labelString.setPrefWidth(20 * tempString.length() + 20);
                labelString.setLayoutX(x);
                labelString.setLayoutY(y);
                y += 100;
                pane.getChildren().add(labelString);
//                break;
            } catch (IOException e) {   //когда не останется строк для чтения выходим из цикла чтения строк
                e.printStackTrace();
                break;
            }
        }while (endTime - startTime < 500);
    }
}
