package objects.level.map.moovig_objects.characters;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import objects.level.map.moovig_objects.other_objects.Upgrade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainCharacter extends Character {
    private static MainCharacter mainCharacter;
    private int collectedCoins;
    private int bullets;
    private int bulletsForCoin;
    private int killedEnemies;
    private final List<Upgrade> upgrades;
    private long gameTime;
    private boolean gotHit = false;
    private long startHit = 0, endHit = 0;
    private final int HIT_TIME = 3000;     //ms while main character is immortal

    //singleton
    private MainCharacter(){
        setStep(5);
        setSpeed(2.5); //2.5
        setDirection("down");
        collectedCoins = 0;
        bullets = 0;
        bulletsForCoin = 5;
        killedEnemies = 0;
        gameTime = 0;
        upgrades = new ArrayList<>();
        setHp(3);
    }
    public static synchronized MainCharacter getMainCharacter(){
        if(mainCharacter == null){
            mainCharacter = new MainCharacter();
        }
        return mainCharacter;
    }
    //coins
    public int getCollectedCoins(){ return collectedCoins; }
    public void setCollectedCoins(int collectedCoins){
        this.collectedCoins = collectedCoins;
    }
    //bullets
    public int getBullets(){
        return bullets;
    }
    public void setBullets(int bullets){
        this.bullets = bullets;
    }

    public int getBulletsForCoin() {
        return bulletsForCoin;
    }

    public void setBulletsForCoin(int bulletsForCoin) {
        this.bulletsForCoin = bulletsForCoin;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void setKilledEnemies(int killedEnemies) {
        this.killedEnemies = killedEnemies;
    }

    public void addOneKilledEnemy() {
        this.killedEnemies++;
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
    }

    public void removeUpgrade(Upgrade upgrade) {
        for(Upgrade upgrade_ : upgrades)
            if(upgrade.id() == upgrade_.id())
                upgrades.remove(upgrade);
    }

    public void getHit(double soundVolume){
        String musicFile = "src/recourses/sound/hit.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(soundVolume);
        mediaPlayer.play();

        startHit();
    }

    public void startHit(){
        hpDec();
        gotHit = true;

    }

    public void endHit(){
        gotHit = false;
    }

    public void clearUpgrades(){
        upgrades.clear();
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public void incHp(){
        setHp(getHp() + 1);
    }

    public double hitTime(){
        return HIT_TIME;        //ms while main character is immortal
    }

    public long getStartHit() {
        return startHit;
    }

    public void setStartHit(long startHit) {
        this.startHit = startHit;
    }

    public long getEndHit() {
        return endHit;
    }

    public void setEndHit(long endHit) {
        this.endHit = endHit;
    }

    public boolean isGotHit() {
        return gotHit;
    }

    public void setGotHit(boolean gotHit) {
        this.gotHit = gotHit;
    }
}
