import java.util.LinkedList;
import java.util.Stack;

public class Path {
	private LinkedList<Board> list;
	
	// Restores the path ending in this board
	public Path(Board goal) {
		list = new LinkedList<>();
		if (goal != null)
			restore(goal);
	}
	
	//The restores logic
	private void restore(Board ending) {
		Board current = ending;
		while (current != null) {
			list.push(current);
			current = current.prev;
		}
	}
	
	// Restores the path by stack of boards that marked as 'out'
	// last board is the last board in the path
	public Path(Stack<BoardNode> stk, Board lastBoard) {
		list = new LinkedList<>();
		list.push(lastBoard);
		while (!stk.empty()) {
			BoardNode brd = stk.pop();
			if (brd.out)
				list.push(brd);
		}
	}
	
	public int cost() {
		int total = 0;
		for (Board brd : list) {
			if (brd.prev != null)
				total += brd.createdOp.cost();
		}
		return total;
	}
	
	public int length() {
		return list.size() - 1;
	}
	
	//Returns string of the operations on the path
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Board lastBoard = null;
		for (Board brd : list) {
			if (brd.createdOp != null) { //this is not the firsts board
				sb.append(brd.createdOp.actualToString(lastBoard));
				sb.append('-');
			}
			lastBoard = brd;
		}
		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}