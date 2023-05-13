import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    AStarGUI gui;
    public KeyHandler(AStarGUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(gui.done) {
            if (code == KeyEvent.VK_ENTER && !gui.goalReached) {
                gui.search();
            }
            if (code == KeyEvent.VK_SHIFT) {
                gui.clearPath();
            }
            if (code == KeyEvent.VK_BACK_SPACE) {
                gui.resetNodes();
            }
        }
        else if(code == KeyEvent.VK_SPACE) {
            if (gui.pause) {
                gui.pauseResumeButton.setText("<html><center>Pause</center></html>");
            } else {
                gui.pauseResumeButton.setText("<html><center>Resume</center></html>");
            }
            gui.pause = !gui.pause;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
