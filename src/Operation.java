
public class Operation {
	
	//0-3 pair operations
	//4-7 first blank operations
	//8-11 second blank operations
	private final int index;
	
	public Operation(int _index) {
		index = _index;
	}

	public boolean isPair() {
		return (index/4 == 0);
	}
	
	public boolean onFirstBlank() {
		return (index/4 == 1);
	}
	
	public boolean onSecondBlank() {
		return (index/4 == 2);
	}
	
	public OpTypeClass getOp() {
		return new OpTypeClass(index%4);
	}
	
	//reverse of operation type
	public OpTypeClass reverse() {
		return getOp().reverse();
	}
	
	//Is other operation is the inverse of this, in the board
	// A. Where board == board.apply(other).apply(this)
	// B. Where this is opposite operations on one blank, but one is applies on pair.
	public boolean isInverseOf(Board board, Operation other) {
		if (!getOp().reverse().equals(other.getOp()))
			return false;
		
		//If one of the operations are on pair
		//Is enough to check only the direction of the operations
		if (isPair() || other.isPair()) {
			return true;
		}
		
		//If both are single operations they are equivalent if:
		//board2 <-- board.apply(other)
		//And board == board2.apply(this)
		if (board.areBlankedFliped(this))
			return this.onFirstBlank() == other.onSecondBlank();
		else
			return this.onFirstBlank() == other.onFirstBlank();
	}
	
	@Override
	public String toString() {
		return "group: " + isPair() + "\ton first: " + onFirstBlank() + "\t" + getOp().toString();
	}
	
	//Returns a string representing the operation
	//and the actual values in the board on which it was performed
	public String actualToString(Board brd) {
		StringBuilder result = new StringBuilder();
		if (!isPair()) { //one blank
			Index idx = (onFirstBlank() ? brd.getFirstBlank() : brd.getSecondBlank()).move(getOp().reverse());
			result.append(brd.get(idx));
		}
		else { //on two blanks
			Index idx = brd.getFirstBlank().move(getOp().reverse());
			result.append(brd.get(idx));
			result.append('&');
			idx = brd.getSecondBlank().move(getOp().reverse());
			result.append(brd.get(idx));
		}
		result.append(getOp().getType().name()); //append type
		return result.toString();
	}
	
	//Cost of any operation
	public int cost() {
		if (!isPair())
			return 5;
		if (getOp().isHorizontal())
			return 6;
		return 7;
	}
	
	public boolean equals(Operation other) {
		return index == other.index;
	}

}
