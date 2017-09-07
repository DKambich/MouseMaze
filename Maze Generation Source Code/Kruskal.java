import java.util.ArrayList;
/**
 * Kruskal.java  
 *
 * @author: Daniel Kambich
 *
 */
public class Kruskal extends MazeGenerator
{
    private TreeSet[][] sets;
    private ArrayList<Edge> edges;
    private Cell nextCell, neighbor;
    public Kruskal(Maze maze){
        this.maze=maze;
    }

    public void startMaze(int width, int height, int numCells){
        initializeMaze(width, height, numCells);
        sets=new TreeSet[numCells][numCells];
        edges=new ArrayList<Edge>();
        for(int r=0; r<maze.cells.length; r++){
            for(int c=0; c<maze.cells[0].length; c++){
                sets[r][c]=new TreeSet();
                if(r > 0) edges.add(new Edge(r, c, 0));
                if(c > 0) edges.add(new Edge(r, c, 3));
            }
        } 
        nextCell=maze.cells[0][0];
    }    

    public void stepMaze(){
        if(!edges.isEmpty()){
            nextCell.isCurrent=false;
            Edge nextEdge=edges.remove((int)(Math.random()*edges.size()));
            nextCell=maze.cells[nextEdge.row][nextEdge.col]; neighbor=(nextEdge.direction%2==0) ? maze.cells[nextEdge.row-1][nextEdge.col]:maze.cells[nextEdge.row][nextEdge.col-1];           
            if((!sets[nextCell.getRow()][nextCell.getCol()].isConnected(sets[neighbor.getRow()][neighbor.getCol()]))){
                sets[neighbor.getRow()][neighbor.getCol()].connect(sets[nextCell.getRow()][nextCell.getCol()]);              
                removeWall(nextCell, neighbor);    
                nextCell.visited=true; 
                nextCell.isCurrent=true;
                neighbor.visited=true;                
            }
        }
        else{
            nextCell.isCurrent=false;
        }
    }

    public class TreeSet{
        protected TreeSet parent=null;
        public TreeSet getRoot(){
            return (parent==null) ? this: parent.getRoot();
        }

        public boolean isConnected(TreeSet set){
            return set.getRoot().equals(getRoot());
        }

        public void connect(TreeSet set){
            set.getRoot().parent=this;
        }
    }

    public class Edge{
        protected int row, col, direction;
        public Edge(int row, int col, int direction){
            this.row=row; this.col=col; this.direction=direction;
        }
    }
}
