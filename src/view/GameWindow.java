package view;

import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import model.*;


public class GameWindow extends JFrame {
    private Arena drawArena;
    private MenuWindow window;
  
    
    public GameWindow(String name1, String name2, Colour c1, Colour c2, MenuWindow window) {
        setPreferredSize(new Dimension(800, 800));
        this.drawArena = new Arena(name1, name2, c1,  c2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawArena.setPreferredSize(new Dimension(800,800));
        this.window = window;
        
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Menu");
        JMenuItem menuGameLevel = new JMenuItem(new AbstractAction("Reset") {
            @Override
            public void actionPerformed(ActionEvent e) {
            drawArena.setScore(0);
            drawArena.startTimer();
            drawArena.startFrameTimer();
            drawArena.startAgain();
            }
        });
        JMenuItem menuGameLeaderboard = new JMenuItem(new AbstractAction("Leaderboard") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                 new HighScoreWindow(drawArena.getDatabase().getHighScores(), GameWindow.this);
                } catch(SQLException ex) {
                    System.out.println("Error");
                }
            }
            
        });
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                doUponExit();
            }

            
        });
              
        mainMenu.add(menuGameLevel);
        mainMenu.add(menuGameLeaderboard);
        mainMenu.add(menuGameExit);
        menuBar.add(mainMenu);
        setJMenuBar(menuBar);
        
        getContentPane().add(BorderLayout.CENTER, drawArena);
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    protected void doUponExit() {
        this.dispose();
        window.getGameWindows().remove(this);
    }
}

