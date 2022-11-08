package MouseMaze;

/**
 * Cell.java
 *
 * @author: Daniel Kambich
 *
 */

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
  public boolean visited, isCurrent, isTouched;
  public Cell[] pointers; // North --> East --> South --> West (Clockwise rotation)
  private Color wallColor, visitedColor, currentColor, touchedColor;
  protected int x, y, width;

  public Cell(int x, int y, int width) {
    // Positional Assignment
    this.x = x;
    this.y = y;
    this.width = width;
    // State Assignment
    visited = false;
    isCurrent = false;
    isTouched = false;
    // Adjacent Cell Creation
    pointers = new Cell[] { null, null, null, null };
    // Color Creation
    wallColor = Color.WHITE;
    visitedColor = new Color(255, 0, 255, 100);
    currentColor = new Color(0, 0, 255, 100);
    touchedColor = new Color(200, 0, 0, 100);
  }

  public int getRow() {
    return y / width;
  }

  public int getCol() {
    return x / width;
  }

  // public LinkedList<Cell> getAdjacent(){
  // LinkedList<Cell> adj = new LinkedList<Cell>();
  // for(Cell p: pointers)
  // if(p != null && !p.visited) {
  // adj.add(p);
  // }
  // return adj;
  // }

  public void show(Graphics g) {
    g.setColor(wallColor);
    // Draw a wall if the given adjacent cell is not linked
    if (pointers[0] == null)
      g.drawLine(x, y, x + width, y);
    if (pointers[1] == null)
      g.drawLine(x + width, y, x + width, y + width);
    if (pointers[2] == null)
      g.drawLine(x + width, y + width, x, y + width);
    if (pointers[3] == null)
      g.drawLine(x, y + width, x, y);

    if (isCurrent) {
      // Highlights the cell for clarity so it is known as the current cell
      g.setColor(currentColor);
      g.fillRect(x, y, width, width);
    } else if (visited) {
      // Marks the cell as a visited cell
      g.setColor(visitedColor);
      g.fillRect(x, y, width, width);
    } else if (isTouched) {
      // Marks the cell as a touched cell(not necessarily visited), used mainly for
      // Wilson algorithm
      g.setColor(touchedColor);
      g.fillRect(x, y, width, width);
    }
  }

  public String toString() {
    return "Index: " + y / width + ", " + x / width;
  }
}
