package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import persistence.HighScore;

public class HighScoreTableModel extends AbstractTableModel{
    private final ArrayList<HighScore> highScores;
    private final String[] colName = new String[]{ "Name", "Score"};
    
    public HighScoreTableModel(ArrayList<HighScore> highScores){
        this.highScores = highScores;
    }

    @Override
    public int getRowCount() { 
        if(highScores.size() <= 10) return highScores.size();
        return 10;
    }

    @Override
    public int getColumnCount() { return 2; }

    @Override
    public Object getValueAt(int r, int c) {
        HighScore h = highScores.get(r);
        if      (c == 0) return h.getName();
        return h.getScore();
    }

    @Override
    public String getColumnName(int i) { return colName[i]; }    
    
}
