
//Heuristic path value to the goal, via some node
public abstract class Heuristic {
	
	protected Board goal;
	
	public Board getGoal() {
		return goal;
	}

	public void setGoal(Board goal) {
		this.goal = goal;
	}

	//The h function
	public abstract int heuristic(BoardNode current);
	
	//g function
	public int g(BoardNode current) {
		return current.getCost();
	}
	
	//f function - total value
	public int f(BoardNode current) {
		return g(current) + heuristic(current);
	}
}
