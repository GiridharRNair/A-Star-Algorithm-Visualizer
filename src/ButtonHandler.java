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
            if (e.getSource() == Main.searchButton && !gui.goalReached) {
                gui.search(); // Call search method in the AStarGUI
            }
            else if (e.getSource() == Main.clearButton) { // If clearButton is clicked
                gui.clearPathOnly(); // Call clearPath method in the AStarGUI
            }
            else if (e.getSource() == Main.resetButton) { // If resetButton is clicked
                gui.clearBoard(); // Call resetNodes method in the AStarGUI
            }

        } else if(e.getSource() == Main.pauseResumeButton) { // If pauseResumeButton is clicked

            if (gui.pause) { // If pause is true
                Main.pauseResumeButton.setText("<html><center>Pause</center></html>"); // Set text to "Pause"
            } else {
                Main.pauseResumeButton.setText("<html><center>Resume</center></html>"); // Set text to "Resume"
            }

            // Toggle pause
            gui.pause = !gui.pause;
        }
    }
}