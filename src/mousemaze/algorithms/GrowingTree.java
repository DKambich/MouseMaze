package mousemazeTemp.algorithms;

import java.util.ArrayList;

import mousemazeTemp.Cell;
import mousemazeTemp.Maze;
import mousemazeTemp.MazeGenerator;

/**
 * GrowingTree.java
 *
 * @author: Daniel Kambich
 *
 */
public class GrowingTree extends MazeGenerator {
  private ArrayList<Cell> cellList;
  private double primPercentage;
  private Cell current, randNeighbor;

  public GrowingTree(Maze maze) {
    this.maze = maze;
    primPercentage = .5;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    cellList = new ArrayList<Cell>();
    // Add a random cell to our cell list
    cellList.add(maze.cells[(int) (Math.random() * numCells)][(int) (Math.random() * numCells)]);
    // Set our current cell to a cell from the cell list
    current = selectCell(cellList);
  }

  public void stepMaze() {
    if (allVisited()) {
      // If all the cells have been visited the maze is complete, clear the rest of
      // the cells
      randNeighbor.isCurrent = false;
      cellList.clear();
    } else if (!cellList.isEmpty()) {
      if (randNeighbor != null) {
        randNeighbor.isCurrent = false;
      }
      // Set the current cell to a cell from the cell list
      current = selectCell(cellList);
      current.visited = true;
      // Get a random unvisited neighbor
      randNeighbor = getRandomUnvisitedNeighbor(current.getRow(), current.getCol());
      if (randNeighbor != null) {
        // If there is a unvisited neighbor...
        // Remove the wall between the current cell and the neighbor
        removeWall(current, randNeighbor);
        // Visit the neighbor cell
        randNeighbor.visited = true;
        randNeighbor.isCurrent = true;
        // Make the newly visited neighbor cell to be selected from the cell list
        cellList.add(randNeighbor);
      } else {
        // Remove the cell from the cell list if there is no other neighbor to visit
        cellList.remove(current);
      }
    }
  }

  public Cell selectCell(ArrayList<Cell> cellList) {
    // Choose between selecting from the end of the cell list(similar to
    // Backtracker) or from a random point in the list(similar to Prim)
    return (Math.random() <= primPercentage) ? cellList.get((int) (Math.random() * cellList.size()))
        : cellList.get(cellList.size() - 1);
  }

  public void setBactrackerPercentage(int percent) {
    primPercentage = (percent != 0) ? (100 - percent) / 100.0 : 1;
  }

  public void setPrimPercentage(int percent) {
    primPercentage = (percent != 0) ? percent / 100.0 : 0;
  }

  public boolean allVisited() {
    for (Cell[] row : maze.cells)
      for (Cell col : row)
        if (!col.visited)
          return false;
    return true;
  }
}
