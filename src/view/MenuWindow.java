package view;

import model.Colour;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import persistence.HighScores;



public class MenuWindow extends JFrame{
    private final List<GameWindow> gameWindows;
    
    
    private final Colour[] colors = { new Colour("Fekete", Color.black), new Colour("Fehér", Color.white),
                                new Colour("Szürke", Color.gray), new Colour("Piros", Color.red),
                                new Colour("Zöld", Color.green), new Colour("Kék", Color.blue),
                                new Colour("Sárga", Color.yellow), new Colour("Lila", Color.magenta),
                                new Colour("Cián", Color.cyan) };
    
    
    public MenuWindow() {
        this.gameWindows = new ArrayList<>();
        setSize(500,500);
        setTitle("Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(3,2));
        
        JComboBox combo1 = new JComboBox();
        JComboBox combo2 = new JComboBox();
        JTextField name1 = new JTextField("Player One");
        JTextField name2 = new JTextField("Player Two");
        JButton button = new JButton("Start");
        JButton menuGameLeaderboard = new JButton(new AbstractAction("Leaderboard") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HighScores db = new HighScores();
                    new HighScoreWindow(db.getHighScores(), MenuWindow.this);
                } catch(SQLException ex) {
                    System.out.println("Error");
                }
            }
            
        });
        
        for (Colour color : colors) {
            combo1.addItem(color.getName());
            combo2.addItem(color.getName());
        }
        add(combo1);
        add(combo2);
        add(name1);
        add(name2);
        add(button);
        add(menuGameLeaderboard);
         
        button.addActionListener(getActionListener(combo1, combo2, name1, name2));
 
        pack();
        setVisible(true);
    }
    
    private ActionListener getActionListener(JComboBox color1, JComboBox color2, JTextField name1, JTextField name2) {
        return new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean equalName = name1.getText().equals(name2.getText());
                boolean equalColor = color1.getSelectedItem() == color2.getSelectedItem();
                if(!equalColor && !equalName) {
                    Colour colour1 = null;
                    Colour colour2 = null;
                    for(Colour color : colors) {
                        if(color.getName().equals(color1.getSelectedItem()))
                            colour1 = new Colour(color.getName(), color.getColor());
                        if(color.getName().equals(color2.getSelectedItem()))
                            colour2 = new Colour(color.getName(), color.getColor());
                    }
                    GameWindow window = new GameWindow(name1.getText(), name2.getText(), colour1, colour2, MenuWindow.this);
                    window.setVisible(true);
                    gameWindows.add(window);               
                }   
            }
        };
    }

    public List<GameWindow> getGameWindows() {
        return gameWindows;
    }
    
    
}


