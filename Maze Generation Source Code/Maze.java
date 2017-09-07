import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;


/**
* MazeDemo.java
*
* @author: Daniel Kambich
*
*/

public class Maze
{
    public static void main(String[] args){
        Maze test=new Maze();
        test.setup();
    }

    //* GRAPHICS VARIABLES
    private final int WINDOW_WIDTH = 801, WINDOW_HEIGHT = 801;
    private JFrame window;
    private JMenuBar bar;
    private Panel panel;
    private Timer eventTimer;

    //* MECHANICS VARIABLES
    private boolean paused=false;
    protected int mazeWidth=WINDOW_WIDTH-1, mazeHeight=WINDOW_HEIGHT-1, numCells=16, speed=30, mazeGen=4;
    private final MazeGenerator[] generators=new MazeGenerator[]{new AldousBroder(this), new Backtracker(this), new BinaryTree(this), new Eller(this), new GrowingTree(this), new Hunter(this), new Kruskal(this), new Prim(this), new Recursive(this), new Sidewinder(this), new Wilson(this)};
    protected Cell[][] cells;
    private MazeGenerator currentGenerator;

    public Maze(){
        this.cells=new Cell[numCells][numCells];
    }

    public void setup(){
        window = new JFrame("Maze Generation Demo");
        bar = new JMenuBar();
        panel = new Panel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); window.setResizable(false);
        window.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-WINDOW_WIDTH/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-WINDOW_HEIGHT/2);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        generateMenuBar();

        window.setJMenuBar(bar);
        window.add(panel);
        window.addMouseListener(panel);

        window.pack();
        if(window.getContentPane().getWidth() > WINDOW_WIDTH)
            window.pack();
        window.setVisible(true);

        currentGenerator = generators[mazeGen];
        currentGenerator.startMaze(mazeWidth, mazeHeight, numCells);

        eventTimer=new Timer(1000/speed, e->{updateWindow();});
        eventTimer.start();
    }

    public void generateMenuBar() {
        //General Menus and Features
        JMenu speed = new JMenu("1. Generation Speed"), size = new JMenu("2. Maze Size"), mazeStyle = new JMenu("3. Maze Style"), randomWalk=new JMenu("Random Walkers"), biased=new JMenu("Biased Trees"), mst=new JMenu("Minimum Spanning Trees"), special=new JMenu("Special Cases");

        //Speed Menu
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem slow = new JRadioButtonMenuItem("Slow"), medium = new JRadioButtonMenuItem("Medium"), fast = new JRadioButtonMenuItem("Fast"), superFast = new JRadioButtonMenuItem("Super-Fast");
        slow.addActionListener(e -> setSearchSpeed(10));
        medium.addActionListener(e -> setSearchSpeed(30));
        fast.addActionListener(e -> setSearchSpeed(60));
        superFast.addActionListener(e -> setSearchSpeed(90));
        group.add(slow); group.add(medium); group.add(fast); group.add(superFast);
        group.setSelected(medium.getModel(), true);
        speed.add(slow); speed.add(medium); speed.add(fast); speed.add(superFast);

        //Maze Size Menu
        group = new ButtonGroup();
        JRadioButtonMenuItem[] buttons = new JRadioButtonMenuItem[]{ new JRadioButtonMenuItem("4x4 Grid"), new JRadioButtonMenuItem("8x8 Grid"), new JRadioButtonMenuItem("16x16 Grid"), new JRadioButtonMenuItem("32x32 Grid"), new JRadioButtonMenuItem("50x50 Grid"), new JRadioButtonMenuItem("100x100 Grid") };
        buttons[0].addActionListener(e -> setMazeSize(4));
        buttons[1].addActionListener(e -> setMazeSize(8));
        buttons[2].addActionListener(e -> setMazeSize(16));
        buttons[3].addActionListener(e -> setMazeSize(32));
        buttons[4].addActionListener(e -> setMazeSize(50));
        buttons[5].addActionListener(e -> setMazeSize(100));
        for(JRadioButtonMenuItem button: buttons){
            group.add(button);
            size.add(button);
        }
        group.setSelected(buttons[2].getModel(), true);

        //Maze Style Menu
        group=new ButtonGroup();
        JRadioButtonMenuItem[] styles = new JRadioButtonMenuItem[]{
          new JRadioButtonMenuItem("Aldous-Broder"), new JRadioButtonMenuItem("Backtracker"), new JRadioButtonMenuItem("Binary Tree"), new JRadioButtonMenuItem("Eller"),
          new JRadioButtonMenuItem("Growing Tree"), new JRadioButtonMenuItem("Hunter"), new JRadioButtonMenuItem("Kruskal"), new JRadioButtonMenuItem("Prim"),
          new JRadioButtonMenuItem("Recursive"), new JRadioButtonMenuItem("Sidewinder"), new JRadioButtonMenuItem("Wilson")
        };
        styles[0].addActionListener(e-> setMazeStyle(0));
        styles[1].addActionListener(e-> setMazeStyle(1));
        styles[2].addActionListener(e-> setMazeStyle(2));
        styles[3].addActionListener(e-> setMazeStyle(3));
        styles[4].addActionListener(e-> setMazeStyle(4));
        styles[5].addActionListener(e-> setMazeStyle(5));
        styles[6].addActionListener(e-> setMazeStyle(6));
        styles[7].addActionListener(e-> setMazeStyle(7));
        styles[8].addActionListener(e-> setMazeStyle(8));
        styles[9].addActionListener(e-> setMazeStyle(9));
        styles[10].addActionListener(e-> setMazeStyle(10));
        for(JRadioButtonMenuItem button: styles){
            group.add(button);
        }
        //Random Walkers Subgroup
        randomWalk.add(styles[0]);
        randomWalk.add(styles[1]);
        randomWalk.add(styles[5]);
        randomWalk.add(styles[10]);

        //Biased Trees Subgroup
        biased.add(styles[2]);
        biased.add(styles[3]);
       biased.add(styles[9]);

       //Minimum Spanning Trees Subgroup
        mst.add(styles[6]);
        mst.add(styles[7]);

        //Special Cases Subgroup
        special.add(styles[4]);
        special.add(styles[8]);

        group.setSelected(styles[4].getModel(), true);

        mazeStyle.add(randomWalk);
        mazeStyle.add(biased);
        mazeStyle.add(mst);
        mazeStyle.add(special);

        //Adding Created Menus
        bar.add(speed); bar.add(size); bar.add(mazeStyle);
    }

    public void setSearchSpeed(int speed){
        eventTimer.setDelay(1000/speed);
    }

    public void setMazeSize(int mazeSize){
        numCells=mazeSize;
        resetGeneration();
    }

    public void setMazeStyle(int style){
        currentGenerator=generators[style];
        resetGeneration();
    }

    public void updateWindow(){
        if(!paused){
            currentGenerator.stepMaze();
            panel.repaint();
        }
    }

    public void resetGeneration(){
        currentGenerator.startMaze(mazeWidth, mazeHeight, numCells);
        paused = false;
        panel.pause.setText("1. Pause");
    }

    public class Panel extends JPanel implements MouseListener {
        private JPopupMenu popup;
        protected JMenuItem pause, reset;
        public Panel(){
            setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
            setBackground(new Color(57,57,57));
            popup = new JPopupMenu("Settings");
            pause = new JMenuItem("1. Pause");
            reset = new JMenuItem("2. Reset");
            pause.addActionListener(e -> pause(e));
            reset.addActionListener(e -> resetGeneration());
            popup.add(pause); popup.add(reset);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(Cell[] row: cells)
                for(Cell col: row)
                    col.show(g);
        }

        public void mouseReleased(MouseEvent e){
            maybeShowPopup(e);
        }

        public void mousePressed(MouseEvent e){
            maybeShowPopup(e);
        }

        public void pause(ActionEvent e){
            pause.setText((paused) ? "1. Pause":"1. Resume");
            paused = !paused;
        }

        public void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger())
                popup.show(e.getComponent(), e.getX(), e.getY());
        }

        public void mouseClicked(MouseEvent e){}

        public void mouseEntered(MouseEvent e){}

        public void mouseExited(MouseEvent e){}
    }
}
