# 8 Puzzle Solver

[gui_design](gui_design)

This is a Java Swing application that solves the 8-puzzle problem using various search algorithms such as Breadth First Search (BFS), Depth First Search (DFS), and A* Search.

The 8-puzzle problem is a sliding puzzle that consists of a 3x3 grid with 8 numbered tiles and one blank space. The goal is to arrange the tiles in the proper numerical order by sliding the tiles into the blank space.

## Features

- Allows users to input the initial and goal states of the puzzle.
- Provides a graphical representation of the puzzle board.
- Implements various search algorithms to find the shortest path to the goal state.
- Displays the number of nodes expanded, search depth, and running time for each algorithm.
- Allows users to manually step through the solution or automatically solve the puzzle.

## How to Use

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Build and run the application.
4. Enter the initial set and goal set of the puzzle in the provided text fields.
5. Click the "Apply" button to set the initial state.
6. Select a search algorithm (BFS, DFS, or A*) and click the corresponding button to start the search.
7. Follow the on-screen instructions to step through the solution or click the "outomatic" button to automatically solve the puzzle.
8. The results, including the cost, nodes expanded, search depth, and running time, will be displayed in the text area.

## Requirements

- Java Development Kit (JDK) 8 or newer
- Java Swing Library

## Contributing

Contributions to this project are welcome. Feel free to open issues and submit pull requests to suggest improvements or add new features.

## License

This project is licensed under the MIT License. You are free to use, modify, and distribute the code as permitted by the license. See the [LICENSE](LICENSE) file for more details.

## Explaination

The code provided is a Java program that implements an 8 Puzzle Solver. The program uses various search algorithms such as Breadth First Search (BFS), Depth First Search (DFS), and A* Search to find the optimal solution to the 8 Puzzle problem.

The program provides a graphical user interface (GUI) where the user can input their initial and goal sets for the puzzle. The program then uses the selected search algorithm to find the solution and provides information about the search process, including the cost of the path, number of nodes expanded, search depth, and running time.

To run the program, you can compile and execute the Java code in a Java development environment such as Eclipse or IntelliJ IDEA. The GUI will open, allowing you to input the initial and goal sets for the puzzle and select the search algorithm. You can then click on the "Apply" button to apply the input sets and start the search, and use the "Next" and "Previous" buttons to navigate through the search process. The "outomatic" button allows you to see the complete search process automatically. The program also provides an area to display the iterations and results.

Please note that the program requires images for the GUI background and buttons. You should provide the necessary image files in the specified locations ("src/image_background.jpg", "src/backbox.png", "src/back.png", "src/button.png", "src/previous.png", "src/next.png", "src/backbox5.png") for the program to run without any errors.

Additionally, the program includes a README and LICENSE file. These files provide additional information about the program and the license terms under which it is distributed.
The code provided is a Java Swing application that solves the 8-puzzle problem using various search algorithms such as Breadth First Search (BFS), Depth First Search (DFS), and A* search.

The application provides a graphical user interface (GUI) that allows the user to input the initial and goal states of the puzzle. The GUI also displays the puzzle board and buttons to perform the search algorithms.

Here is an overview of the code:

1. The Main class extends JFrame and sets up the GUI components such as buttons, text areas, and puzzle tiles.

2. The CustomTextArea and CustomScrollPane classes are custom subclasses of JTextArea and JScrollPane, respectively, that are used to customize the appearance of the text areas in the GUI.

3. The applyButtonActionPerformed method handles the logic when the Apply button is clicked. It validates the input sets, updates the puzzle state, and checks if the puzzle is solvable.

4. The nextButtonActionPerformed method handles the logic when the Next button is clicked. It advances to the next state in the solution path.

5. The previousButtonActionPerformed method handles the logic when the Previous button is clicked. It goes back to the previous state in the solution path.

6. The bfsButtonActionPerformed, dfsButtonActionPerformed, and aStarButtonActionPerformed methods handle the logic when the BFS, DFS, and A* buttons are clicked, respectively. They perform the respective search algorithms and display the results.

7. The breadthFirstSearch, depthFirstSearch, and aStarSearch methods implement the BFS, DFS, and A* search algorithms, respectively.

8. The Node class represents a node in the search tree and contains the current state, cost, heuristic cost, and parent node.

9. The calculateHeuristic method calculates the heuristic cost for a given state using the Manhattan distance heuristic.

10. The generateNeighbors method generates all possible neighbor states from a given state by moving the empty tile (represented by 0) in different directions.

11. The copyState method creates a copy of a given state.

12. The parseInputSet method parses the input set string into a 2D array representing the puzzle state.

13. The updatePuzzleButtons method updates the GUI puzzle buttons with the current state.

14. The isSolvable method checks if a given puzzle state is solvable by counting the number of inversions.

15. The main method creates an instance of the Main class and runs the application.

Overall, the code provides a functional and visually appealing GUI application for solving the 8-puzzle problem using various search algorithms.
Sure! Let's start by explaining the algorithms used in the code to solve the 8-puzzle problem.

1. Breadth First Search (BFS):
BFS is an uninformed search algorithm that explores all the vertices of a graph in breadth-first order. In the context of the 8-puzzle problem, BFS explores all possible puzzle states level by level to find the shortest path to the goal state. It maintains a queue to store the states to be explored. The algorithm starts with the initial state and keeps expanding the states by generating their neighbors until the goal state is found or all the states have been explored.

2. Depth First Search (DFS):
DFS is another uninformed search algorithm that explores the vertices of a graph in depth-first order. In the 8-puzzle problem, DFS explores the states in a depth-first manner by recursively visiting the states and backtracking when a dead end is reached. It uses a stack to store the states to be explored. DFS does not consider the optimality of a solution path, so it may not find the shortest path to the goal state.

3. A* Search:
A* search is an informed search algorithm that combines the advantages of both BFS and DFS. It uses a heuristic function to estimate the cost of reaching the goal state from a given state. In the 8-puzzle problem, the heuristic function used is the Manhattan distance, which calculates the sum of the distances between each tile in the current state and its position in the goal state. A* search maintains a priority queue (implemented as a min-heap) to prioritize the states to be explored based on their total cost, which is the sum of the current cost and the heuristic cost. This ensures that the states with lower total costs are explored first, leading to an optimal solution.

Now let's go through the code and explain the implementation of these algorithms.

1. BFS Algorithm:
The breadthFirstSearch method implements the BFS algorithm. It uses a queue (frontier) to store the states to be explored and a set (explored) to keep track of the states that have been visited. The algorithm starts by adding the initial state to the frontier and marking it as explored. Then, it enters a loop that continues until the frontier is empty. In each iteration, it dequeues a state from the frontier, generates its neighbors, and adds them to the frontier if they haven't been explored yet. It also maintains a cost map to keep track of the cost of reaching each state from the initial state. If the goal state is found, the algorithm calculates the number of nodes expanded, the search depth, and the running time and returns the result.

2. DFS Algorithm:
The depthFirstSearch method implements the DFS algorithm. It uses a stack (frontier) to store the states to be explored and a set (explored) to keep track of the visited states. The algorithm starts by pushing the initial state to the frontier and marking it as explored. Then, it enters a loop that continues until the frontier is empty. In each iteration, it pops a state from the frontier, generates its neighbors, and pushes them to the frontier if they haven't been explored yet. It also maintains a depth variable to keep track of the depth of the current state. If the goal state is found, the algorithm calculates the number of nodes expanded, the search depth, and the running time and returns the result.

3. A* Algorithm:
The aStarSearch method implements the A* algorithm. It uses a priority queue (frontier) to store the states to be explored, sorted based on their total cost (current cost + heuristic cost). It also uses a set (explored) to keep track of the states that have been visited. The algorithm starts by adding the initial state to the frontier with its heuristic cost and enters a loop that continues until the frontier is empty. In each iteration, it dequeues a state from the frontier, generates its neighbors, and calculates their costs and heuristic costs. It adds the neighbors to the frontier if they haven't been explored yet or if a shorter path to them has been found. If the goal state is found, the algorithm calculates the number of nodes expanded, the search depth, and the running time and returns the result.

These algorithms are used to solve the 8-puzzle problem by generating all possible neighbor states from the current state and exploring them until the goal state is reached. The search algorithms keep track of the path taken to reach the goal state and provide information such as the cost of the path, the number of nodes expanded, the search depth, and the running time.
Sure! Here is the pseudocode for each of the algorithms used in the code:

1. Breadth First Search (BFS):

```
function breadthFirstSearch(initialState, goalState):
    frontier = Queue()
    explored = Set()
    
    frontier.enqueue(initialState)
    explored.add(initialState)
    
    while not frontier.isEmpty():
        currentState = frontier.dequeue()
        
        if currentState == goalState:
            return "SUCCESS"
        
        for neighbor in generateNeighbors(currentState):
            if neighbor not in explored:
                frontier.enqueue(neighbor)
                explored.add(neighbor)
    
    return "FAILURE"
```

2. Depth First Search (DFS):

```
function depthFirstSearch(currentState, goalState, explored):
    if currentState == goalState:
        return "SUCCESS"
    
    explored.add(currentState)
    
    for neighbor in generateNeighbors(currentState):
        if neighbor not in explored:
            result = depthFirstSearch(neighbor, goalState, explored)
            
            if result == "SUCCESS":
                return "SUCCESS"
    
    return "FAILURE"
```

3. A* Search:

```
function aStarSearch(initialState, goalState):
    frontier = PriorityQueue()
    explored = Set()
    costMap = Map()
    
    frontier.enqueue(initialState, 0)
    costMap.put(initialState, 0)
    
    while not frontier.isEmpty():
        currentState = frontier.dequeue()
        
        if currentState == goalState:
            return "SUCCESS"
        
        for neighbor in generateNeighbors(currentState):
            cost = costMap.get(currentState) + 1
            
            if neighbor not in explored:
                if neighbor not in costMap or cost < costMap.get(neighbor):
                    frontier.enqueue(neighbor, cost + calculateHeuristic(neighbor, goalState))
                    costMap.put(neighbor, cost)
                    explored.add(neighbor)
    
    return "FAILURE"
```

These pseudocode snippets outline the basic structure and operations of each algorithm. You can use these as a guide to implement the algorithms in your preferred programming language.

Please note that these snippets are simplified and may require additional implementation details and error handling. Additionally, the generateNeighbors and calculateHeuristic functions are not included in the pseudocode but are necessary for the complete implementation of the algorithms.




