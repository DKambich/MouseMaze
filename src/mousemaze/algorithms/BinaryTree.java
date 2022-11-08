package mousemazeTemp.algorithms;

import mousemazeTemp.Cell;
import mousemazeTemp.Maze;
import mousemazeTemp.MazeGenerator;

/**
 * BinaryTree.java
 *
 * @author: Daniel Kambich
 *
 */
public class BinaryTree extends MazeGenerator {
  private Cell current;
  private int vertPref, horzPref, row, col;

  public BinaryTree(Maze maze) {
    this.maze = maze;
    vertPref = horzPref = -1;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    row = col = 0;
    // Begin at the first cell in the maze
    current = maze.cells[row][col];
  }

  public void stepMaze() {
    if (row < maze.numCells) {
      // Set the current cell to be the current row-col cell
      current.isCurrent = false;
      current = maze.cells[row][col];
      // Remove the wall between current and a random 'preferred' neigbor
      removeWall(current, getRandomNeighbor(row, col));
      // Visit the current cell
      current.visited = true;
      current.isCurrent = true;
      if (col + 1 == maze.numCells) {
        // If col has reached the end, cycle to the next row
        row++;
      }
      // Increment col if it has not reached the end, otherwise reset it to zero
      col = (col + 1 < maze.numCells) ? col + 1 : 0;
    } else {
      // The maze is completed
      current.isCurrent = false;
    }
  }

  public Cell getRandomNeighbor(int row, int col) {
    if (getCell(row + vertPref, col) != null && getCell(row, col + horzPref) != null) {
      // If cells in the preferred directions exist, choose at random between them
      return ((int) (Math.random() * 2) == 0) ? getCell(row + vertPref, col) : getCell(row, col + horzPref);
    }
    // Otherwise, choose the one cell that exists in the preferred directions
    return (getCell(row + vertPref, col) == null) ? getCell(row, col + horzPref) : getCell(row + vertPref, col);
  }

  /**
   * Rotates clockwise like a compass, using directional pairs
   * 0-NE, 1-SE, 2-SW, 3-NW
   */
  public void setPreference(int preference) {
    switch (preference) {
      case 0:
        vertPref = -1;
        horzPref = 1;
        break;
      case 1:
        vertPref = 1;
        horzPref = 1;
        break;
      case 2:
        vertPref = 1;
        horzPref = -1;
        break;
      case 3:
        vertPref = -1;
        horzPref = -1;
        break;
    }
  }
}
