/**
 * This class is responsible for handling button events in the AStarGUI.
 * @author Giridhar Nair
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {

    // AStarGUI instance
    AStarGUI gui;

    /**
     * Constructor for ButtonHandler class.
     * @param gui - An instance of AStarGUI.
     */
    public ButtonHandler(AStarGUI gui) {
        this.gui = gui;
    }

    /**
     * Override actionPerformed method to handle button events.
     * @param e - The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gui.done) {
            if (e.getSource() == Main.searchButton && !gui.goalReached) {
                gui.search();

                Main.searchButton.setEnabled(false);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(false);
                Main.pauseResumeButton.setEnabled(true);
                Main.stopSearchButton.setEnabled(true);
            }
            else if (e.getSource() == Main.clearButton) {
                gui.clearPathOnly();

                Main.searchButton.setEnabled(true);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(gui.solidExist());
                Main.pauseResumeButton.setEnabled(false);
                Main.stopSearchButton.setEnabled(false);
            }
            else if (e.getSource() == Main.resetButton) {
                gui.clearBoard();

                Main.searchButton.setEnabled(true);
                Main.clearButton.setEnabled(false);
                Main.resetButton.setEnabled(gui.solidExist());
                Main.pauseResumeButton.setEnabled(false);
                Main.stopSearchButton.setEnabled(false);
            }
        } else if(e.getSource() == Main.pauseResumeButton) {
            if (gui.pause) {
                Main.pauseResumeButton.setText("<html><center>Pause</center></html>");
            } else {
                Main.pauseResumeButton.setText("<html><center>Resume</center></html>");
            }
            gui.pause = !gui.pause;
        } else if (e.getSource() == Main.stopSearchButton) {
            gui.cancel = true;
            gui.pause = false;
            Main.pauseResumeButton.setText("<html><center>Pause</center></html>");

            Main.searchButton.setEnabled(true);
            Main.clearButton.setEnabled(false);
            Main.resetButton.setEnabled(gui.solidExist());
            Main.pauseResumeButton.setEnabled(false);
            Main.stopSearchButton.setEnabled(false);
        }
    }
}