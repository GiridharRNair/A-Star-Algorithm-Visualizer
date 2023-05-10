import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AStarGUI extends JPanel {

    final int maxCol = 12;
    final int maxRow = 12;
    final int nodeSize = 60;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    Node[][] node = new Node[maxRow][maxCol];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList;

    boolean done = true;
    boolean goalReached = false;

    public AStarGUI() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
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
                add(node[row][col]);
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
        done = false;
        final int[] step = {0};
        openList = new ArrayList<>();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable stepTask = () -> {

            if (step[0] >= 144 || goalReached) {
                executor.shutdown();
            }

            currentNode.setAsChecked();
            openList.remove(currentNode);

            int row = currentNode.row;
            int col = currentNode.col;

            if (row - 1 > 0) {
                openNode(node[row - 1][col]);
            }
            if (row + 1 < maxRow) {
                openNode(node[row + 1][col]);
            }
            if (col - 1 > 0) {
                openNode(node[row][col - 1]);
            }

            if (col + 1 < maxRow) {
                openNode(node[row][col + 1]);
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
            }

            step[0]++;
        };

        executor.scheduleWithFixedDelay(stepTask, 0, 5, TimeUnit.MILLISECONDS);
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        Node current = goalNode;
        while(current != startNode) {
            current = current.parent;
            if (current != startNode) {
                current.setAsPath();
            }
        }

        done = true;
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
    }
}
