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
    boolean done = true, goalReached = false, pause = false;
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

    public void initializeGrid() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col] = new Node(row, col);
                aStarGUI.add(node[row][col]);
            }
        }
    }

    public void initializeGoalStartNodes() {
        node[1][1].setStartNode();
        startNode = node[1][1];
        currentNode = startNode;
        node[10][10].setGoalNode();
        goalNode = node[10][10];
    }

    private void setCostOnNodes() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                getCost(node[row][col]);
            }
        }
    }

    private void getCost(Node node) {
        node.gCost = Math.abs(node.col - startNode.col) + Math.abs(node.row - startNode.row);
        node.hCost = Math.abs(node.col - goalNode.col) + Math.abs(node.row - goalNode.row);
        node.fCost = node.gCost + node.hCost;
        if (node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.gCost + "<br>G:" + node.hCost + "<br>H:" + node.fCost + "</html>");
        }
    }

    public void search() {
        disableAllButtons();

        SoundEffect soundEffect = null;
        try {
            soundEffect = new SoundEffect();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        SoundEffect finalSoundEffect = soundEffect;

        long start = System.nanoTime();
        done = false;
        openList = new ArrayList<>();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable stepTask = () ->  {
            if (!goalReached && !pause) {
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
                    done = true;
                    Main.stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (System.nanoTime() - start) / 1_000_000 + " ms <html>");
                    Main.stats.setVisible(true);
                    assert finalSoundEffect != null;
                    finalSoundEffect.playErrorSound();
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
            }
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
                executor.shutdown(); // Stop the algorithm after reaching the goal node
            }

            if (done) {
                Main.stats.setText("<html>A* Algorithm Complete: " + goalReached + "<br> Time Elapsed: " + (System.nanoTime() - start)/ 1_000_000 + " ms" + "<br> Total F Cost: " + totalfCost + "<html>");
                Main.stats.setVisible(true);
                assert finalSoundEffect != null;
                finalSoundEffect.playSuccessSound();
            }
        };
        executor.scheduleWithFixedDelay(stepTask, 0, MAX_SPEED - ((long) Main.speedSlider.getValue() * (MAX_SPEED - MIN_SPEED) / 100), TimeUnit.MILLISECONDS);
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        totalfCost = 0;
        currentNode = goalNode;
        while(currentNode != startNode) {
            currentNode = currentNode.parent;
            if (currentNode != startNode) {
                currentNode.setAsPath();
                totalfCost += currentNode.fCost;
            }
        }
        done = true;
    }

    private void disableAllButtons() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].disabled = true;
            }
        }
    }

    public void clearBoard() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].reset();
            }
        }
        initializeGoalStartNodes();
        goalReached = false;
        Main.stats.setVisible(false);
    }

    public void clearPathOnly() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                node[row][col].clearOnlyPath();
            }
        }
        initializeGoalStartNodes();
        goalReached = false;
        Main.stats.setVisible(false);
    }
}