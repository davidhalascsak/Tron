package view;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import persistence.HighScore;
import model.*;

public class HighScoreWindow extends JDialog{
    private final JTable table;
    
    public HighScoreWindow(ArrayList<HighScore> highScores, JFrame parent){
        super(parent, true);
        table = new JTable(new HighScoreTableModel(highScores));
        table.setFillsViewportHeight(true);
        
        
        add(new JScrollPane(table));
        setSize(300,220);
        setTitle("Leaderboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
