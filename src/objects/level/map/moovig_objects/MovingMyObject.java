package objects.level.map.moovig_objects;

import objects.MyObject;

public abstract class MovingMyObject extends MyObject {

    private int step;
    private double speed;
    private long endTimeUP = 0, startTimeUP = 0;
    private long endTimeDOWN = 0, startTimeDOWN = 0;
    private long endTimeLEFT = 0, startTimeLEFT = 0;
    private long endTimeRIGHT = 0, startTimeRIGHT = 0;
    //up
    public void setEndTimeUP(long time) { this.endTimeUP = time;}
    public long getEndTimeUP() { return this.endTimeUP;}
    public void setStartTimeUP(long time) { this.startTimeUP = time;}
    public long getStartTimeUP() { return this.startTimeUP;}
    //down
    public void setEndTimeDOWN(long time) { this.endTimeDOWN = time;}
    public long getEndTimeDOWN() { return this.endTimeDOWN;}
    public void setStartTimeDOWN(long time) { this.startTimeDOWN = time;}
    public long getStartTimeDOWN() { return this.startTimeDOWN;}
    //left
    public void setEndTimeLEFT(long time) { this.endTimeLEFT = time;}
    public long getEndTimeLEFT() { return this.endTimeLEFT;}
    public void setStartTimeLEFT(long time) { this.startTimeLEFT = time;}
    public long getStartTimeLEFT() { return this.startTimeLEFT;}
    //right
    public void setEndTimeRIGHT(long time) { this.endTimeRIGHT = time;}
    public long getEndTimeRIGHT() { return this.endTimeRIGHT;}
    public void setStartTimeRIGHT(long time) { this.startTimeRIGHT = time;}
    public long getStartTimeRIGHT() { return this.startTimeRIGHT;}
    ////////////////////////////////////////////////////////////////
    public MovingMyObject(){
        speed = 1;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    abstract public void changeMovement();
    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }
}
