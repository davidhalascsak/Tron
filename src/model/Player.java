package model;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Player {
    private int lastStep;
    private int width;
    private int height;
    private Image img;
    private boolean won;
    private Position position;
    private Direction previousDirection;
    private Direction direction;
    private final int id;
    private final String name;
    private final Colour lineColor;
    private final ArrayList<Position> neon;
    
    public Player(String name, Position position, Colour lineColor, String link,Position start, int id, Direction previousDirection) {
        this.id = id;
        this.won = true;
        this.name = name;
        this.lastStep = -1;
        this.position = position;  
        this.lineColor = lineColor;
        this.direction = Direction.STILL;
        this.img = new ImageIcon(link).getImage(); 
        this.height = this.img.getHeight(null);
        this.width = this.img.getWidth(null);
        this.neon = new ArrayList<Position>();
        this.previousDirection = previousDirection;
        neon.add(start);
    }
    
    public void collidesWithWall(int x, int y) {
        boolean u = this.position.getY() <= 20;
        boolean l = this.position.getX() <= 20;
        boolean d = (this.position.getY() + this.height) >= y;
        boolean r = (this.position.getX() + this.width) >= x;
        
        if(u || r || d || l) this.won = false;
    }
    
    public void collidesWithNeon(Player other) {

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int w = this.getWidth();
        int h = this.getHeight();
        int x1 = other.getPosition().getX();
        int y1 = other.getPosition().getY();
        int w1 = other.getWidth();
        int h1 = other.getHeight();

        if(this == other) {
            for(int j = 0; j < other.getNeon().size()-20; j++) {
                if(other.getNeon().size() >= 2) {
                    if(new Rectangle(x,y,w,h).intersectsLine(other.getNeon().get(j).getX(),other.getNeon().get(j).getY(), 
                            other.getNeon().get(j+1).getX(),other.getNeon().get(j+1).getY())) {
                        this.won = false;
                    }   
                }
            }
        } else {
           for(int j = 0; j < other.getNeon().size()-1; j++) {
                if(other.getNeon().size() >= 2) {
                    if(new Rectangle(x,y,w,h).intersectsLine(other.getNeon().get(j).getX(),other.getNeon().get(j).getY(), 
                            other.getNeon().get(j+1).getX(),other.getNeon().get(j+1).getY())) {
                        this.won = false;
                    }   
                }
            } 
        }
        
        if(this != other) {
            if(new Rectangle(x,y,w,h).intersects(new Rectangle(x1,y1,w1,h1))) {
                if(this.position.getY() > other.getPosition().getY() && (this.direction == Direction.UP && other.getDirection() == Direction.LEFT) ||
                        (this.direction == Direction.UP && other.getDirection() == Direction.RIGHT)) {
                    this.won = false;
               } else if(this.position.getY() < other.getPosition().getY() && (this.direction == Direction.DOWN && other.getDirection() == Direction.LEFT) ||
                        (this.direction == Direction.DOWN && other.getDirection() == Direction.RIGHT)) {
                    this.won = false;          
               } else if(this.position.getX() < other.getPosition().getX() && (this.direction == Direction.RIGHT && other.getDirection() == Direction.UP) ||
                        (this.direction == Direction.RIGHT && other.getDirection() == Direction.DOWN)) {
                   this.won = false;
               } else if(this.position.getX() > other.getPosition().getX() && (this.direction == Direction.LEFT && other.getDirection() == Direction.UP) ||
                        (this.direction == Direction.LEFT && other.getDirection() == Direction.DOWN)) {
                   this.won = false;
               } else if(this.direction == Direction.UP && other.getDirection() == Direction.UP && y1 < y) {
                   this.won = false;
               } else if(this.direction == Direction.DOWN && other.getDirection() == Direction.DOWN && y1 > y) {
                    this.won = false;
               } else if(this.direction == Direction.UP && other.getDirection() == Direction.DOWN ||
                       this.direction == Direction.DOWN && other.getDirection() == Direction.UP) {
                   this.won = false;
                   other.setWon(false);
               } else if(this.direction == Direction.RIGHT && other.getDirection() == Direction.LEFT ||
                       this.direction == Direction.LEFT && other.getDirection() == Direction.RIGHT) {
                   this.won = false;
                   other.setWon(false);
               }
            }
        }
    }

    
    public void move() {
        int x1 = this.position.getX();
        int x2 = this.direction.getX();
        int y1 = this.position.getY();
        int y2 = this.direction.getY();
        this.position.setX(x1 + x2);
        this.position.setY(y1 + y2);
        
        if(direction == direction.LEFT) {
            Position p = this.neon.get(this.neon.size()-1);
            neon.add(new Position(p.getX() - 2, p.getY()));
            if(id == 0) {
                this.img = new ImageIcon("purple-left.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
                
            } else if(id == 1) {
                this.img = new ImageIcon("yellow-left.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            }
        }
        else if(direction == direction.UP) {
            Position p = this.neon.get(this.neon.size()-1);
            neon.add(new Position(p.getX(), p.getY() - 2));
            if(id == 0) {
                this.img = new ImageIcon("purple-up.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            } else if(id == 1) {
                this.img = new ImageIcon("yellow-up.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            }
        }
        else if(direction == direction.RIGHT) {
            Position p = this.neon.get(this.neon.size()-1);
            neon.add(new Position(p.getX() + 2, p.getY()));
            if(id == 0) {
                this.img = new ImageIcon("purple-right.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            } else if(id == 1) {
                this.img = new ImageIcon("yellow-right.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            }
        }
        else if(direction == direction.DOWN) {
            Position p = this.neon.get(this.neon.size()-1);
            neon.add(new Position(p.getX(), p.getY() + 2));
            if(id == 0) {
                this.img = new ImageIcon("purple-down.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            } else if(id == 1) {
                this.img = new ImageIcon("yellow-down.png").getImage();
                this.height = this.img.getHeight(null);
                this.width = this.img.getWidth(null);
            }
        }
    }
    
    public void setPosition(Position p) {
        this.position = p;
    }
    
    public void setDirection(Direction d) {
        this.direction = d;
    }
    
    public void setPreviousDirection(Direction direction) {
        this.previousDirection = direction;
    }
    
     public void setWon(boolean won) {
        this.won = won;
    }
     public void setLastStep(int lastStep) {
        this.lastStep = lastStep;
    }

    public int getLastStep() {
        return lastStep;
    }
     
    public boolean getWon() {
        return won;
    }

    public String getName() {
        return name;
    }
    
    
    public Image getImage() {
        return img;
    }
    
    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public ArrayList<Position> getNeon() {
        return neon;
    }

    public int getHeight() {
        return this.height;
    }
    
    public Colour getLineColor() {
        return lineColor;
    }
    
    public Direction getPreviousDirection() {
        return previousDirection;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getId() {
        return id;
    }
}