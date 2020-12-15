package objects.level.map.moovig_objects.other_objects;

import objects.level.Level;
import objects.level.map.moovig_objects.MovingMyObject;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;

import java.util.List;

public abstract class Upgrade extends MovingMyObject {

    private int currentFrame;       //текущий кадр
    private long endTimeUpgrade, startTimeUpgrade;
    protected long upgradeTimeMS;
    protected Level level;

    public Upgrade(Level level, double x, double y){
        setStep(0);
        setScale(0.8);
        setSpeed(0.8);
        setX(x);
        setY(y);
        level.root().getChildren().add(this);
        level.addUpgrade(this);
        currentFrame = 0;
        upgradeTimeMS = 5000;
        endTimeUpgrade = 0;
        startTimeUpgrade = 0;
        this.level = level;
    }

    public abstract String getPath();

    @Override
    public void changeMovement() {
        double up = 1.25, down = 0.8;
        switch (currentFrame){ //1.0625 / 0.871839581517
            case 0:
                setScale(up);
                currentFrame++;
                break;
            case 1:
                setScale(down);
                currentFrame = 0;
                break;
        }
    }

    public abstract void use(MainCharacter hero);

    public long getUpgradeTimeMS() {
        return upgradeTimeMS;
    }

    public void setEndTimeUpgrade(long endTimeUpgrade) {
        this.endTimeUpgrade = endTimeUpgrade;
    }

    public void setStartTimeUpgrade(long startTimeUpgrade) {
        this.startTimeUpgrade = startTimeUpgrade / 1_000_000;
    }

    public boolean isUpgradeEnded(){
        return endTimeUpgrade - startTimeUpgrade >= upgradeTimeMS;
    }

    protected abstract void startUse(MainCharacter hero);
    public abstract void stopUse(MainCharacter hero);
}
