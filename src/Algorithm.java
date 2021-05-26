
public abstract class Algorithm {
	protected int created ; //How many nodes created at search
	private long exeTime; // time of execution in milliseconds
	protected boolean open; //Print open list?
	
	public Path search(Board start, Board goal, boolean _open) {
		created = 1; //start board
		exeTime = 0;
		open = _open;
		long exeStart = System.currentTimeMillis(); //start time
		Path result = searchAlgo(start, goal);
		long exeEnd = System.currentTimeMillis(); //End time
		exeTime = exeEnd - exeStart;
		return result;
	}
	
	//Given start and goal, searches for shortest path
	protected abstract Path searchAlgo(Board start, Board goal);
	
	public int countCreated() { 
		return created;
	}
	
	//Returns running time in seconds
	public double runningTime() {
		return exeTime / 1000.0;
	}
	
	//Prints the open list if open is true
	protected void print_open_list() {
		if (open) {
			System.out.println("***---***---***");
			print_logic();
		}
	}
	
	//Prints the open list, depending on the inherits class
	protected abstract void print_logic();
}
