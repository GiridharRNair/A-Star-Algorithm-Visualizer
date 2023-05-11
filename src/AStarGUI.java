import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AStarGUI extends JPanel {

    static final int MIN_SPEED = 1;
    static final int MAX_SPEED = 100;
    static final int DEFAULT_SPEED = 10;
    final int maxCol = 12;
    final int maxRow = 12;
    final int nodeSize = 50;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    JPanel aStarGUI;
    JPanel userInputPanel;
    JLabel description;
    JLabel stats;
    JButton searchButton;
    JButton clearButton;
    JButton resetButton;
    JSlider speedSlider;

    Node[][] node = new Node[maxRow][maxCol];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList;

    boolean done = true;
    boolean goalReached = false;
    int totalfCost;

    public AStarGUI() {

        totalfCost = 0;

        aStarGUI = new JPanel(new GridLayout(maxRow, maxCol));
        aStarGUI.setPreferredSize(new Dimension(screenWidth, screenHeight));

        userInputPanel = new JPanel();
        userInputPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userInputPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        description = new JLabel(
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

        speedSlider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        speedSlider.setFocusable(false);
        Hashtable labelTable = new Hashtable();
        labelTable.put(  MIN_SPEED, new JLabel("Slow") );
        labelTable.put(  MAX_SPEED, new JLabel("Fast") );
        speedSlider.setLabelTable( labelTable );
        speedSlider.setPaintLabels(true);

        searchButton = new JButton("<html><center>Run the visualizer</center></html>");
        searchButton.setToolTipText("Keyboard Shortcut: Enter Key");
        searchButton.setSize(new Dimension(1000, 40));
        searchButton.addActionListener(new ButtonHandler(this));
        searchButton.setFocusable(false);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearButton = new JButton("<html><center>Clear the path</center></html>");
        clearButton.setToolTipText("Keyboard Shortcut: Shift Key");
        clearButton.addActionListener(new ButtonHandler(this));
        clearButton.setFocusable(false);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        resetButton = new JButton("<html><center>Clear entire board</center></html>");
        resetButton.setToolTipText("Keyboard Shortcut: Backspace Key");
        resetButton.addActionListener(new ButtonHandler(this));
        resetButton.setFocusable(false);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        stats = new JLabel("");
        stats.setVisible(false);
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInputPanel.add(Box.createVerticalGlue()); // Add glue to center vertically
        userInputPanel.add(description);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(speedSlider);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(searchButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(clearButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.add(resetButton);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userInputPanel.add(stats);
        userInputPanel.add(Box.createVerticalGlue()); // Add glue to center vertically

        this.add(aStarGUI);
        this.add(userInputPanel, BorderLayout.WEST);
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        initializeGrid();
        setStartNode(1, 1);
        setGoalNode(10, 10);
        setCostOnNodes();

    }

    public void initializeGrid() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col] = new Node(row, col);
                aStarGUI.add(node[row][col]);
            }
        }
    }

    public void setStartNode(int row, int col) {
        node[row][col].setStartNode();
        startNode = node[row][col];
        currentNode = startNode;
    }

    public void setGoalNode(int row, int col) {
        node[row][col].setGoalNode();
        goalNode = node[row][col];
    }

    private void setCostOnNodes() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                getCost(node[row][col]);
            }
        }
    }

    private void getCost(Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;

        if (node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.gCost + "<br>G:" + node.hCost + "<br>H:" + node.fCost + "</html>");
        }
    }

    public void search() {

        long start = System.nanoTime();

        done = false;
        openList = new ArrayList<>();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable stepTask = () ->  {
            if (!goalReached) {
                currentNode.setAsChecked();
                openList.remove(currentNode);

                int row = currentNode.row;
                int col = currentNode.col;

                if (row - 1 >= 0) {
                    openNode(node[row - 1][col]);
                }
                if (row + 1 < maxRow) {
                    openNode(node[row + 1][col]);
                }
                if (col - 1 >= 0) {
                    openNode(node[row][col - 1]);
                }
                if (col + 1 < maxRow) {
                    openNode(node[row][col + 1]);
                }

                if (openList.isEmpty()) {
                    // No path found
                    executor.shutdown();
                    long end = System.nanoTime();
                    done = true;
                    stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (end - start) / 1_000_000 + " ms <html>");
                    stats.setVisible(true);
                }

                int bestNodeIndex = 0;
                int bestNodefCost = Integer.MAX_VALUE;

                for (int i = 0; i < openList.size(); i++) {
                    if (openList.get(i).fCost < bestNodefCost) {
                        bestNodeIndex = i;
                        bestNodefCost = openList.get(i).fCost;
                    } else if (openList.get(i).fCost == bestNodefCost) {
                        if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                            bestNodeIndex = i;
                        }
                    }
                }
                currentNode = openList.get(bestNodeIndex);

                if (currentNode == goalNode) {
                    goalReached = true;
                    trackThePath();
                    executor.shutdown(); // Stop the algorithm after reaching the goal node
                }

            } else {
                executor.shutdown();
                // Stop the algorithm after reaching the maximum steps
            }
            long end = System.nanoTime();
            if (done) {
                stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (end - start)/ 1_000_000 + " ms" + "<br> Total F Cost: " + totalfCost + "<html>");
                stats.setVisible(true);
            }
        };

        executor.scheduleWithFixedDelay(stepTask, 0, MAX_SPEED - (speedSlider.getValue() * (MAX_SPEED - MIN_SPEED) / 100), TimeUnit.MILLISECONDS);
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        currentNode = goalNode;
        while(currentNode != startNode) {
            currentNode = currentNode.parent;
            if (currentNode != startNode) {
                currentNode.setAsPath();
                totalfCost += currentNode.fCost;
            }
        }

        disableAllButtons();
        done = true;
    }

    private void disableAllButtons() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].disabled = true;
            }
        }
    }

    public void resetNodes() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].reset();
            }
        }

        setStartNode(1, 1);
        setGoalNode(10, 10);
        goalReached = false;
        stats.setVisible(false);
    }

    public void clearPath() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].clearPath();
            }
        }

        setStartNode(1, 1);
        setGoalNode(10, 10);
        goalReached = false;
        stats.setVisible(false);
    }
}
