import java.util.ArrayList;
/**
 * Wilson.java
 *
 * @author: Daniel Kambich
 *
 */
public class Wilson extends MazeGenerator
{
    private ArrayList<Cell> unvisited;
    private Cell current, origin;
    private int[] directions=new int[]{-1,1,1,-1};
    private int[][] cellDir;

    public Wilson(Maze maze){
        this.maze=maze;
    }

    public void startMaze(int width, int height, int numCells){
        initializeMaze(width, height, numCells); cellDir=new int[maze.cells.length][maze.cells[0].length]; unvisited=new ArrayList<Cell>();

        maze.cells[(int)(Math.random()*maze.cells.length)][(int)(Math.random()*maze.cells[0].length)].visited=true;
        for(int r=0; r<maze.cells.length; r++)
            for(int c=0; c<maze.cells[0].length; c++)
              if(!maze.cells[r][c].visited){
                unvisited.add(maze.cells[r][c]);
              }
        current=unvisited.get((int)(Math.random()*unvisited.size()));
        origin=current;
    }

    public void stepMaze(){
        if(!allVisited()){
            if(!current.visited){
                current.isCurrent=false; current.isTouched=true;
                Cell next=getRandomNeighbor(current.getRow(), current.getCol());
                cellDir[current.getRow()][current.getCol()]=getDirection(current, next);
                current=next;
                current.isCurrent=true;
            }
            else if(!origin.equals(current)){
                origin.visited=true; current.isCurrent=false; origin.isCurrent=false;
                unvisited.remove(origin);
                removeWall(origin, getCellFromDirection(origin, cellDir[origin.getRow()][origin.getCol()]));
                origin=getCellFromDirection(origin, cellDir[origin.getRow()][origin.getCol()]);
                origin.isCurrent=true;
            }
            else{
                current.isCurrent=false;
                current=unvisited.get((int)(Math.random()*unvisited.size()));
                origin=current;
                clearTouched();
            }
        }
        else
            current.isCurrent=false;
    }

    public boolean allVisited(){
        for(Cell[] row: maze.cells)
            for(Cell col: row)
                if(!col.visited)
                    return false;
        return true;
    }

    public void clearTouched(){
        for(Cell[] cellRow: maze.cells)
            for(Cell cell: cellRow)
                cell.isTouched=false;
    }

    public Cell getCellFromDirection(Cell current, int direction){
        return (direction%2==0) ? getCell(current.getRow()+directions[direction], current.getCol()): getCell(current.getRow(), current.getCol()+directions[direction]);
    }

    public int getDirection(Cell from , Cell to){
        return (from.getCol()-to.getCol()!=0) ? ((from.getCol()-to.getCol()==1) ? 3:1) : ((from.getRow()-to.getRow()==1) ? 0:2);
    }
}
