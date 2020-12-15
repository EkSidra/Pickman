package objects.level.map.moovig_objects.other_objects;

import objects.level.Level;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;

import java.util.List;

public class Boost extends Upgrade {
    private static final String IMAGE_PATH = "/recourses/images/upgrades/rainbow.png";
    private double lastSpeed;
    public Boost(Level level, double x, double y){
        super(level,x,y);
        super.changeSkin(IMAGE_PATH);
        upgradeTimeMS = 7000;
    }

    @Override
    public void use(MainCharacter hero){
        hero.addUpgrade(this);
        setVisible(false);
        lastSpeed = hero.getSpeed();
        startUse(hero);
    }

    public String getPath(){
        return IMAGE_PATH;
    }

    @Override
    public void startUse(MainCharacter hero){
        hero.setSpeed(hero.getSpeed() * 5);
    }

    @Override
    public void stopUse(MainCharacter hero){
        hero.setSpeed(lastSpeed);
        hero.removeUpgrade(this);
    }

    @Override
    public final void setSkin(String path){}
    @Override
    public final void changeSkin(String path){}
    @Override
    public final void setImagePath(String path){}
}
