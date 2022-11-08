package mousemaze.algorithms;
/**
 * Kruskal.java
 *
 * @author: Daniel Kambich
 *
 */

import java.util.ArrayList;

import mousemaze.Cell;
import mousemaze.Maze;
import mousemaze.MazeGenerator;

public class Kruskal extends MazeGenerator {
  private ArrayList<Edge> edges;
  private Cell current, neighbor;
  private TreeSet[][] sets;

  public Kruskal(Maze maze) {
    this.maze = maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    sets = new TreeSet[numCells][numCells];
    edges = new ArrayList<Edge>();
    //Initalizaze an array a sets and an ArrayList of edges
    for(int r = 0; r < maze.numCells; r++) {
      for(int c = 0; c < maze.numCells; c++) {
        sets[r][c] = new TreeSet();
        if(r > 0) {
          //Each edge has an row-col position along with a direction it opens to
          edges.add(new Edge(r, c, 0));
        }
        if(c > 0) {
          edges.add(new Edge(r, c, 3));
        }
      }
    }
    //Set the current cell to the first cell of the maze
    current = maze.cells[0][0];
  }

  public void stepMaze() {
    if(!edges.isEmpty()) {
      //If there are still more edges
      current.isCurrent = false;
      //Get a random edge
      Edge nextEdge = edges.remove((int) (Math.random() * edges.size()));
      //Set the current cell to the cell at the random edges row-col position
      current = maze.cells[nextEdge.row][nextEdge.col];
      //Set the neighbor cells to the cell adjacent in the edges given direction
      neighbor = (nextEdge.direction % 2 == 0) ? maze.cells[nextEdge.row - 1][nextEdge.col] : maze.cells[nextEdge.row][nextEdge.col - 1];
      if((!sets[current.getRow()][current.getCol()].isConnected(sets[neighbor.getRow()][neighbor.getCol()]))){
        //If the set at the current cell and the set at the neighbor cell are not connected...
        //Connect the two sets together
        sets[neighbor.getRow()][neighbor.getCol()].connect(sets[current.getRow()][current.getCol()]);
        //Remove the wall between the current cell and the neighbor
        removeWall(current, neighbor);
        //Visit both the current and neighbor cell
        current.visited = true;
        current.isCurrent = true;
        neighbor.visited = true;
      }
    }
    else {
      //The maze is complete
      current.isCurrent = false;
    }
  }

  public class TreeSet {
    protected TreeSet parent = null;

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

  public class Edge {
    protected int row, col, direction;

    public Edge(int row, int col, int direction) {
      this.row = row;
      this.col = col;
      this.direction = direction;
    }
  }
}
