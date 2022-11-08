package mousemaze;

/**
 * MazeGenerator.java
 *
 * @author: Daniel Kambich
 *
 */
import java.util.ArrayList;

public abstract class MazeGenerator {
    protected Maze maze;

    public abstract void startMaze(int width, int height, int numCells);

    public abstract void stepMaze();

    public void initializeMaze(int width, int height, int numCells) {
        maze.cells = new Cell[numCells][numCells];
        for (int r = 0; r < numCells; r++)
            for (int c = 0; c < numCells; c++)
                maze.cells[r][c] = new Cell(c * width / numCells, r * height / numCells, width / numCells);
    }

    public void clearMaze() {
        for (int r = 0; r < maze.cells.length; r++)
            for (int c = 0; c < maze.cells.length; c++) {
                removeWall(getCell(r, c), getCell(r + 1, c));
                removeWall(getCell(r, c), getCell(r, c + 1));
            }
    }

    public void removeWall(Cell a, Cell b) {
        if (a != null && b != null) {
            switch (a.getCol() - b.getCol()) {
                case 1:
                    a.pointers[3] = b;
                    b.pointers[1] = a;
                    break;
                case -1:
                    a.pointers[1] = b;
                    b.pointers[3] = a;
                    break;
            }
            switch (a.getRow() - b.getRow()) {
                case 1:
                    a.pointers[0] = b;
                    b.pointers[2] = a;
                    break;
                case -1:
                    a.pointers[2] = b;
                    b.pointers[0] = a;
                    break;
            }
        }
    }

    public Cell getCell(int row, int col) {
        return (row >= 0 && row < maze.cells.length && col >= 0 && col < maze.cells[0].length) ? maze.cells[row][col]
                : null;
    }

    public Cell getRandomNeighbor(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        Cell[] possible = new Cell[] { getCell(row, col - 1), getCell(row, col + 1), getCell(row - 1, col),
                getCell(row + 1, col) };
        for (Cell dc : possible)
            if (dc != null)
                neighbors.add(dc);
        return (neighbors.size() > 0) ? neighbors.get((int) (Math.random() * neighbors.size())) : null;
    }

    public Cell getRandomUnvisitedNeighbor(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        Cell[] possible = new Cell[] { getCell(row, col - 1), getCell(row, col + 1), getCell(row - 1, col),
                getCell(row + 1, col) };
        for (Cell dc : possible)
            if (dc != null && !dc.visited)
                neighbors.add(dc);
        return (neighbors.size() > 0) ? neighbors.get((int) (Math.random() * neighbors.size())) : null;
    }

    public ArrayList<Cell> getUnvisitedNeighbors(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        Cell[] possible = new Cell[] { getCell(row, col - 1), getCell(row, col + 1), getCell(row - 1, col),
                getCell(row + 1, col) };
        for (Cell dc : possible)
            if (dc != null && !dc.visited)
                neighbors.add(dc);
        return neighbors;
    }

    public ArrayList<Cell> getNeighbors(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        Cell[] possible = new Cell[] { getCell(row, col - 1), getCell(row, col + 1), getCell(row - 1, col),
                getCell(row + 1, col) };
        for (Cell dc : possible)
            if (dc != null)
                neighbors.add(dc);
        return neighbors;
    }

    public String toString() {
        return getClass().getName();
    }
}
