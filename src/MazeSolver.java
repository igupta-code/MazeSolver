/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // The stack has the backwards solution
        Stack<MazeCell> inverseSolution = new Stack<MazeCell>();
        // Backwards solution is reversed into the ArrayList
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();

        // Starts at the end and adds the parents to the stack
        MazeCell check = maze.getEndCell();
        while(!check.equals(maze.getStartCell())){
            inverseSolution.push(check);
            check = check.getParent();
        }
        solution.add(maze.getStartCell());

        // Reverses order of stack into Arraylist and returns in order sln
        while(!inverseSolution.empty()){
            solution.add(inverseSolution.pop());
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        Stack<MazeCell> toVisit = new Stack<MazeCell>();
        toVisit.push(maze.getStartCell());
        int row = 0, col = 0;

        // While loop finishes after end cell is explored, meaning the maze is solved
        while(!maze.getEndCell().isExplored()){
            row = toVisit.peek().getRow();
            col = toVisit.peek().getCol();
            // Removes current cell from stack and sets its explored boolean to true
            toVisit.pop().setExplored(true);

            // Adds to stack in reverse order of W, S, E, then N
            if(maze.isValidCell(row-1, col)){
                toVisit.push(maze.getCell(row-1, col));
                maze.getCell(row-1, col).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row, col-1)){
                toVisit.push(maze.getCell(row, col-1));
                maze.getCell(row, col-1).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row+1, col)){
                toVisit.push(maze.getCell(row+1, col));
                maze.getCell(row+1, col).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row, col+1)){
                toVisit.push(maze.getCell(row, col+1));
                maze.getCell(row, col+1).setParent(maze.getCell(row, col));
            }
        }
        return this.getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS(){
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> toVisit = new LinkedList<MazeCell>();
        toVisit.add(maze.getStartCell());
        int row = 0, col = 0;

        // While loop finishes after end cell is explored, meaning the maze is solved
        while(!maze.getEndCell().isExplored()){
            row = toVisit.peek().getRow();
            col = toVisit.peek().getCol();
            // Removes current cell from the front of the queue and sets its explored boolean to true
            toVisit.remove().setExplored(true);

            // Explores in order N, E, S, W
            if(maze.isValidCell(row, col+1)){
                toVisit.add(maze.getCell(row, col+1));
                maze.getCell(row, col+1).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row+1, col)){
                toVisit.add(maze.getCell(row+1, col));
                maze.getCell(row+1, col).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row, col-1)){
                toVisit.add(maze.getCell(row, col-1));
                maze.getCell(row, col-1).setParent(maze.getCell(row, col));
            }
            if(maze.isValidCell(row-1, col)){
                toVisit.add(maze.getCell(row-1, col));
                maze.getCell(row-1, col).setParent(maze.getCell(row, col));
            }
        }
        //maze.printMaze();

        return this.getSolution();

    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        System.out.println();
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
