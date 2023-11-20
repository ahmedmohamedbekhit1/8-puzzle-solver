
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class Main extends JFrame {
    private static final int TILE_SIZE = 100;
    private static final int BOARD_SIZE = 3;
    private static final int WINDOW_WIDTH = TILE_SIZE * BOARD_SIZE + 400;
    private static final int WINDOW_HEIGHT = TILE_SIZE * BOARD_SIZE + 150;
    private static final String WINDOW_TITLE = "8 Puzzle Solver";

    private JButton[][] tiles;
    private int[][] currentState;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton aStarButton;
    private CustomTextArea initialSetField;
    private CustomTextArea goalSetField;
    private JButton applyButton;
    private JButton previousButton;
    private JButton nextButton;
    private Stack<int[][]> previousStates;
    private Queue<int[][]> nextStates;
    private JTextArea iterationsTextArea;


    public Main() {
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentState = new int[BOARD_SIZE][BOARD_SIZE];
        currentState[0][0] = 1;
        currentState[0][1] = 2;
        currentState[0][2] = 3;
        currentState[1][0] = 4;
        currentState[1][1] = 0;
        currentState[1][2] = 5;
        currentState[2][0] = 7;
        currentState[2][1] = 8;
        currentState[2][2] = 6;
        setLayout(new BorderLayout());
        try {
            Image backgroundImage = ImageIO.read(new File("src/image_background.jpg"));
            setContentPane(new ImagePanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);

        JLabel label = new JLabel("initialSet :");
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        inputPanel.add(label);
        try {
            Image inputBackgroundImage = ImageIO.read(new File("src/backbox.png"));
            initialSetField = new CustomTextArea();
            initialSetField.setBackgroundImage(inputBackgroundImage);
            initialSetField.setText("1 2 3 4 0 5 7 8 6");
        } catch (IOException e) {

        }

        initialSetField.setFont(new Font("Arial", Font.BOLD, 36));
        initialSetField.setPreferredSize(new Dimension(WINDOW_WIDTH / 2, 10));
        initialSetField.setMargin(new Insets(35, 45, 10, 10));

        inputPanel.add(initialSetField);
        JLabel label2 = new JLabel("goalSet :");
        label2.setForeground(Color.white);
        label2.setFont(new Font("Arial", Font.BOLD, 16));
        label2.setHorizontalTextPosition(SwingConstants.LEFT);
        inputPanel.add(label2);
        try {
            Image inputBackgroundImage = ImageIO.read(new File("src/backbox.png"));
            goalSetField = new CustomTextArea();
            goalSetField.setBackgroundImage(inputBackgroundImage);
            goalSetField.setText("1 2 3 4 5 6 7 8 0");


        } catch (IOException e) {

        }

        goalSetField.setFont(new Font("Arial", Font.BOLD, 36));
        goalSetField.setPreferredSize(new Dimension(WINDOW_WIDTH / 2, 10));
        goalSetField.setMargin(new Insets(35, 45, 10, 10));
        inputPanel.add(goalSetField);

        applyButton = new JButton("Apply");
        applyButton.setFont(new Font("Arial", Font.BOLD, 24));

        applyButton.setIcon(new ImageIcon("src/button.png"));
        applyButton.setForeground(Color.BLACK);

        applyButton.setVerticalTextPosition(SwingConstants.CENTER);
        applyButton.setHorizontalTextPosition(SwingConstants.CENTER);
        applyButton.setOpaque(false);
        applyButton.setContentAreaFilled(false);
        applyButton.setBorderPainted(false);
        applyButton.setPreferredSize(new Dimension(150, 50));

        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });
        inputPanel.add(applyButton);


        inputPanel.add(applyButton);

        mainPanel.add(inputPanel, BorderLayout.EAST);

        JPanel puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        puzzlePanel.setOpaque(false);
        tiles = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                int value = currentState[row][col];
                ImageIcon icon = new ImageIcon("src/back.png");
                Image image = icon.getImage();

                // Resize the image to fit within JButton
                Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                tiles[row][col] = new JButton(String.valueOf(value));

                tiles[row][col].setIcon(resizedIcon);
                tiles[row][col].setVerticalTextPosition(SwingConstants.CENTER);
                tiles[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
                tiles[row][col].setOpaque(false);
                tiles[row][col].setFont(new Font("Arial", Font.BOLD, 32));
                tiles[row][col].setContentAreaFilled(false);
                tiles[row][col].setBorderPainted(false);
                tiles[row][col].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                puzzlePanel.add(tiles[row][col]);
            }
        }

        mainPanel.add(puzzlePanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        bfsButton = new JButton("BFS");
        bfsButton.setFont(new Font("Arial", Font.BOLD, 24));
        bfsButton.setIcon(new ImageIcon("src/button.png"));
        bfsButton.setForeground(Color.BLACK);

        bfsButton.setVerticalTextPosition(SwingConstants.CENTER);
        bfsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        bfsButton.setOpaque(false);
        bfsButton.setContentAreaFilled(false);
        bfsButton.setBorderPainted(false);
        bfsButton.setPreferredSize(new Dimension(150, 50));

        bfsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bfsButtonActionPerformed(evt);
            }
        });


        dfsButton = new JButton("DFS");
        dfsButton.setFont(new Font("Arial", Font.BOLD, 24));
        dfsButton.setIcon(new ImageIcon("src/button.png"));
        dfsButton.setForeground(Color.BLACK);

        dfsButton.setVerticalTextPosition(SwingConstants.CENTER);
        dfsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        dfsButton.setOpaque(false);
        dfsButton.setContentAreaFilled(false);
        dfsButton.setBorderPainted(false);
        dfsButton.setPreferredSize(new Dimension(150, 50));
        dfsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dfsButtonActionPerformed(evt);
            }
        });

        aStarButton = new JButton("A*");
        aStarButton.setFont(new Font("Arial", Font.BOLD, 24));
        aStarButton.setIcon(new ImageIcon("src/button.png"));
        aStarButton.setForeground(Color.BLACK);

        aStarButton.setVerticalTextPosition(SwingConstants.CENTER);
        aStarButton.setHorizontalTextPosition(SwingConstants.CENTER);
        aStarButton.setOpaque(false);
        aStarButton.setContentAreaFilled(false);
        aStarButton.setBorderPainted(false);
        aStarButton.setPreferredSize(new Dimension(150, 50));

        aStarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aStarButtonActionPerformed(evt);
            }
        });
        JButton nextAllButton = new JButton("outomatic");
        nextAllButton.setFont(new Font("Arial", Font.BOLD, 24));
        nextAllButton.setIcon(new ImageIcon("src/button.png"));
        nextAllButton.setForeground(Color.BLACK);

        nextAllButton.setVerticalTextPosition(SwingConstants.CENTER);
        nextAllButton.setHorizontalTextPosition(SwingConstants.CENTER);
        nextAllButton.setOpaque(false);
        nextAllButton.setContentAreaFilled(false);
        nextAllButton.setBorderPainted(false);
        nextAllButton.setPreferredSize(new Dimension(150, 50));

        nextAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextAllButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(nextAllButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(aStarButton);

        previousButton = new JButton("Previous", new ImageIcon("src/previous.png"));
        previousButton.setForeground(Color.white);
        previousButton.setFont(new Font("Arial", Font.BOLD, 16));
        previousButton.setContentAreaFilled(false);
        previousButton.setBorderPainted(false);
        previousButton.setVerticalTextPosition(SwingConstants.CENTER);
        previousButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        nextButton = new JButton("Next", new ImageIcon("src/next.png"));
        nextButton.setContentAreaFilled(false);
        nextButton.setBorderPainted(false);
        nextButton.setForeground(Color.white);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setVerticalTextPosition(SwingConstants.CENTER);
        nextButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        CustomTextArea iterationsTextArea = new CustomTextArea(2, 15);

        iterationsTextArea.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        int topPadding = 90;
        int leftPadding = 50;


        iterationsTextArea.setMargin(new Insets(topPadding, leftPadding, 50, 70));
        try {
            iterationsTextArea.setBackgroundImage(ImageIO.read(new File("src/backbox5.png")));
        } catch (IOException e) {

        }
        iterationsTextArea.setEditable(false);


        this.iterationsTextArea = iterationsTextArea;

        iterationsTextArea.setOpaque(false);
        iterationsTextArea.setText("");
        iterationsTextArea.setAutoscrolls(true);
        CustomScrollPane iterationsScrollPane = new CustomScrollPane(iterationsTextArea);

        iterationsScrollPane.setVerticalScrollBarPolicy(CustomScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        iterationsTextArea.setLineWrap(true);
        iterationsScrollPane.getPreferredSize();


        iterationsScrollPane.setAutoscrolls(true);

        iterationsScrollPane.getViewport().setOpaque(false);

        iterationsScrollPane.setViewportView(iterationsTextArea);
        iterationsScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        setVisible(true);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textPanel.add(iterationsScrollPane, BorderLayout.EAST);

        textPanel.add(mainPanel, BorderLayout.WEST);

        add(textPanel);
        pack(); // Pack the components together without resizing the window
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);

        previousStates = new Stack<>();
        nextStates = new LinkedList<>();
        updatePuzzleButtons(currentState);
    }

    private void nextAllButtonActionPerformed(ActionEvent evt) {
        if (!result.equals("FAILURE")) {
            String message = result;
            JOptionPane.showMessageDialog(this, message, " Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, " Result: FAILURE", " Result", JOptionPane.ERROR_MESSAGE);
        }

        iterationsTextArea.setText("");

        StringBuilder sb = new StringBuilder();
        for (int[][] state : nextStates) {
            sb.append(Arrays.deepToString(state)).append("\n");
        }

        iterationsTextArea.setText(sb.toString());
        iterationsTextArea.setCaretPosition(iterationsTextArea.getDocument().getLength());
    }

    public class CustomScrollPane extends JScrollPane {
        private Image backgroundImage;

        public CustomScrollPane(Component view) {
            super(view);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
        }

        public void setBackgroundImage(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Draw the background image
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            // Render the viewport
            super.paintComponent(g);
        }
    }

    public class CustomTextArea extends JTextArea {
        private Image backgroundImage;

        public CustomTextArea() {
            setOpaque(false);
            setTransferHandler(new TransferHandler("text"));
        }

        public CustomTextArea(int i, int i1) {
            super(i, i1);
        }

        public void setBackgroundImage(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Draw the background image
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            // Render the text
            super.paintComponent(g);
        }
    }

    private void applyButtonActionPerformed(ActionEvent evt) {
        String initialSet = initialSetField.getText();
        String goalSet = goalSetField.getText();


        if (!isValidInputSet(initialSet) || !isValidInputSet(goalSet)) {
            JOptionPane.showMessageDialog(this, "Invalid input sets. Please use numbers from 0 to 8.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }



        previousStates.push(currentState);
        currentState = parseInputSet(initialSet);

        updatePuzzleButtons(currentState);
        if (!isSolvable(currentState)) {
            JOptionPane.showMessageDialog(this, "Unsolvable puzzle. Please enter a solvable puzzle.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        nextStates.clear();

        System.out.println("Initial Set: " + initialSet);
        System.out.println("Goal Set: " + goalSet);
    }

    private boolean isValidInputSet(String inputSet) {
        String[] numbers = inputSet.trim().split("\\s+");

        for (String number : numbers) {
            try {
                int value = Integer.parseInt(number);
                if (value < 0 || value > 8) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private void nextButtonActionPerformed(ActionEvent evt) {
        // Go forward to the next state


        System.out.println("Next button clicked");
        if (!nextStates.isEmpty()) {
            previousStates.push(currentState);
            currentState = nextStates.remove();
            updatePuzzleButtons(currentState);
            if (iterationsTextArea.getText().equals("")) {
                iterationsTextArea.setText(Arrays.deepToString(currentState));

            } else {
                iterationsTextArea.setText(iterationsTextArea.getText() + "\n" + Arrays.deepToString(currentState));

            }

        }


        if (isEqual(currentState, parseInputSet(goalSetField.getText()))) {
            if (!result.equals("FAILURE")) {
                String message = result;
                JOptionPane.showMessageDialog(this, message, " Result", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(this, " Result: FAILURE", " Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void previousButtonActionPerformed(ActionEvent evt) {
        // Go back to the previous state
        System.out.println("Previous button clicked");
        if (!previousStates.isEmpty()) {
            nextStates.add(currentState);
            currentState = previousStates.pop();
            updatePuzzleButtons(currentState);
        }
    }
    int nodesExpanded;  int searchDepth; long runningTime; long startTime;
    private void aStarButtonActionPerformed(ActionEvent evt) {
        nextStates.clear();
        currentState = parseInputSet(initialSetField.getText());



        startTime = System.nanoTime();

        result = aStarSearch(currentState, parseInputSet(goalSetField.getText()));

        System.out.println("A* Result: " + result);

        iterationsTextArea.setText("");


    }

    private String aStarSearch(int[][] initialState, int[][] goalTest) {
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        HashSet<Node> explored = new HashSet<>();

        int heuristicCost = calculateHeuristic(initialState, goalTest);
        Node initialNode = new Node(initialState, heuristicCost);

        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
            explored.add(currentNode);
            nextStates.add(currentNode.state);

            if (isEqual(currentNode.state, goalTest)) {
                // Get the number of nodes expanded
                nodesExpanded = nextStates.size();
                // The search depth is equal to the cost
                searchDepth = currentNode.cost - currentNode.heuristicCost;
                // Calculate the running time in nanoseconds

                runningTime = System.nanoTime() - startTime;
                return "SUCCESS\nCost: " + (currentNode.cost - currentNode.heuristicCost)+"\n" +
                        "Nodes Expanded: " + nodesExpanded + "\n" +
                        "Search Depth: " + searchDepth + "\n" +
                        "Running Time: " + runningTime + " ns"; // Show the success message with cost

            }

            ArrayList<int[][]> neighbors = generateNeighbors(currentNode.state);

            for (int[][] neighbor : neighbors) {
                int cost = currentNode.cost + 1;

                heuristicCost = calculateHeuristic(neighbor, goalTest);
                Node nextNode = new Node(neighbor, cost, heuristicCost, currentNode);

                if (explored.contains(nextNode)) {
                    continue;
                }

                if (!frontier.contains(nextNode)) {
                    frontier.add(nextNode);
                } else {
                    // If the neighbor is already in the frontier, update its cost if the new cost is lower
                    for (Node n : frontier) {
                        if (nextNode.equals(n) && nextNode.cost < n.cost) {
                            frontier.remove(n);
                            frontier.add(nextNode);
                            break;
                        }
                    }
                }
            }
        }

        return "FAILURE";
    }

    class Node implements Comparable<Node> {
        int[][] state;
        int cost;
        int heuristicCost;
        Node parent;

        public Node(int[][] state, int cost) {
            this.state = state;
            this.cost = cost;
            this.heuristicCost = 0;
            this.parent = null;
        }

        public Node(int[][] state, int cost, int heuristicCost, Node parent) {
            this.state = state;
            this.cost = cost;
            this.heuristicCost = heuristicCost;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node other) {
            int priority1 = this.cost + this.heuristicCost;
            int priority2 = other.cost + other.heuristicCost;
            return priority1 - priority2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return Arrays.deepEquals(state, other.state);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(state);
        }
    }

    private int calculateHeuristic(int[][] state, int[][] goal) {
        int heuristic = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int value = state[i][j];

                if (value != 0) {
                    int goalRow = -1;
                    int goalCol = -1;

                    for (int k = 0; k < goal.length; k++) {
                        for (int l = 0; l < goal[k].length; l++) {
                            if (value == goal[k][l]) {
                                goalRow = k;
                                goalCol = l;
                                break;
                            }
                        }
                    }

                    heuristic += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }

        return heuristic;
    }

    private void dfsButtonActionPerformed(ActionEvent evt) {
        nextStates.clear();
        currentState = parseInputSet(initialSetField.getText());

        long startTime = System.nanoTime();


        result = depthFirstSearch(currentState, parseInputSet(goalSetField.getText()), new HashSet<>());

        System.out.println("DFS Result: " + result);

        iterationsTextArea.setText("");


    }

    private String depthFirstSearch(int[][] currentState, int[][] goalState, HashSet<String> explored) {
        Stack<StateNode> stack = new Stack<>();

        stack.push(new StateNode(currentState, 0)); // Initialize depth as 0

        while (!stack.isEmpty()) {
            StateNode node = stack.pop();
            int[][] state = node.state;
            int depth = node.depth;
            nextStates.add(state);
            if (isEqual(state, goalState)) {
                int cost = depth;
                int nodesExpanded = nextStates.size(); // Get the number of nodes expanded

                long runningTime = System.nanoTime() - startTime; // Calculate the running time in nanoseconds
                return "SUCCESS"+"\n" +
                        "Cost of Path: " + cost + "\n" +
                        "Nodes Expanded: " + nodesExpanded + "\n" +
                        "Search Depth: " + depth + "\n" +
                        "Running Time: " + runningTime + " ns";
            }

            String stateString = getStateString(state);
            if (explored.contains(stateString)) {
                continue; // Skip already explored state
            }

            explored.add(stateString);

            ArrayList<int[][]> neighbors = generateNeighbors(state);
            neighbors.sort(Comparator.comparingInt(n -> estimateDistance(n, goalState)));

            for (int[][] neighbor : neighbors) {
                stack.push(new StateNode(neighbor, depth + 1)); // Increase depth by 1
            }
        }

        return "FAILURE";
    }

    private class StateNode {
        int[][] state;
        int depth;

        public StateNode(int[][] state, int depth) {
            this.state = state;
            this.depth = depth;
        }
    }


    private String getStateString(int[][] state) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                sb.append(state[i][j]);
            }
        }

        return sb.toString();
    }
    private int estimateDistance(int[][] state, int[][] goalState) {
        int distance = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int element = state[i][j];
                int[] goalPosition = findElementPosition(goalState, element);

                distance += Math.abs(i - goalPosition[0]) + Math.abs(j - goalPosition[1]);
            }
        }

        return distance;
    }

    private int[] findElementPosition(int[][] state, int element) {
        int[] position = new int[2];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == element) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }

        return position;
    }


    String result;
    private void bfsButtonActionPerformed(ActionEvent evt) {
        nextStates.clear();
        currentState = parseInputSet(initialSetField.getText());


        long startTime = System.nanoTime();

        result = breadthFirstSearch(currentState, parseInputSet(goalSetField.getText()));

        System.out.println("BFS Result: " + result);

        iterationsTextArea.setText("");


    }
    public int[][] state;
    Queue<int[][]> frontier;
    Set<int[][]> explored;
    Map<int[][], Integer> costMap;

    private String breadthFirstSearch(int[][] initialState, int[][] goalTest) {
        frontier = new LinkedList<>();

        BitSet explored = new BitSet();
        costMap = new HashMap<>();

        frontier.add(initialState);
        explored.set(getStateIndex(initialState));
        costMap.put(initialState, 0);

        while (!frontier.isEmpty()) {
            state = frontier.poll();
            nextStates.add(state);

            int cost = costMap.get(state);

            if (isEqual(state, goalTest)) {

                int nodesExpanded = nextStates.size(); // Get the number of nodes expanded
                int searchDepth = cost; // The search depth is equal to the cost
                long runningTime = System.nanoTime() - startTime; // Calculate the running time in nanoseconds
                return "SUCCESS\n" +
                        "Cost of Path: " + cost + "\n" +
                        "Nodes Expanded: " + nodesExpanded + "\n" +
                        "Search Depth: " + searchDepth + "\n" +
                        "Running Time: " + runningTime + " ns";
            }

            for (int[][] neighbor : generateNeighbors(state)) {
                int index = getStateIndex(neighbor);
                if (!explored.get(index)) {
                    frontier.add(neighbor);
                    explored.set(index);
                    costMap.put(neighbor, cost + 1);
                }
            }
        }

        return "FAILURE";
    }

    // method to get the index for the state in the BitSet
    private int getStateIndex(int[][] state) {
        int index = 0;
        int multiplier = 1;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                index += state[i][j] * multiplier;
                multiplier *= 10;
            }
        }

        return index;
    }
    private boolean isEqual(int[][] state1, int[][] state2) {
        for (int i = 0; i < state1.length; i++) {
            for (int j = 0; j < state1[i].length; j++) {
                if (state1[i][j] != state2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    private ArrayList<int[][]> generateNeighbors(int[][] state) {
        ArrayList<int[][]> neighbors = new ArrayList<>();
        int zeroRow = -1;
        int zeroCol = -1;

        // Find the position of the zero (empty) tile
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (state[row][col] == 0) {
                    zeroRow = row;
                    zeroCol = col;
                    break;
                }
            }
        }

        // Generate neighbors by moving the zero tile in different directions
        if (zeroRow > 0) {
            int[][] neighbor = copyState(state);
            neighbor[zeroRow][zeroCol] = neighbor[zeroRow-1][zeroCol];
            neighbor[zeroRow-1][zeroCol] = 0;
            neighbors.add(neighbor);
        }

        if (zeroRow < BOARD_SIZE - 1) {
            int[][] neighbor = copyState(state);
            neighbor[zeroRow][zeroCol] = neighbor[zeroRow+1][zeroCol];
            neighbor[zeroRow+1][zeroCol] = 0;
            neighbors.add(neighbor);
        }

        if (zeroCol > 0) {
            int[][] neighbor = copyState(state);
            neighbor[zeroRow][zeroCol] = neighbor[zeroRow][zeroCol-1];
            neighbor[zeroRow][zeroCol-1] = 0;
            neighbors.add(neighbor);
        }

        if (zeroCol < BOARD_SIZE - 1) {
            int[][] neighbor = copyState(state);
            neighbor[zeroRow][zeroCol] = neighbor[zeroRow][zeroCol+1];
            neighbor[zeroRow][zeroCol+1] = 0;
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    private int[][] copyState(int[][] state) {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                copy[row][col] = state[row][col];
            }
        }

        return copy;
    }
    private int[][] parseInputSet(String inputSet) {
        int[][] puzzle = new int[BOARD_SIZE][BOARD_SIZE];
        String[] numbers = inputSet.trim().split("\\s+");
        int index = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                puzzle[row][col] = Integer.parseInt(numbers[index]);
                index++;
            }
        }

        return puzzle;
    }

    private void updatePuzzleButtons(int[][] newState) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = newState[row][col];
                tiles[row][col].setText(String.valueOf(value));
            }
        }
    }
    private boolean isSolvable(int[][] puzzle) {
        int[] array = new int[9];
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                array[index++] = puzzle[row][col];
            }
        }

        int inversionCount = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j] && array[i] != 0 && array[j] != 0) {
                    inversionCount++;
                }
            }
        }

        return inversionCount % 2 == 0;
    }
}



class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
