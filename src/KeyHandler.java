/**
 * A KeyListener implementation for handling keyboard input in AStarGUI.
 * @author Giridhar Nair
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    // A reference to the AStarGUI instance to which this KeyHandler belongs.
    AStarGUI gui;

    /**
     * Constructs a new KeyHandler with the given AStarGUI instance.
     * @param gui The instance of AStarGUI to which this KeyHandler belongs.
     */
    public KeyHandler(AStarGUI gui) {
        this.gui = gui;
    }

    /**
     * Responds to a key being pressed.
     * @param e The KeyEvent corresponding to the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Only respond to keys if the search is completed
        if(gui.done) {

            // If Enter key is pressed and goal has not been reached, initiate a new search
            if (code == KeyEvent.VK_ENTER && !gui.goalReached) {
                gui.search();
            }

            // If Shift key is pressed, clear the current path
            if (code == KeyEvent.VK_SHIFT) {
                gui.clearPathOnly();
            }

            // If Backspace key is pressed, clear the whole board
            if (code == KeyEvent.VK_BACK_SPACE) {
                gui.clearBoard();
            }
        }
        // Otherwise, if the space bar is pressed, toggle pause state
        else if (code == KeyEvent.VK_SPACE){

            // Update the GUI button text accordingly
            if (gui.pause) {
                Main.pauseResumeButton.setText("<html><center>Pause</center></html>");
            } else {
                Main.pauseResumeButton.setText("<html><center>Resume</center></html>");
            }
            gui.pause = !gui.pause;
        }
        else if (code == KeyEvent.VK_X) {
            gui.cancel = true;
            gui.pause = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}