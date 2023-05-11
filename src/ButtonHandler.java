import java.awt.*;
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
            if (e.getSource() == gui.search && !gui.goalReached) {
                gui.search();
            } else if (e.getSource() == gui.clear) {
                gui.clearPath();
            } else if (e.getSource() == gui.reset) {
                gui.resetNodes();
            }
        }
    }
}
