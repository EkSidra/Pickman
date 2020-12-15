package objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class MyObject extends ImageView {
    //skin of object
    private int id;
    private static int count = 0;
    public MyObject(){
        count++;
        id = count;
    }

    public int id(){
        return id;
    }

    private String imagePath;
    private boolean abilityToPassThroughTheObject = false; //возможность проходить сквозь данный объект

    public void changeSkin(String path){
        Image temp = new Image(getClass().getResource(path).toExternalForm());
        this.setImage(temp);
        imagePath = path;
    }
    public void setSkin(String path){
        changeSkin(path);
    }
    public String getSkin(){
        return this.getImagePath();
    }
    public void setImagePath(String path) {
        imagePath = path;
        setSkin(path);
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setScale(double size) {
        this.setScaleY(this.getScaleY() * size);
        this.setScaleX(this.getScaleX() * size);
    }
    public void setSize(double width, double height) {
        this.setFitHeight(height);
        this.setFitWidth(width);
    }

    public Rectangle2D getBoundary()
    {
        if(abilityToPassThroughTheObject)
            return  new Rectangle2D(0,0,0,0);
        else
            return new Rectangle2D(getX(),getY(),getImage().getWidth(),getImage().getHeight());
    }

    public Rectangle2D hitBox(){
        return getBoundary();
    }

    public boolean intersects(MyObject object)
    {
        return object.getBoundary().intersects( this.getBoundary() );
    }

    public void setAbilityToPassThroughTheObject(boolean value)
    {
        abilityToPassThroughTheObject = value;
    }
}
