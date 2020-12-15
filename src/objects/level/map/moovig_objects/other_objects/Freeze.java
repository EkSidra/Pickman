package objects.level.map.moovig_objects.other_objects;

import objects.level.Level;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;

import java.util.HashMap;

public class Freeze extends Upgrade {
    private static final String IMAGE_PATH = "/recourses/images/upgrades/clock.png";
    private HashMap<Integer,Double> enemySpeed; //key = enemy.id, value = enemy.speed       //in case if some enemies will die
    public Freeze(Level level, double x, double y) {
        super(level, x, y);
        setSkin(IMAGE_PATH);
    }

    @Override
    public String getPath(){
        return IMAGE_PATH;
    }

    @Override
    public void use(MainCharacter hero) {
        hero.addUpgrade(this);
        setVisible(false);
        enemySpeed = new HashMap<>(level.getEnemies().size());
        for(Enemy enemy : level.getEnemies()){
            enemySpeed.put(enemy.id(), enemy.getSpeed());
        }
        startUse(hero);
    }

    @Override
    protected void startUse(MainCharacter hero) {
        hero.addUpgrade(this);
        for(Enemy enemy : level.getEnemies()){
            enemy.setSpeed(0);
        }
    }

    @Override
    public void stopUse(MainCharacter hero) {
        for(Enemy enemy : level.getEnemies()){
            enemy.setSpeed(enemySpeed.get(enemy.id())); //get speed on id of the enemy
        }
        hero.removeUpgrade(this);
    }
}
