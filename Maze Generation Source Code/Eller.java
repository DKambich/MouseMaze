/**
 * Eller.java
 *
 * @author: Daniel Kambich
 *
 */

import java.util.LinkedList;

public class Eller extends MazeGenerator {
  private boolean madeVerticalCut;
  private Cell current;
  private int row, col, verticalCol;
  private LinkedList<TreeSet> rowSet;
  private TreeSet currentSet;
  private TreeSet[][] sets;

  public Eller(Maze maze) {
    this.maze = maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    sets = new TreeSet[numCells][numCells];
    row = 0; col = 0; verticalCol = 0;
    current = maze.cells[row][col];
  }

  public void stepMaze(){
      if(row < maze.numCells) {
        if(col < maze.numCells) {
          //If we are not at the last row and col, set current to the current row-col cell
          current.isCurrent = false;
          current = maze.cells[row][col];
          current.visited = true;
          current.isCurrent = true;

          if(sets[row][col] == null) {
            //Create a new tree set if there's not one already
            sets[row][col] = new TreeSet();
          }

          if(col < maze.numCells - 1) {
            if(sets[row][col + 1] == null) {
              //Create a new tree set if there's not one already in the next column
              sets[row][col + 1] = new TreeSet();
            }
            if(mergeSets() && !sets[row][col].isConnected(sets[row][col + 1])) {
              //If we randomly merge the set and they're not connected...
              //Remove the wall between the adjacent cells and connect the sets
              removeWall(maze.cells[row][col], maze.cells[row][col + 1]);
              sets[row][col].connect(sets[row][col + 1]);
            }
            else if(row == maze.numCells - 1 && !sets[row][col].isConnected(sets[row][col + 1])) {
              //If we're at the last row and they're not connected...
              //Remove the wall between the adjacent cells and connect the sets
              removeWall(maze.cells[row][col], maze.cells[row][col + 1]);
              sets[row][col].connect(sets[row][col + 1]);
            }
          }
          else {
            //Get the rows sets and set the current set to the first set in it
            rowSet = getSets(row);
            currentSet = rowSet.remove(0);
          }
          col++;
        }
        else if(row < maze.numCells - 1) {
          if(!madeVerticalCut || verticalCol < maze.cells[0].length) {
            //Set the current cell to the row-col cell
            current.isCurrent = false;
            current = maze.cells[row][verticalCol];
            current.isCurrent = true;

          if(sets[row][verticalCol].isConnected(currentSet) && branchSet()) {
            //If the sets are connected and we randomly branch, create a new set at the next row
            sets[row + 1][verticalCol] = new TreeSet();
            //Remove the wall between the two rows at the vertical column
            removeWall(maze.cells[row][verticalCol], maze.cells[row + 1][verticalCol]);
            //Connect the row sets
            sets[row][verticalCol].connect(sets[row + 1][verticalCol]);
            //Visit the cell on the next row
            maze.cells[row + 1][verticalCol].visited=true;
            madeVerticalCut=true;
          }
          verticalCol = (!madeVerticalCut && verticalCol + 1 == maze.cells[0].length) ? 0 : ++verticalCol;
        }
        else if(rowSet.size() > 0){
          //Reset the current row
          currentSet = rowSet.remove(0);
          verticalCol = 0;
          madeVerticalCut = false;
        }
        else{
          //Reset the variables and increment the next row
          row++;
          col = verticalCol = 0;
          madeVerticalCut = false;
        }
      }
    }
    else {
      //The maze is complete
      current.isCurrent=false;
    }
  }

  public LinkedList<TreeSet> getSets(int row) {
    LinkedList<TreeSet> rowSet = new LinkedList<TreeSet>();
    for(int c = 0; c < maze.numCells; c++){
      boolean isNewSet = true;
      for(TreeSet ts: rowSet) {
        if(sets[row][c].isConnected(ts)) {
          isNewSet = false;
        }
      }
      if(isNewSet) {
        rowSet.add(sets[row][c]);
      }
    }
    return rowSet;
  }

  public boolean mergeSets() {
    return (int) (Math.random() * 2) != 0;
  }

  public boolean branchSet(){
    return (int) (Math.random() * 2) != 0;
  }

  public class TreeSet {
    protected TreeSet parent=null;

    public TreeSet getRoot() {
      return (parent == null) ? this : parent.getRoot();
    }

    public boolean isConnected(TreeSet set) {
      return set.getRoot().equals(getRoot());
    }

    public void connect(TreeSet set) {
      set.getRoot().parent = this;
    }
  }
}
