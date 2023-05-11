import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame window = new JFrame("A* Algorithm Visualizer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new FlowLayout());

        final Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("AStarIcon.png"));
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
        window.setIconImage(image);

        window.setBackground(Color.BLACK);
        window.add(new AStarGUI());
        window.add(Box.createVerticalGlue());

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}