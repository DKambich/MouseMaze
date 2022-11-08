package mousemaze.algorithms;
/**
 * Hunter.java
 *
 * @author: Daniel Kambich
 *
 */

import java.util.ArrayList;

import mousemaze.Cell;
import mousemaze.Maze;
import mousemaze.MazeGenerator;

public class Hunter extends MazeGenerator {
  private Cell current, neighbor, previous;
  private int row, col;

  public Hunter(Maze maze) {
    this.maze=maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    row = col = 0;
    //Set the current cell to a random cell in the maze
    current = maze.cells[(int) (Math.random() * numCells)][(int) (Math.random() * numCells)];
    current.visited = true;
    previous = maze.cells[0][0];
    }

  public void stepMaze() {
    if(!allVisited()) {
      current.isCurrent = false;
      //Get a random unvisited neighbor cell
      neighbor = getRandomUnvisitedCell(current.getRow(), current.getCol());
      if(neighbor != null) {
        //If there is an unvisited neighbor remove the wall between the current cell and the neighbor
        removeWall(current, neighbor);
        //Set the current cell to the neighbor and visit it
        current = neighbor;
        current.visited = true;
        current.isCurrent = true;
      }
      else if(row < maze.numCells) {
        previous.isCurrent = false;
        maze.cells[row][col].isCurrent = true;
        //Search through the maze
        previous = maze.cells[row][col];
        if(!maze.cells[row][col].visited) {
          //If a visited cell is found, get the visited neighbors
          ArrayList<Cell> nextCells = getVisitedCells(row, col);
          if(nextCells.size() > 0) {
            //Choose one of the visited cells if there are any
            Cell prey = nextCells.get((int) (Math.random() * nextCells.size()));
            //Remove the wall between the row-col cell and the prey cell
            removeWall(maze.cells[row][col], prey);
            //Set the current row-col cell to the current cell
            current = maze.cells[row][col];
            //Reset the cell search
            row = col = 0;
            current.visited=true;
          }
        }
        //Continuing through the maze in row-col order
        if(col + 1 == maze.numCells) {
          row++;
        }
        col=(col + 1< maze.numCells) ? ++col : 0;
      }
    }
    else {
      current.isCurrent = false;
    }
  }

  public Cell getRandomUnvisitedCell(int row, int col){
    ArrayList<Cell> neighbors = getUnvisitedNeighbors(row, col);
    return (neighbors.size() > 0) ? neighbors.get((int) (Math.random() * neighbors.size())) : null;
  }

  public ArrayList<Cell> getVisitedCells(int row, int col){
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    Cell[] possible = new Cell[]{ getCell(row, col - 1), getCell(row, col + 1), getCell(row - 1, col), getCell(row + 1, col)};
    for(Cell dc: possible)
      if(dc != null && dc.visited)
        neighbors.add(dc);
    return neighbors;
  }

  public boolean allVisited(){
    for(Cell[] row: maze.cells)
      for(Cell col: row)
        if(!col.visited)
          return false;
    return true;
  }
}
