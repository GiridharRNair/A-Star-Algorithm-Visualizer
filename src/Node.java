import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

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
    boolean clicked;
    boolean disabled;

    public Node(int row, int col) {
        disabled = false;
        clicked = false;
        this.row = row;
        this.col = col;
        this.setFocusable(false);

        this.setFont(new Font("Arial", Font.PLAIN, 8));
        setBackground(Color.WHITE);
        setForeground(Color.black);
        addActionListener(this);
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
        if (!start && !goal) {
            solid = false;
            open = false;
            checked = false;
            clicked = false;
            disabled = false;
            setBackground(Color.WHITE);
            setForeground(Color.black);
        }
    }

    public void clearPath() {
        if (!solid) {
            open = false;
            checked = false;
            clicked = false;
            disabled = false;
            setBackground(Color.WHITE);
            setForeground(Color.black);
        } else if (solid) {
            disabled = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!start && !goal && !disabled) {
            if (!clicked) {
                setBackground(Color.BLACK);
                setForeground(Color.BLACK);
                solid = true;
                clicked = true;
            }
            else {
                setBackground(Color.WHITE);
                setForeground(Color.black);
                solid = false;
                clicked = false;
            }
        }
    }
}
