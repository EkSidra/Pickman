package objects.level.map;

import javafx.geometry.Rectangle2D;
import objects.level.Level;

import java.util.ArrayList;

public class VWall extends Wall{
    public VWall(Level level, String image, int count, double x, double y){
        bricks = new ArrayList<Brick>(count);
        for(int i = 0; i < count; i++)
        {
            Brick brick = new Brick();
            brick.setSkin(image);
            level.root().getChildren().add(brick);
            brick.setX(x);
            brick.setY(y + i * brick.getImage().getHeight());
            height += brick.getImage().getHeight();
            width = brick.getImage().getWidth();
        }
        level.root().getChildren().add(this);
        level.addBarrier(this);
        setX(x);
        setY(y);
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(getX(),getY(),width,height);
    }
}
