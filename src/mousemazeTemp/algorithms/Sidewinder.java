package mousemazeTemp.algorithms;

import java.util.ArrayList;

import mousemazeTemp.Cell;
import mousemazeTemp.Maze;
import mousemazeTemp.MazeGenerator;

/**
 * Sidewinder.java
 *
 * @author: Daniel Kambich
 *
 */
public class Sidewinder extends MazeGenerator {
  private ArrayList<Cell> runSet;
  private Cell current;
  private int row, col;

  public Sidewinder(Maze maze) {
    this.maze = maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    runSet = new ArrayList<Cell>();
    row = col = 0;
    // Set the current cell to the first cell of the maze
    current = maze.cells[row][col];
  }

  public void stepMaze() {
    if (row < maze.numCells) {
      current.isCurrent = false;
      // Set the current cell to the row-col cell
      current = maze.cells[row][col];
      // Add the cell to a set
      runSet.add(current);
      if (getCell(row, col + 1) != null && (getCell(current.getRow() - 1, current.getCol()) == null || carveEast())) {
        // If the cell east to current cell exists and we are at the first row or
        // randomly carve east
        // Remove the wall between current and the east cell
        removeWall(current, getCell(row, col + 1));
      } else {
        // Otherwise, select a random cell from the set
        Cell random = runSet.get((int) (Math.random() * runSet.size()));
        // Remove the wall between the random cell and it north cell
        removeWall(random, getCell(row - 1, random.getCol()));
        // Clear the current set
        runSet.clear();
      }
      // Visit the current cell
      current.visited = true;
      current.isCurrent = true;
      // Continue looping through the maze in row-col order
      if (col + 1 == maze.numCells) {
        row++;
      }
      col = (col + 1 < maze.numCells) ? col + 1 : 0;
    } else {
      // The maze is complete
      current.isCurrent = false;
    }
  }

  public boolean carveEast() {
    return (int) (Math.random() * 2) == 0;
  }
}
