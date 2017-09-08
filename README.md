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

	Hunter - It starts at a random cell in the maze and selects a random unvisited neighbor. If an unvisited
	neighbor is found, it removes the wall between the current cell and the neighbor cell, then moves to the
	neighbor cell. If there are no unvisited neighbors for the current cell (i.e. the maze has double back on
	itself) the algorithm switches to hunt mode. It goes through the maze in row-column fashion, checking if the 
	current cell has any visited cell. Once it has found a cell with a visited neighbor, it removes the wall
	between them. Then the current cell begins the 'random walk' again, looking for unvisited neighbors. This
	repeats until all cells are visited.
	
 	Wilson - This algorithm is the most complicated of the random walk algorithms. It starts by marking a random
	cell in the maze as visited. It then selects a random unvisited cell to be the current cell. The current cell
	then performs a 'random walk'. During the random walk, it keeps track of the direction by which the last cell 
	moved to the current cell (i.e. if the last cell was to the left of the current cell, it stores the direction
	on the last cell as right). If the current cell ever is on a cell previously touched, it overrides the 
	direction that was there. If at any point the current cell chooses a visited cell as its neighbor, the current
	cell then restarts to its original location, following the directional path layed out and removing walls as it
	goes until reaching the visitied cell. A new random cell is then picked and the process repeats until all
	cells have been visited.

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