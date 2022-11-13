package model;

public enum Direction {
    DOWN(0, 2), LEFT(-2, 0), UP(0, -2), RIGHT(2, 0), STILL(0,0);
    
    public final int x;
    public final int y;
    
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}