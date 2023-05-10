import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set the look and feel of the user interface
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Print the stack trace of the exception if the look and feel could not be set
            e.printStackTrace();
        }

        JFrame window = new JFrame("A* Algorithm Visualizer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(new AStarGUI());

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}