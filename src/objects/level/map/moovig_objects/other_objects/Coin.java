package objects.level.map.moovig_objects.other_objects;

import objects.level.Level;
import objects.level.map.moovig_objects.MovingMyObject;

public class Coin extends MovingMyObject {
    private final String coin1 = "/recourses/images/coins/gold/medium/coin1.png";
    private final String coin2 = "/recourses/images/coins/gold/medium/coin2.png";
    private final String coin3 = "/recourses/images/coins/gold/medium/coin3.png";
    private final String coin4 = "/recourses/images/coins/gold/medium/coin4.png";
    private final String coin5 = "/recourses/images/coins/gold/medium/coin5.png";
    private final String coin6 = "/recourses/images/coins/gold/medium/coin6.png";
    private final String coin7 = "/recourses/images/coins/gold/medium/coin7.png";
    private final String coin8 = "/recourses/images/coins/gold/medium/coin8.png";
    private String currentCoin;

    public Coin(Level level, double x, double y){
        setSkin(currentCoin = coin1);
        level.root().getChildren().add(this);
        level.addCoins(this);
        setX(x);
        setY(y);
        setSpeed(getSpeed() * 2);
    }

    @Override
    public void changeMovement() {
        switch (currentCoin){
            case coin1: currentCoin = coin2;
                break;
            case coin2: currentCoin = coin3;
                break;
            case coin3: currentCoin = coin4;
                break;
            case coin4: currentCoin = coin5;
                break;
            case coin5: currentCoin = coin6;
                break;
            case coin6: currentCoin = coin7;
                break;
            case coin7: currentCoin = coin8;
                break;
            case coin8: currentCoin = coin1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentCoin);
        }
        setSkin(currentCoin);
    }
}
