import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton {

    Node parent;
    int row;
    int col;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;

        setBackground(Color.WHITE);
        setForeground(Color.black);
    }


    public void setStartNode() {
        setBackground(Color.blue);
        setForeground(Color.white);
        setText("Start");
        start = true;
    }

    public void setGoalNode() {
        setBackground(Color.yellow);
        setForeground(Color.black);
        setText("Goal");
        goal = true;
    }

    public void setAsSolid() {
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);
        solid = true;
    }

    public void setAsChecked() {
        if (!start && !goal) {
            setBackground(Color.ORANGE);
            setForeground(Color.black);
        }
        checked = true;
    }

    public void setAsOpen() {
        open = true;
    }

    public void setAsPath() {
        setBackground(Color.GREEN);
        setForeground(Color.black);
    }

    public void reset() {
        solid = false;
        open = false;
        checked = false;
        setBackground(Color.WHITE);
        setForeground(Color.black);
    }
}
