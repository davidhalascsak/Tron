package view;

import java.sql.SQLException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import model.Colour;
import model.Direction;
import model.Player;
import model.Position;
import persistence.*;

public class Arena extends JPanel {
    private int score;
    private int currentTime;
    private boolean isEnded;
    private HighScores database;  
    private ArrayList<Player> players;
    private final Color background;
    private final JLabel elapsedTime;
    private final Timer timer;
    private final Timer newFrameTimer;

    public Arena(String name1, String name2, Colour c1, Colour c2) {
        super();  
        this.elapsedTime = new JLabel("Score: 0");
        this.currentTime = 0;
        this.background = Color.WHITE;
        this.database = null;
        try {
            this.database = new HighScores();
        } catch(SQLException ex) {
            Logger.getLogger(Arena.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(0).getLastStep() >= 1) {
                    players.get(0).setLastStep(currentTime);
                    if (players.get(0).getDirection() != Direction.STILL) {
                        if (players.get(0).getDirection() != Direction.RIGHT) {
                            players.get(0).setDirection(Direction.LEFT);
                            if (players.get(0).getPreviousDirection() == Direction.UP) {
                                int x = players.get(0).getPosition().getX();
                                int y = players.get(0).getPosition().getY();
                                players.get(0).setPosition((new Position(x + players.get(0).getWidth() / 2 - players.get(0).getHeight(), y + players.get(0).getHeight() - players.get(0).getWidth() / 2)));
                                players.get(0).setPreviousDirection(Direction.LEFT);
                            }
                            if (players.get(0).getPreviousDirection() == Direction.DOWN) {
                                int x = players.get(0).getPosition().getX();
                                int y = players.get(0).getPosition().getY();
                                players.get(0).setPosition((new Position(x + players.get(0).getWidth() / 2 - players.get(0).getHeight(), y - players.get(0).getWidth() / 2)));
                                players.get(0).setPreviousDirection(Direction.LEFT);
                            }
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(0).getLastStep() >= 1) {
                    players.get(0).setLastStep(currentTime);
                    if (players.get(0).getDirection() != Direction.LEFT) {
                        players.get(0).setDirection(Direction.RIGHT);
                        if (players.get(0).getPreviousDirection() == Direction.UP) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition((new Position(x + players.get(0).getWidth() / 2, y + players.get(0).getHeight() - players.get(0).getWidth() / 2)));
                            players.get(0).setPreviousDirection(Direction.RIGHT);
                        }
                        if (players.get(0).getPreviousDirection() == Direction.DOWN) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition((new Position(x + players.get(0).getWidth() / 2, y - players.get(0).getWidth() / 2)));
                            players.get(0).setPreviousDirection(Direction.RIGHT);
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(0).getLastStep() >= 1) {
                    players.get(0).setLastStep(currentTime);
                    if (players.get(0).getDirection() != Direction.UP) {
                        players.get(0).setDirection(Direction.DOWN);
                        if (players.get(0).getPreviousDirection() == Direction.LEFT) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition(new Position(x + players.get(0).getWidth() - players.get(0).getHeight() / 2, y + players.get(0).getHeight() / 2));
                            players.get(0).setPreviousDirection(Direction.DOWN);

                        }
                        if (players.get(0).getPreviousDirection() == Direction.RIGHT) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition(new Position(x - players.get(0).getHeight() / 2, y + players.get(0).getHeight() / 2));
                            players.get(0).setPreviousDirection(Direction.DOWN);
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(0).getLastStep() >= 1) {
                    players.get(0).setLastStep(currentTime);
                    if (players.get(0).getDirection() != Direction.DOWN) {
                        players.get(0).setDirection(Direction.UP);
                        if (players.get(0).getPreviousDirection() == Direction.LEFT) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition(new Position(x + players.get(0).getWidth() - players.get(0).getHeight() / 2, y - 85 + players.get(0).getHeight() / 2));
                            players.get(0).setPreviousDirection(Direction.UP);

                        }
                        if (players.get(0).getPreviousDirection() == Direction.RIGHT) {
                            int x = players.get(0).getPosition().getX();
                            int y = players.get(0).getPosition().getY();
                            players.get(0).setPosition(new Position(x - players.get(0).getHeight() / 2, y - 85 + players.get(0).getHeight() / 2));
                            players.get(0).setPreviousDirection(Direction.UP);
                        }
                    }
                }
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "left");
        this.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(1).getLastStep() >= 1) {
                    players.get(1).setLastStep(currentTime);
                    if (players.get(1).getDirection() != Direction.RIGHT) {
                        if (players.get(1).getDirection() != Direction.RIGHT) {
                            players.get(1).setDirection(Direction.LEFT);
                            if (players.get(1).getPreviousDirection() == Direction.UP) {
                                int x = players.get(1).getPosition().getX();
                                int y = players.get(1).getPosition().getY();
                                players.get(1).setPosition((new Position(x + players.get(1).getWidth() / 2 - players.get(1).getHeight(), y + players.get(1).getHeight() - players.get(1).getWidth() / 2)));
                                players.get(1).setPreviousDirection(Direction.LEFT);
                            }
                            if (players.get(1).getPreviousDirection() == Direction.DOWN) {
                                int x = players.get(1).getPosition().getX();
                                int y = players.get(1).getPosition().getY();
                                players.get(1).setPosition((new Position(x + players.get(1).getWidth() / 2 - players.get(1).getHeight(), y - players.get(1).getWidth() / 2)));
                                players.get(1).setPreviousDirection(Direction.LEFT);
                            }
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "right");
        this.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(1).getLastStep() >= 1) {
                    players.get(1).setLastStep(currentTime);
                    if (players.get(1).getDirection() != Direction.STILL) {
                        if (players.get(1).getDirection() != Direction.LEFT) {
                            players.get(1).setDirection(Direction.RIGHT);
                            if (players.get(1).getPreviousDirection() == Direction.UP) {
                                int x = players.get(1).getPosition().getX();
                                int y = players.get(1).getPosition().getY();
                                players.get(1).setPosition((new Position(x + players.get(1).getWidth() / 2, y + players.get(1).getHeight() - players.get(1).getWidth() / 2)));
                                players.get(1).setPreviousDirection(Direction.RIGHT);
                            }
                            if (players.get(1).getPreviousDirection() == Direction.DOWN) {
                                int x = players.get(1).getPosition().getX();
                                int y = players.get(1).getPosition().getY();
                                players.get(1).setPosition((new Position(x + players.get(1).getWidth() / 2, y - players.get(1).getWidth() / 2)));
                                players.get(1).setPreviousDirection(Direction.RIGHT);
                            }
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "down");
        this.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(1).getLastStep() >= 1) {
                    players.get(1).setLastStep(currentTime);
                    if (players.get(1).getDirection() != Direction.UP) {
                        players.get(1).setDirection(Direction.DOWN);
                        if (players.get(1).getPreviousDirection() == Direction.LEFT) {
                            int x = players.get(1).getPosition().getX();
                            int y = players.get(1).getPosition().getY();
                            players.get(1).setPosition(new Position(x + players.get(1).getWidth() - players.get(1).getHeight() / 2, y + players.get(1).getHeight() / 2));
                            players.get(1).setPreviousDirection(Direction.DOWN);

                        }
                        if (players.get(1).getPreviousDirection() == Direction.RIGHT) {
                            int x = players.get(1).getPosition().getX();
                            int y = players.get(1).getPosition().getY();
                            players.get(1).setPosition(new Position(x - players.get(1).getHeight() / 2, y + players.get(1).getHeight() / 2));
                            players.get(1).setPreviousDirection(Direction.DOWN);
                        }
                    }
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "up");
        this.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(currentTime - players.get(1).getLastStep() >= 1) {
                    players.get(1).setLastStep(currentTime);
                    if (players.get(1).getDirection() != Direction.DOWN) {
                        players.get(1).setDirection(Direction.UP);
                        if (players.get(1).getPreviousDirection() == Direction.LEFT) {
                            int x = players.get(1).getPosition().getX();
                            int y = players.get(1).getPosition().getY();
                            players.get(1).setPosition(new Position(x + players.get(1).getWidth() - players.get(1).getHeight() / 2, y - 85 + players.get(1).getHeight() / 2));
                            players.get(1).setPreviousDirection(Direction.UP);
                        }
                        if (players.get(1).getPreviousDirection() == Direction.RIGHT) {
                            int x = players.get(1).getPosition().getX();
                            int y = players.get(1).getPosition().getY();
                            players.get(1).setPosition(new Position(x - players.get(1).getHeight() / 2, y - 85 + players.get(1).getHeight() / 2));
                            players.get(1).setPreviousDirection(Direction.UP);
                        }
                    }
                }
            }
        });
        add(BorderLayout.NORTH,elapsedTime);
        
        restart(name1, name2, c1, c2);
        newFrameTimer = new Timer(40, new NewFrameListener());
        timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            increaseScore();
            updateElapsedTimeLabel();
            currentTime += 1;
        }
        });
        
        timer.start();
        newFrameTimer.start();
    }

    public void restart(String name1, String name2, Colour color1, Colour color2) {
        isEnded = false;
        this.score = 0;
        this.players = new ArrayList<Player>();
   
        int x1 = 125;
        int y1 = 350 + new ImageIcon("purple-right.png").getImage().getHeight(null) / 2;
        Position start1 = new Position(x1,y1);
        
        int x2 = 700;
        int y2 = 350 + new ImageIcon("yellow-left.png").getImage().getHeight(null) / 2;
        Position start2 = new Position(x2,y2);
        
        Player p1 = new Player(name1, new Position(120, 350), color1, "purple-right.png", start1, 0, Direction.RIGHT);
        Player p2 = new Player(name2, new Position(700 - new ImageIcon("yellow-left.png").getImage().getWidth(null), 350), color2, "yellow-left.png", start2, 1, Direction.LEFT);

        players.add(p1);
        players.add(p2);
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        setBackground(background);
        g2.drawImage(players.get(0).getImage(), players.get(0).getPosition().getX(), players.get(0).getPosition().getY(), this);
        g2.drawImage(players.get(1).getImage(), players.get(1).getPosition().getX(), players.get(1).getPosition().getY(), this);
        
        for (int i = 0; i < players.get(0).getNeon().size()-1; i++) {
            if(players.get(0).getNeon().size() >= 2) {
                g2.setStroke(new BasicStroke(4));
                g2.setColor(players.get(0).getLineColor().getColor());
                g2.draw(new Line2D.Double(players.get(0).getNeon().get(i).getX(), players.get(0).getNeon().get(i).getY(),
                        players.get(0).getNeon().get(i + 1).getX(), players.get(0).getNeon().get(i + 1).getY()));
            }
        }
        for (int i = 0; i < players.get(1).getNeon().size()-1; i++) {
            if(players.get(1).getNeon().size() >= 2) {
                g2.setStroke(new BasicStroke(4));
                g2.setColor(players.get(1).getLineColor().getColor());
                g2.draw(new Line2D.Double(players.get(1).getNeon().get(i).getX(), players.get(1).getNeon().get(i).getY(),
                        players.get(1).getNeon().get(i + 1).getX(), players.get(1).getNeon().get(i + 1).getY()));
            }
        }
        
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.black);
        g2.drawRect(20, 20, 760, 710);
    }

    class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).move();
                players.get(i).collidesWithWall(780,730);
                for(int j = 0; j < players.size(); j++) {
                        players.get(i).collidesWithNeon(players.get(j));
                    if(hasWinner()) break;
                }
                if(hasWinner()) break;
            }
            repaint();
            if(hasWinner()) {
                    isEnded = true;
                    showResultMessage();
                    if(getWinnerId() != -1) {
                        try {
                            database.putHighScore(players.get(getWinnerId()).getName(), score);
                        } catch(SQLException ex) {
                            System.out.println("Error");
                        }
                    }
                    newFrameTimer.stop();
            }
        }
    }

    public void startFrameTimer() {
        this.newFrameTimer.start();
    }
    
    public void startTimer() {
        this.timer.start();
    }
    
    public void updateElapsedTimeLabel() {
        String s = "Score: " + this.getScore();
        elapsedTime.setText(s);
    }
    
    public void increaseScore() {
        if(isEnded) {
            timer.stop();
        }
        else this.score += 1;
    }

    public HighScores getDatabase() {
        return database;
    }
    
    public void showResultMessage() {
        if(getWinnerId() == -1) JOptionPane.showMessageDialog(this, "The result is a draw!");
        else JOptionPane.showMessageDialog(this, "The winner is " + players.get(getWinnerId()).getName() + "!");
    }
    
    public void setIsEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void startAgain() {
       restart(players.get(0).getName(), players.get(1).getName(), players.get(0).getLineColor(), players.get(1).getLineColor());
    }
    
    public boolean hasWinner() {
        int counter = 0;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getWon()) counter += 1;
        }
        if(counter <= 1) return true;
        return false;
    }
    
    public boolean getIsEnded() {
        return isEnded;
    }
    
    public int getWinnerId() {
        int id = -1;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getWon()) id = players.get(i).getId();
        }
        return id;
    }
    
    public int getScore() {
        return score;
    }
    
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
