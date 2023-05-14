/**
 * This class is responsible for initializing the GUI for the AStarGUI class.
 * @author Giridhar Nair
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Taskbar;
import java.util.Hashtable;

public class Main {

    static final int MIN_SPEED = 1;
    static final int MAX_SPEED = 100;
    public static MyJSlider speedSlider;
    public static JLabel stats;
    public static JButton searchButton;
    public static JButton clearButton;
    public static JButton resetButton;
    public static JButton pauseResumeButton;
    public static JButton stopSearchButton;

    public static void main(String[] args) {

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

        //Load icon image and set as taskbar icon (if on macOS) and window icon
        final Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("AStarIcon.png"));
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            Taskbar.getTaskbar().setIconImage(image);
        }
        window.setIconImage(image);

        //Create an A* Algorithm GUI
        AStarGUI aStar = new AStarGUI();

        JPanel userInputPanel = new JPanel();
        userInputPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel(
                "<html><center>" +
                        "The A* algorithm is a powerful search algorithm used to" +
                        "<br> efficiently find the shortest path between two points." +
                        "<br> It is commonly applied in map traversal scenarios to " +
                        "<br> determine the most efficient route to reach a destination." +
                        "<br><br>Click on the boxes to create obstacle nodes. <br>" +
                        "<br> Set Iteration Speed" +
                        "</center></html>"
        );
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        speedSlider = new MyJSlider(MIN_SPEED, MAX_SPEED);
        speedSlider.setFocusable(false);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(  MIN_SPEED, new JLabel("Slow") );
        labelTable.put(  MAX_SPEED, new JLabel("Fast") );
        speedSlider.setLabelTable( labelTable );
        speedSlider.setPaintLabels(true);

        searchButton = new JButton("<html><center>Run the visualizer</center></html>");
        searchButton.setToolTipText("Keyboard Shortcut: Enter Key");
        searchButton.setSize(new Dimension(1000, 40));
        searchButton.addActionListener(new ButtonHandler(aStar));
        searchButton.setFocusable(false);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearButton = new JButton("<html><center>Clear the path</center></html>");
        clearButton.setToolTipText("Keyboard Shortcut: Shift Key");
        clearButton.addActionListener(new ButtonHandler(aStar));
        clearButton.setFocusable(false);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        resetButton = new JButton("<html><center>Clear entire board</center></html>");
        resetButton.setToolTipText("Keyboard Shortcut: Backspace Key");
        resetButton.addActionListener(new ButtonHandler(aStar));
        resetButton.setFocusable(false);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pauseResumeButton = new JButton("<html><center>Pause</center></html>");
        pauseResumeButton.setToolTipText("Keyboard Shortcut: Space Key");
        pauseResumeButton.addActionListener(new ButtonHandler(aStar));
        pauseResumeButton.setFocusable(false);
        pauseResumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        stopSearchButton = new JButton("<html><center>Stop Search</center></html>");
        stopSearchButton.setToolTipText("Keyboard Shortcut: X Key");
        stopSearchButton.addActionListener(new ButtonHandler(aStar));
        stopSearchButton.setFocusable(false);
        stopSearchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        stats = new JLabel((String) null);
        stats.setVisible(false);
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInputPanel.add(description);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(speedSlider);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(searchButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(clearButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(resetButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(pauseResumeButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(stopSearchButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userInputPanel.add(stats);

        //Set window background and add an AStarGUI object
        window.setBackground(Color.BLACK);
        window.add(aStar);
        window.add(userInputPanel);
        window.add(Box.createVerticalGlue());

        //Pack and center window, then make visible
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}