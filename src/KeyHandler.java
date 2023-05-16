/**
 * A KeyListener implementation for handling keyboard input in AStarGUI.
 * @author Giridhar Nair
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    // A reference to the AStarGUI instance to which this KeyHandler belongs.
    AStarGUI gui;

    boolean searched = false;

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
        if(gui.done) {
            if (!searched && code == KeyEvent.VK_ENTER) {
                gui.search();

                searched = true;
                Main.searchButton.setEnabled(false);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(false);
                Main.pauseResumeButton.setEnabled(true);
                Main.stopSearchButton.setEnabled(true);
            }
            if (code == KeyEvent.VK_SHIFT) {
                gui.clearPathOnly();
                searched = false;

                Main.searchButton.setEnabled(true);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(gui.solidExist());
                Main.pauseResumeButton.setEnabled(false);
                Main.stopSearchButton.setEnabled(false);
            }
            else if (code == KeyEvent.VK_BACK_SPACE) {
                gui.clearBoard();
                searched = false;

                Main.searchButton.setEnabled(true);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(gui.solidExist());
                Main.pauseResumeButton.setEnabled(false);
                Main.stopSearchButton.setEnabled(false);
            }
        } else if (code == KeyEvent.VK_SPACE){
            if (gui.pause) {
                Main.pauseResumeButton.setText("<html><center>Pause</center></html>");
            } else {
                Main.pauseResumeButton.setText("<html><center>Resume</center></html>");
            }
            gui.pause = !gui.pause;
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            gui.clearBoard();
            gui.cancel = true;
            gui.pause = false;
            Main.pauseResumeButton.setText("<html><center>Pause</center></html>");
            searched = false;

            Main.searchButton.setEnabled(true);
            Main.clearButton.setEnabled(false);
            Main.resetButton.setEnabled(gui.solidExist());
            Main.pauseResumeButton.setEnabled(false);
            Main.stopSearchButton.setEnabled(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}