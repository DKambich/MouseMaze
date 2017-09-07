# Maze-Generation
This project generates mazes using a variety of algorithms. The creation and completion of the maze is displayed using a GUI

Credit for the design and inspiration for this project goes to Daniel Shiffman (https://youtu.be/HyK_Q5rrcr4)

Credit for the general structure of algorithms goes to Jamis Buck.
The same algorithms written and implemented in Ruby can be found at: http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap

Here are some general explanations of each algorithm

Random Walkers:

	Aldous Broder - One of the simplest algorithms to implement. It simply starts at a random cell in the maze and 
	chooses a random neighbor to move to. If the random neighbor has not been visited, it removes the wall between 
	the current cell and the neighbor. This repeats until the every cell in the maze has been visited.
	
	Backtracker - This algorithm utilizes a stack to implement backtracking. It starts at a random cell in the
	maze and looks for unvisited neighbor cells. If an unvisited neighbor is found, it pushes the current cell 
	onto the stack, removes the wall between the current cell and the neighbor cell, then moves to the 
	neighbor. If no unvisited neighbor cell is found, it pops from the stack until a cell that has an unvisited
	neighbor is found. This process is then repeated until the stack is empty.


	Hunter -
	
 	Wilson -

Biased Trees:

	Binary Tree -

	Eller - 

	Sidewinder - 


Minimum Spanning Trees:

	Kruskal - 

	Prim -

Special Cases:

	Growing Tree - 

	Recursive - 


Instructions on Usage: