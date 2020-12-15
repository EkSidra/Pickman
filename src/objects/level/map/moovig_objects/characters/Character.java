package objects.level.map.moovig_objects.characters;
import objects.level.map.moovig_objects.MovingMyObject;

public abstract class Character extends MovingMyObject {

    public Character(){
        setStep(5);
        hp = 1;
    }
    private String direction;
    private int hp;
    public int getHp(){ return hp; }
    public void setHp(int hp){
        this.hp = hp;
    }
    public void hpDec() {
        hp--;
    }

    @Override
    public void changeMovement(){
        String path = this.getImagePath();
        int index = path.lastIndexOf("up1");
        if (index != -1) {
            path = path.replace("up1", "up2");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("up2");
        if (index != -1) {
            path = path.replace("up2", "up1");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("down1");
        if (index != -1) {
            path = path.replace("down1", "down2");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("down2");
        if (index != -1) {
            path = path.replace("down2", "down1");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("left1");
        if (index != -1) {
            path = path.replace("left1", "left2");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("left2");
        if (index != -1) {
            path = path.replace("left2", "left1");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("right1");
        if (index != -1) {
            path = path.replace("right1", "right2");
            this.setSkin(path);
            return;
        }
        index = path.lastIndexOf("right2");
        if (index != -1) {
            path = path.replace("right2", "right1");
            this.setSkin(path);
            return;
        }
    }

    public String direction() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

