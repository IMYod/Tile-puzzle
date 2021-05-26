
public class AStar extends UCS {
	
	@Override
	protected void setHeur() {
		//set Manhattan heuristic to UCS
		heur = new ManHeuristic();
	}
}
