/**
 * A GUI class that visualizes the A* algorithm for pathfinding on a 2D grid.
 * Click on the boxes to create obstacle nodes.
 * Set iteration speed using slider.
 *
 * @author Giridhar Nair
 */

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AStarGUI extends JPanel {

    // Class constants for grid size, node size, and screen size
    static final int MIN_SPEED = 1;
    static final int MAX_SPEED = 100;
    final int maxCol = 12, maxRow = 12, nodeSize = 50, screenWidth = nodeSize * maxCol, screenHeight = nodeSize * maxRow;

    // GUI component to display the A* Algorithm
    JPanel aStarGUI;

    // 2D array to hold all nodes in the grid
    Node[][] node = new Node[maxRow][maxCol];
    // startNode and goalNode are Nodes representing the start point and goal point
    Node startNode, goalNode, currentNode;
    // openList contains all Nodes that were checked and are not solid
    ArrayList<Node> openList;

    // Flags to keep track of algorithm status
    boolean done = true, goalReached = false, pause = false, cancel;
    int totalfCost;

    /**
     * Constructor to build the GUI
     * Sets up the 2D grid of Nodes, initializes the start and goal Nodes, and sets heuristics on all nodes
     */
    public AStarGUI() {
        aStarGUI = new JPanel(new GridLayout(maxRow, maxCol));
        aStarGUI.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.add(aStarGUI);
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        initializeGrid();
        initializeGoalStartNodes();
        setCostOnNodes();
    }

    /**
     * Initializes the grid with Node objects and adds them to the AStarGUI.
     */
    public void initializeGrid() {
        for (int row = 0; row < maxRow; row++) {    // Loop through rows
            for (int col = 0; col < maxCol; col++) {    // Loop through columns
                node[row][col] = new Node(row, col);   // Create a new Node object for each cell
                aStarGUI.add(node[row][col]);   // Add the Node object to the AStarGUI
            }
        }
    }

    /**
     * Sets the start and goal nodes of the grid.
     */
    public void initializeGoalStartNodes() {
        node[1][1].setStartNode();  // Set the (1,1) Node as the start node
        startNode = node[1][1]; // Set the start node variable as the (1,1) Node
        currentNode = startNode; // Set the current node as the start node
        node[10][10].setGoalNode(); // Set the (10,10) Node as the goal node
        goalNode = node[10][10]; // Set the goal node variable as the (10,10) Node
    }

    /**
     * Calculates and sets the cost of each Node object in the grid.
     */
    private void setCostOnNodes() {
        for (int row = 0; row < maxRow; row++) {    // Loop through rows
            for (int col = 0; col < maxCol; col++) {    // Loop through columns
                getCost(node[row][col]);    // Calculate the cost of the current Node object
            }
        }
    }

    /**
     * Calculates the gCost, hCost, and fCost of a given Node object and sets its text property.
     * @param node - The Node object to calculate the costs for
     */
    private void getCost(Node node) {
        // Calculate the gCost, hCost, and fCost of the given Node object
        node.gCost = Math.abs(node.col - startNode.col) + Math.abs(node.row - startNode.row);
        node.hCost = Math.abs(node.col - goalNode.col) + Math.abs(node.row - goalNode.row);
        node.fCost = node.gCost + node.hCost;
        // If the node is not the start or goal node, set its text property with its costs
        if (node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.gCost + "<br>G:" + node.hCost + "<br>H:" + node.fCost + "</html>");
        }
    }

    /**
     * Executes the A* algorithm step by step, visualizing the search process on the grid.
     * Disables all buttons to prevent interference with the algorithm.
     */
    public void search() {
        disableAllButtons(); // Disable all buttons to prevent interference with the algorithm

        // Instantiate a SoundEffect object to play success/failure sounds when the search is complete
        SoundEffect soundEffect = null;
        try {
            soundEffect = new SoundEffect();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        SoundEffect finalSoundEffect = soundEffect; // Final reference to SoundEffect object to be used in the future

        long start = System.nanoTime(); // Get start time of the algorithm
        done = false;
        openList = new ArrayList<>(); // Initialize list of open nodes

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(); // Create a single thread executor

        Runnable stepTask = () ->  {
            if (!cancel) {
                if (!goalReached && !pause) { // If the goal has not been reached and pause has not been pressed
                    currentNode.setAsChecked(); // Set the current node as checked
                    openList.remove(currentNode); // Remove the current node from the open list

                    // Find neighbors of the current node and add them to the open list if they are not already open, checked, or solid
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

                    if (openList.isEmpty()) { // If the open list is empty, there is no path
                        executor.shutdown(); // Shutdown the executor
                        done = true; // Set done flag to true
                        Main.stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (System.nanoTime() - start) / 1_000_000 + " ms <html>");
                        Main.stats.setVisible(true); // Update stats label and make it visible
                        assert finalSoundEffect != null;
                        finalSoundEffect.playErrorSound(); // Play error sound
                    }

                    // Select the best node to explore next based on its f cost (if there are nodes with equal f costs, choose the one with lower g cost)
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
                    currentNode = openList.get(bestNodeIndex); // Set the current node to the selected node
                }
                if (currentNode == goalNode) { // If the goal node has been reached
                    goalReached = true; // Set the goalReached flag to true
                    trackThePath(); // Trace back the path
                    executor.shutdown(); // Stop the algorithm after reaching the goal node
                }

                if (done) { // If the search is complete
                    Main.stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (System.nanoTime() - start)/ 1_000_000 + " ms" + "<br> Total F Cost: " + totalfCost + "<html>");
                    Main.stats.setVisible(true); // Update and make stats label visible
                    assert finalSoundEffect != null;
                    finalSoundEffect.playSuccessSound(); // Play success sound
                }
            } else {
                executor.shutdown();
                done = true;
                cancel = false;
                clearBoard();
            }
        };
        // Schedule the stepTask to execute periodically based on the current speed setting
        executor.scheduleWithFixedDelay(stepTask, 0, MAX_SPEED - ((long) Main.speedSlider.getValue() * (MAX_SPEED - MIN_SPEED) / 100), TimeUnit.MILLISECONDS);
    }

    /**
     * Adds the given node to the openList if it is not already open, checked, or solid.
     * @param node the node to add to the openList
     */
    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Helper method to track and display the path from the goal node to the start node
     */
    private void trackThePath() {
        totalfCost = 0; // Reset the total f-cost for each traversal
        currentNode = goalNode; // Start at the goal node
        try {
            Thread.sleep(100); // Pause thread for 100 milliseconds to slow down the path visualization
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(currentNode != startNode) { // Traverse backwards from the goal node to the start node
            currentNode = currentNode.parent; // Move to the parent of the current node
            if (currentNode != startNode) { // If the current node is not the start node, set it as part of the path
                currentNode.setAsPath();
                totalfCost += currentNode.fCost; // Add the f-cost of the current node to the total f-cost
            }
        }
        done = true; // Path tracking is complete
    }

    /**
     * Helper method to disable all buttons on the board
     */
    private void disableAllButtons() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].disabled = true; // Set the 'disabled' attribute of each node to true
            }
        }
    }

    /**
     * Method to clear the entire board by resetting every node
     */
    public void clearBoard() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].reset(); // Reset each node on the board
            }
        }
        initializeGoalStartNodes(); // Re-initialize the goal and start nodes
        goalReached = false; // Reset goalReached flag to false
        Main.stats.setVisible(false); // Hide the statistics panel
    }

    /**
     * Method to clear only the path by clearing the path attribute of each node
     */
    public void clearPathOnly() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].clearOnlyPath(); // Clear only the path attribute of each node
            }
        }
        initializeGoalStartNodes(); // Re-initialize the goal and start nodes
        goalReached = false; // Reset goalReached flag to false
        Main.stats.setVisible(false); // Hide the statistics panel
    }
}