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
        // If the search is done
        if(gui.done) {
            // If searchButton is clicked and goal has not been reached
            if (e.getSource() == gui.searchButton && !gui.goalReached) {
                gui.search(); // Call search method in the AStarGUI
            } else if (e.getSource() == gui.clearButton) { // If clearButton is clicked
                gui.clearPath(); // Call clearPath method in the AStarGUI
            } else if (e.getSource() == gui.resetButton) { // If resetButton is clicked
                gui.resetNodes(); // Call resetNodes method in the AStarGUI
            }
        } else if(e.getSource() == gui.pauseResumeButton) { // If pauseResumeButton is clicked
            if (gui.pause) { // If pause is true
                // Set text to "Pause"
                gui.pauseResumeButton.setText("<html><center>Pause</center></html>");
            } else {
                // Set text to "Resume"
                gui.pauseResumeButton.setText("<html><center>Resume</center></html>");
            }
            // Toggle pause
            gui.pause = !gui.pause;
        }
    }
}