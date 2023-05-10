import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    AStarGUI gui;

    public KeyHandler(AStarGUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gui.done) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ENTER) {
                gui.search();
            }
            if (code == KeyEvent.VK_SPACE) {
                gui.resetNodes();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

}
