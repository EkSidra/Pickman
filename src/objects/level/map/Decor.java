package objects.level.map;

import objects.MyObject;
import objects.level.Level;

public class Decor extends MyObject {
    public Decor(Level level, double x, double y, String image){
        setX(x);
        setY(y);
        setSkin(image);
        setAbilityToPassThroughTheObject(true);
        level.root().getChildren().add(this);
    }
}
