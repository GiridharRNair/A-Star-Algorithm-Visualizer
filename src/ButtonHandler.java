import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {
    AStarGUI gui;

    public ButtonHandler(AStarGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gui.done) {
            if (e.getSource() == gui.searchButton && !gui.goalReached) {
                gui.search();
            } else if (e.getSource() == gui.clearButton) {
                gui.clearPath();
            } else if (e.getSource() == gui.resetButton) {
                gui.resetNodes();
            }
        }
    }
}
