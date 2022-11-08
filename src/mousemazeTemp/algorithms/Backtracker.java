package mousemazeTemp.algorithms;

import java.util.Stack;

import mousemazeTemp.Cell;
import mousemazeTemp.Maze;
import mousemazeTemp.MazeGenerator;

/**
 * Backtracker.java
 *
 * @author: Daniel Kambich
 *
 */
public class Backtracker extends MazeGenerator {
  private Cell current;
  private Stack<Cell> visited;

  public Backtracker(Maze maze) {
    this.maze = maze;
    visited = new Stack<Cell>();
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    // Begin at a random cell in the maze
    current = maze.cells[(int) (Math.random() * numCells)][(int) (Math.random() * numCells)];
    // Push the first visited cell on the stack
    visited.push(current);
  }

  public void stepMaze() {
    if (visited.size() > 0) {
      // If there are cells left in the stack, visit the current cell
      current.visited = true;
      current.isCurrent = false;
      // Retrieve a new random unvisited neighbor
      Cell next = getRandomUnvisitedNeighbor(current.getRow(), current.getCol());
      if (next != null) {
        // If there is a new random unvisted neighbor, push the neighbor onto the stack
        visited.push(current);
        // Remove the wall between the current and next cell
        removeWall(current, next);
        // Set the current cell to the new neighbor
        current = next;
      } else {
        // There are no new random unvisted neighbors, so pop from the visited stack to
        // backtrack
        current = visited.pop();
      }
      current.isCurrent = true;
    } else {
      // The maze is completed
      current.isCurrent = false;
    }
  }
}
