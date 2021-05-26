import java.util.Iterator;

//Iterate possible operations within some board
//The numbers are consistent with Operation class
public class IteratorOperations implements Iterator<Operation> {
	
	int current; //current operation
	int last; //one after last legal operation
	Board board;
	
	public IteratorOperations(Board _board) {
		
		board = _board;
		if (board.isSingleBlank) {
			current = 4;
			last = 8;
		}
		else {
			current = 0;
			last = 12;
		}	
	}

	@Override
	public boolean hasNext() {
		while (current < last) {
			if (board.possible(new Operation(current)))
				return true;
			++current;
		}
		return false;
	}

	@Override
	public Operation next() {
		return new Operation(current++);
	}
}
