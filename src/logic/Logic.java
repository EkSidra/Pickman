package logic;

import objects.level.map.moovig_objects.characters.Character;
import objects.level.map.moovig_objects.characters.Enemy;
import objects.level.map.moovig_objects.characters.MainCharacter;
import objects.level.map.moovig_objects.other_objects.Coin;
import objects.level.map.moovig_objects.other_objects.Fireball;
import objects.level.map.moovig_objects.other_objects.Upgrade;

import java.util.List;

public class Logic {

    public void moveUp(Character object) {
        object.setEndTimeUP(System.currentTimeMillis());
        if ((object.getEndTimeUP() - object.getStartTimeUP()) >= (100 / object.getSpeed())) {
            object.setDirection("up");
            String imagePath = object.getImagePath();
            int index = imagePath.lastIndexOf("up");
            if (index == -1) {
                //System.out.println("нет такой подстроки");
                index = imagePath.lastIndexOf("down1");
                if (index != -1) {
                    imagePath = imagePath.replace("down1", "up1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("down2");
                if (index != -1) {
                    imagePath = imagePath.replace("down2", "up1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left1");
                if (index != -1) {
                    imagePath = imagePath.replace("left1", "up1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left2");
                if (index != -1) {
                    imagePath = imagePath.replace("left2", "up1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right1");
                if (index != -1) {
                    imagePath = imagePath.replace("right1", "up1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right2");
                if (index != -1) {
                    imagePath = imagePath.replace("right2", "up1");
                    object.setSkin(imagePath);
                }
            } else {
                //System.out.println("такая подстрока есть");
                object.changeMovement();
            }
            object.setY(object.getY() - object.getStep());
            object.setStartTimeUP(System.currentTimeMillis());
        }
    }

    public void moveDown(Character object) {
        object.setEndTimeDOWN(System.currentTimeMillis());
        if ((object.getEndTimeDOWN() - object.getStartTimeDOWN()) >= (100 / object.getSpeed())) {
            object.setDirection("down");
            String imagePath = object.getImagePath();
            int index = imagePath.lastIndexOf("down");
            if (index == -1) {
                //System.out.println("нет такой подстроки");
                index = imagePath.lastIndexOf("up1");
                if (index != -1) {
                    imagePath = imagePath.replace("up1", "down1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("up2");
                if (index != -1) {
                    imagePath = imagePath.replace("up2", "down1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left1");
                if (index != -1) {
                    imagePath = imagePath.replace("left1", "down1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left2");
                if (index != -1) {
                    imagePath = imagePath.replace("left2", "down1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right1");
                if (index != -1) {
                    imagePath = imagePath.replace("right1", "down1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right2");
                if (index != -1) {
                    imagePath = imagePath.replace("right2", "down1");
                    object.setSkin(imagePath);
                }
            } else {
                //System.out.println("такая подстрока есть");
                object.changeMovement();
            }
            object.setY(object.getY() + object.getStep());
            object.setStartTimeDOWN(System.currentTimeMillis());
        }
    }

    public void moveLeft(Character object) {
        object.setEndTimeLEFT(System.currentTimeMillis());
        if ((object.getEndTimeLEFT() - object.getStartTimeLEFT()) >= (100 / object.getSpeed())) {
            object.setDirection("left");
            String imagePath = object.getImagePath();
            int index = imagePath.lastIndexOf("left");
            if (index == -1) {
                //System.out.println("нет такой подстроки");
                index = imagePath.lastIndexOf("up1");
                if (index != -1) {
                    imagePath = imagePath.replace("up1", "left1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("up2");
                if (index != -1) {
                    imagePath = imagePath.replace("up2", "left1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("down1");
                if (index != -1) {
                    imagePath = imagePath.replace("down1", "left1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("down2");
                if (index != -1) {
                    imagePath = imagePath.replace("down2", "left1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right1");
                if (index != -1) {
                    imagePath = imagePath.replace("right1", "left1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("right2");
                if (index != -1) {
                    imagePath = imagePath.replace("right2", "left1");
                    object.setSkin(imagePath);
                }
            } else {
                //System.out.println("такая подстрока есть");
                object.changeMovement();
            }
            object.setX(object.getX() - object.getStep());
            object.setStartTimeLEFT(System.currentTimeMillis());
        }
    }

    public void moveRight(Character object) {
        object.setEndTimeRIGHT(System.currentTimeMillis());
        if ((object.getEndTimeRIGHT() - object.getStartTimeRIGHT()) >= (100 / object.getSpeed())) {
            object.setDirection("right");
            String imagePath = object.getImagePath();
            int index = imagePath.lastIndexOf("right");
            if (index == -1) {
                //System.out.println("нет такой подстроки");
                index = imagePath.lastIndexOf("up1");
                if (index != -1) {
                    imagePath = imagePath.replace("up1", "right1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("up2");
                if (index != -1) {
                    imagePath = imagePath.replace("up2", "right1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("down1");
                if (index != -1) {
                    imagePath = imagePath.replace("down1", "right1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("down2");
                if (index != -1) {
                    imagePath = imagePath.replace("down2", "right1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left1");
                if (index != -1) {
                    imagePath = imagePath.replace("left1", "right1");
                    object.setSkin(imagePath);
                }
                index = imagePath.lastIndexOf("left2");
                if (index != -1) {
                    imagePath = imagePath.replace("left2", "right1");
                    object.setSkin(imagePath);
                }
            } else {
                //System.out.println("такая подстрока есть");
                object.changeMovement();
            }
            object.setX(object.getX() + object.getStep());
            object.setStartTimeRIGHT(System.currentTimeMillis());
        }
    }

    public void changeMovingEnemy(Enemy object) {
        int movingDirection; // 0 - Left, 1 - Up, 2 - Right, 3 - Down
        switch (object.direction()) {
            case "left":
                object.setX(object.getX() + object.getStep());
                movingDirection = 0;
                break;
            case "up":
                object.setY(object.getY() + object.getStep());
                movingDirection = 1;
                break;
            case "right":
                object.setX(object.getX() - object.getStep());
                movingDirection = 2;
                break;
            case "down":
                object.setY(object.getY() - object.getStep());
                movingDirection = 3;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + object.direction());
        }
        int temp = (int) (Math.random() * 4.99);
        while (temp == movingDirection) {
            double num = Math.random();
            if (num < 0.25)
                temp = 0;
            else if (num < 0.5)
                temp = 1;
            else if (num < 0.75)
                temp = 2;
            else temp = 3;
        }
        movingDirection = temp;
        switch (movingDirection) {
            case 0:
                object.setDirection("left");
                break;
            case 1:
                object.setDirection("up");
                break;
            case 2:
                object.setDirection("right");
                break;
            case 3:
                object.setDirection("down");
                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + object.direction());
        }
    }

    public void movingEnemy(Enemy object) {
        switch (object.direction()) {
            case "left":
                this.moveLeft(object);
                break;
            case "up":
                this.moveUp(object);
                break;
            case "right":
                this.moveRight(object);
                break;
            case "down":
                this.moveDown(object);
                break;
        }
    }

    public void movingUpgrade(Upgrade object) {
        object.setEndTimeRIGHT(System.currentTimeMillis());
        if ((object.getEndTimeRIGHT() - object.getStartTimeRIGHT()) >= (100 / object.getSpeed())) {
            object.changeMovement();
            object.setStartTimeRIGHT(System.currentTimeMillis());
        }
    }

    public void movingFireball(Fireball object) {
        object.setEndTimeRIGHT(System.currentTimeMillis());
        if ((object.getEndTimeRIGHT() - object.getStartTimeRIGHT()) >= (100 / object.getSpeed())) {
            if(object.direction().equals("up"))
                object.setY(object.getY() - object.getStep());
            if(object.direction().equals("down"))
                object.setY(object.getY() + object.getStep());
            if(object.direction().equals("left"))
                object.setX(object.getX() - object.getStep());
            if(object.direction().equals("right"))
                object.setX(object.getX() + object.getStep());
            object.setStartTimeRIGHT(System.currentTimeMillis());
        }
    }

    public void movingCoin(Coin object) {
        object.setEndTimeRIGHT(System.currentTimeMillis());
        if ((object.getEndTimeRIGHT() - object.getStartTimeRIGHT()) >= (100 / object.getSpeed())) {
            object.changeMovement();
            object.setStartTimeRIGHT(System.currentTimeMillis());
        }
    }

    public void checkEndUpgrade(Upgrade upgrade, MainCharacter hero, List<Enemy>enemies, long curTime){
        upgrade.setEndTimeUpgrade(curTime / 1_000_000);
        if(upgrade.isUpgradeEnded())
            upgrade.stopUse(hero);
    }

}
