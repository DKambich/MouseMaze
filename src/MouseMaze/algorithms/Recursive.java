package mousemaze.algorithms;

import java.util.Stack;

import mousemaze.Cell;
import mousemaze.Maze;
import mousemaze.MazeGenerator;

/**
 * Recursive.java
 *
 * @author: Daniel Kambich
 *
 */
public class Recursive extends MazeGenerator {
  private final int HORIZONTAL = 0, VERTICAL = 1;
  private Stack<RecursionCommand> commands;

  public Recursive(Maze maze) {
    this.maze = maze;
  }

  public void startMaze(int width, int height, int numCells) {
    initializeMaze(width, height, numCells);
    clearMaze();
    commands = new Stack<RecursionCommand>();
    commands
        .add(new RecursionCommand(0, 0, numCells, numCells, ((int) (Math.random() * 2) == 0) ? HORIZONTAL : VERTICAL));
  }

  public void stepMaze() {
    if (!commands.isEmpty()) {
      RecursionCommand[] subFields = commands.pop().runCommand();
      if (subFields != null) {
        for (RecursionCommand field : subFields) {
          if (field != null) {
            commands.push(field);
          }
        }
      }
    } else if (!maze.cells[maze.cells.length - 1][maze.cells[0].length - 1].visited) {
      for (Cell[] cellRow : maze.cells) {
        for (Cell cell : cellRow) {
          cell.visited = true;
        }
      }
    }
  }

  public class RecursionCommand {
    private int x, y, width, height, orientation;

    public RecursionCommand(int x, int y, int width, int height, int orientation) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.orientation = orientation;
    }

    public RecursionCommand[] runCommand() {
      if (width < 2 || height < 2) {
        return null;
      }
      if (orientation == HORIZONTAL) {
        int walledRow = (int) (Math.random() * (height - 1)) + y;
        for (int c = x; c < width + x; c++) {
          addWall(maze.cells[walledRow][c], maze.cells[walledRow + 1][c]);
        }
        int passageCol = (int) (Math.random() * (width)) + x;
        removeWall(maze.cells[walledRow][passageCol], maze.cells[walledRow + 1][passageCol]);
        int topHeight = ++walledRow - y, bottomHeight = height - topHeight; // ++walledRow changes row from an index to
                                                                            // a "height" value
        return new RecursionCommand[] {
            new RecursionCommand(x, y, width, topHeight, selectOrientation(width, topHeight)),
            new RecursionCommand(x, walledRow, width, bottomHeight, selectOrientation(width, bottomHeight)) };
      } else {
        int walledCol = (int) (Math.random() * (width - 1)) + x;
        for (int r = y; r < height + y; r++)
          addWall(maze.cells[r][walledCol], maze.cells[r][walledCol + 1]);
        int passageRow = (int) (Math.random() * (height)) + y;
        removeWall(maze.cells[passageRow][walledCol], maze.cells[passageRow][walledCol + 1]);
        int leftWidth = ++walledCol - x, rightWidth = width - leftWidth; // ++walledCol changes col from an index to a
                                                                         // "width" value
        return new RecursionCommand[] {
            new RecursionCommand(x, y, leftWidth, height, selectOrientation(leftWidth, height)),
            new RecursionCommand(walledCol, y, rightWidth, height, selectOrientation(rightWidth, height)) };
      }
    }

    public int selectOrientation(int width, int height) {
      if (width < height)
        return HORIZONTAL;
      else if (width > height)
        return VERTICAL;
      return (int) (Math.random() * 2) == 0 ? HORIZONTAL : VERTICAL;
    }

    public void addWall(Cell a, Cell b) {
      switch (a.getCol() - b.getCol()) {
        case 1:
          a.pointers[3] = null;
          b.pointers[1] = null;
          break;
        case -1:
          a.pointers[1] = null;
          b.pointers[3] = null;
          break;
      }
      switch (a.getRow() - b.getRow()) {
        case 1:
          a.pointers[0] = null;
          b.pointers[2] = null;
          break;
        case -1:
          a.pointers[2] = null;
          b.pointers[0] = null;
          break;
      }
    }
  }
}
