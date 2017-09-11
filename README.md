# Maze-Generation
This project generates mazes using a variety of algorithms. The creation and completion of the maze is displayed using a GUI

Credit for the design and inspiration for this project goes to Daniel Shiffman (https://youtu.be/HyK_Q5rrcr4)

Credit for the general structure of algorithms goes to Jamis Buck.
The same algorithms written and implemented in Ruby can be found at: http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap

Instructions on Usage:

1. Open the included jar file or run the main method in Maze.java

2. Use the menu bar to select options of the animation

3. Speed alters how fast the maze generates

4. Size alters how large of a maze is generated

5. Style alters which algorithm is used to generate the maze

6. Right clicking on the maze allows you to either pause the animation or reset the current generation

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
	itself) the algorithm switches to hunt mode. It goes through the maze in row-column order, checking if the 
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

	Binary Tree - This algorithm slightly mimics the behaviour of a binary tree data structure. It starts at the
	first cell of the maze. It then goes through in row-column and either removes a wall between the cell to the 
	East or South of the current cell, marking the current cell as visited. This process repeats for every cell, 
	except for the very rightmost column and bottommost row, until all cells have been visited.

	Eller - This algorithm is the most complicated of the biased trees. It starts by assigning each cell in the
	first row to its own set then randomly carves East on the row, combining sets that have connected cells.
	Next, it goes through each set of cells on the current row and carves South at least once per set, adding the
	carved cell to the set. Move to the next row and assign any cells that are not in a set to a new one. Repeat
	the previous steps until the final row is reached. Once the last row is reached, connect any differing sets
	and remove the walls between the newly connected cells.
	
	Sidewinder - This algorithm starts by initializing a set of cells for the current row and sets the current 
	cell to the first cell in the maze. It then adds the current cell to the row set. Next, it decides whether
	to carve East randomly, removing the wall if yes. If a passage was carved, make the eastern cell the current
	cell and repeat the previous steps. Otherwise, carve a passage North, clear the row set, and set the next 
	cell in the row to be the current cell, and repeat the previous steps. This repeats until all the cells have
	been visited.

Minimum Spanning Trees:

	Kruskal - 

	Prim -

Special Cases:

	Growing Tree - 

	Recursive - 