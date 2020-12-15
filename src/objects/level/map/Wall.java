package objects.level.map;

import objects.MyObject;

import java.util.List;

public abstract class Wall extends MyObject {
    protected List<Brick> bricks;
    protected double width = 0, height = 0;
}
