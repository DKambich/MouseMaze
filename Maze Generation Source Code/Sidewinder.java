import java.util.ArrayList;
/**
 * Sidewinder.java  
 *
 * @author: Daniel Kambich
 *
 */
public class Sidewinder extends MazeGenerator
{
    private ArrayList<Cell> runSet=new ArrayList<Cell>();
    private Cell current, previous;
    private int row, col;

    public Sidewinder(Maze maze){
        this.maze=maze;
    }

    public void startMaze(int width, int height, int numCells){
        initializeMaze(width, height, numCells);
        row=0; col=0;        
        current=maze.cells[row][col];
    }

    public void stepMaze(){
        if(row<maze.cells.length){
            current.isCurrent=false;
            current=maze.cells[row][col];
            runSet.add(current);
            if(getCell(row, col+1)!=null && ((getCell(current.getRow()-1, current.getCol())==null) || carveEast()))
                removeWall(current, getCell(row, col+1));
            else{
                Cell random=runSet.get((int)(Math.random()*runSet.size()));
                removeWall(random, getCell(row-1, random.getCol()));
                runSet.clear();
            }
            current.visited=true; current.isCurrent=true;
            if(col+1==maze.cells[0].length)
                row++;
            col=(col+1<maze.cells[0].length) ? col+1:0;
        }
        else
            current.isCurrent=false;
    }

    public boolean carveEast(){
        return (int)(Math.random()*2)==0;
    }
}