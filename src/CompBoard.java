import java.util.Comparator;

//Compares two boards by
//	1. heuristic function
//	2. depth in the searching
//	3. creating operation
public class CompBoard implements Comparator<BoardNode> {

	Heuristic heur;
	
	public CompBoard(Heuristic _heur) {
		heur = _heur;
	}
	
	//Criteria of comparison:
	//	A. The heuristic function
	//	B.Creation time
	@Override
	public int compare(BoardNode b1, BoardNode b2) {
		int f_delta = heur.f(b1) - heur.f(b2);
		if (f_delta != 0)
			return f_delta;
		return b1.getSeriealNum() - b2.getSeriealNum();
	}
}