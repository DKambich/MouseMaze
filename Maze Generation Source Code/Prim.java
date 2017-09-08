/**
 * Prim.java
 *
 * @author: Daniel Kambich
 *
 */

import java.util.ArrayList;

public class Prim extends MazeGenerator {
  private ArrayList<Wall> walls;
  private Cell current;
  private Wall nextWall;

  public Prim(Maze maze) {
    this.maze=maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    walls = new ArrayList<Wall>();
    current = maze.cells[(int) (Math.random() * numCells)][(int) (Math.random() * numCells)];
    walls.addAll(generateWalls(current, getUnvisitedNeighbors(current.getRow(), current.getCol())));
  }

  public void stepMaze() {
    if(walls.size() > 0) {
      if(nextWall != null) {
        nextWall.toCell.isCurrent = false;
      }
      int nextIndex = (int) (Math.random() * walls.size());
      nextWall = walls.get(nextIndex);
      current = nextWall.fromCell;
      if(!nextWall.toCell.visited) {
        nextWall.createPassage();
        nextWall.toCell.isCurrent = true;
        walls.addAll(generateWalls(nextWall.toCell, getUnvisitedNeighbors(nextWall.toCell.getRow(), nextWall.toCell.getCol())));
      }
      walls.remove(nextWall);
    }
    else {
      current.isCurrent = false;
    }
  }

  public ArrayList<Wall> generateWalls(Cell fromCell, ArrayList<Cell> toCells){
    ArrayList<Wall> walls = new ArrayList<Wall>();
    for(Cell cell: toCells){
      Wall next = new Wall(fromCell, cell);
      if(!next.toCell.visited) {
        walls.add(next);
      }
    }
    return walls;
  }

  public class Wall {
    protected Cell fromCell, toCell;

    public Wall(Cell from, Cell to) {
      fromCell = from;
      toCell = to;
    }

    public void createPassage(){
      removeWall(fromCell, toCell);
      fromCell.visited = true;
      toCell.visited = true;
    }
  }
}
