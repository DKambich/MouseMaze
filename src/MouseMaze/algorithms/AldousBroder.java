package mousemaze.algorithms;

import mousemaze.Cell;
import mousemaze.Maze;
import mousemaze.MazeGenerator;

/**
 * AldousBroder.java
 *
 * @author: Daniel Kambich
 *
 */

public class AldousBroder extends MazeGenerator {
  private Cell current;

  public AldousBroder(Maze maze) {
    this.maze = maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    // Begin at a random cell in the maze
    current = maze.cells[(int) (Math.random() * numCells)][(int) (Math.random() * numCells)];
  }

  public void stepMaze() {
    if (!allVisited()) {
      // Visit the current cell
      current.visited = true;
      current.isCurrent = false;
      // Retrieve a new random neighbor
      Cell next = getRandomNeighbor(current.getRow(), current.getCol());
      // Remove the wall if the new neighbor is unvisited
      if (!next.visited)
        removeWall(current, next);
      // Set the current cell to the new neighbor
      current = next;
      current.isCurrent = true;
    } else {
      // The maze is completed
      current.isCurrent = false;
    }
  }

  public boolean allVisited() {
    for (Cell[] row : maze.cells)
      for (Cell col : row)
        if (!col.visited) {
          return false;
        }
    return true;
  }

}
