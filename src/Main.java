/**
 * This class is responsible for initializing the GUI for the AStarGUI class.
 * @author Giridhar Nair
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Taskbar;

public class Main {

    public static void main(String[] args) {

        //Create the A* Visualizer object
        AStarGUI aStarVisualizer = new AStarGUI();

        //Use FlatDarkLaf look and feel
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create new JFrame window
        JFrame window = new JFrame("A* Algorithm Visualizer");

        //Set close operation, resizable, and layout
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new FlowLayout());

        //Load icon image and set as taskbar icon (if on MacOS) and window icon
        final Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("AStarIcon.png"));
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
        window.setIconImage(image);

        //Set window background and add an AStarGUI object
        window.setBackground(Color.BLACK);
        window.add(aStarVisualizer);
        window.add(Box.createVerticalGlue());

        //Pack and center window, then make visible
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}