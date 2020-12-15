package objects.level.map.moovig_objects.characters;

import objects.level.Level;

public class Enemy extends Character {
    public Enemy(Level level, double x, double y, String skin) {
        setStep(7);
        setSpeed(3);
        setHp(5);
        double num = Math.random();
        if (num < 0.25)
            setDirection("right");
        else if (num < 0.5)
            setDirection("up");
        else if (num < 0.75)
            setDirection("left");
        else
            setDirection("down");
        setX(x);
        setY(y);
        setSkin(skin);
        level.root().getChildren().add(this);
        level.addEnemy(this);
    }
}
