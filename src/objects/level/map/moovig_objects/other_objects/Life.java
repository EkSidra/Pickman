package objects.level.map.moovig_objects.other_objects;

import javafx.geometry.Rectangle2D;
import objects.level.Level;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;

import java.util.List;

public class Life extends Upgrade {
    private static final String IMAGE_PATH = "/recourses/images/upgrades/heart.png";

    @Override
    public String getPath(){
        return IMAGE_PATH;
    }

    public Life(Level level, double x, double y) {
        super(level, x, y);
        upgradeTimeMS = 1;
        setSkin(IMAGE_PATH);
    }

    @Override
    public void use(MainCharacter hero) {
        hero.incHp();
        level.setLabelHp(String.valueOf(hero.getHp()));
        setVisible(false);
    }

    @Override
    protected void startUse(MainCharacter hero) {

    }

    @Override
    public void stopUse(MainCharacter hero) {

    }
}
