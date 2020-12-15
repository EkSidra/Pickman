package objects.level.map.moovig_objects.other_objects;

import objects.level.map.moovig_objects.MovingMyObject;

public class Fireball extends MovingMyObject {

    private String direction;

    public Fireball(String direction){
        String path = "/recourses/images/gun/fireball.png";
        super.setSkin(path);
        setSpeed(100);      //сколько раз можно показывать пулю в секунду (чтобы было не меньше 60)
        setStep(15);        //скорость пули
        this.direction = direction;
        switch (direction){
            case "up": setRotate(270);
                break;
            case "left": setRotate(180);
                break;
            case "down": setRotate(90);
                break;
            case "right": break;
            default:
                System.out.println("Wrong name of direction of fireball. Use: up/down/left/right");
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    @Override
    public void changeMovement() {

    }

    public String direction() {
        return direction;
    }
}
