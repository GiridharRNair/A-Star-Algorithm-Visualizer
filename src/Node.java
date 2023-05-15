/**
 * This class represents a single grid node in the GUI grid layout.
 * @author Giridhar Nair
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

    // Instance variables representing the state of the Node.
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

    /**
     * Initializes a new instance of the Node class.
     * @param row The row that the Node is on in the grid.
     * @param col The column that the Node is on in the grid.
     */
    public Node(int row, int col) {

        // Initialize state variables.
        disabled = false;
        clicked = false;
        this.row = row;
        this.col = col;
        this.setFocusable(false);

        // Set up visual display of Node.
        this.setFont(new Font("Arial", Font.PLAIN, 8));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        addActionListener(this);
    }

    /**
     * Sets the Node as the start node and updates its visual display.
     */
    public void setStartNode() {
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setText("Start");
        start = true;
    }

    /**
     * Sets the Node as the goal node and updates its visual display.
     */
    public void setGoalNode() {
        setBackground(Color.YELLOW);
        setForeground(Color.BLACK);
        setText("Goal");
        goal = true;
    }

    /**
     * Updates the visual display of the node as having been checked.
     */
    public void setAsChecked() {
        if (!start && !goal) {
            setBackground(Color.ORANGE);
            setForeground(Color.BLACK);
        }
        checked = true;
    }

    /**
     * Updates the visual display of the Node if it's on the
     * most optimal path.
     */
    public void setAsPath() {
        setBackground(Color.GREEN);
        setForeground(Color.BLACK);
    }

    /**
     * Resets the Node to its default values.
     */
    public void reset() {
        solid = false;
        open = false;
        checked = false;
        clicked = false;
        disabled = false;
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        this.setToolTipText("");
    }

    /**
     * If the Node is not a solid, it will reset to its default values, but
     * will disable the Node regardless of state.
     */
    public void clearOnlyPath() {
        if (!solid) {
            open = false;
            checked = false;
            clicked = false;
            disabled = false;
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        } else {
            disabled = false;
        }
        this.setToolTipText("");
    }

    public void disableNode() {
        this.setToolTipText("Cannot Edit Node While Search");
        disabled = true;
    }

    /**
     * If a Node is clicked, and it's not the start or goal Node,
     * it turns into a solid, as long the Nodes aren't disabled.
     * @param e The ActionEvent passed to the method.
     */
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
                setForeground(Color.BLACK);
                solid = false;
                clicked = false;
            }
        }
    }
}