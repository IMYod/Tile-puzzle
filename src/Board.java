public class Board extends Cells   {
		
	boolean isSingleBlank = true; // true - single blank, false - 2 blanks
	boolean isBlank1First = true;

	// indices of the (possible) blanks
	Index blank1, blank2;
	
	// The operation that created this object
	Operation createdOp;
	Board prev;
	
	//Constructor
	public Board(int _height, int _width, int[][] _cells, Operation _created_op, Board _prev) {
		super(_height, _width, _cells);
		createdOp = _created_op;
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				if (get(i,j) == 0) {
					if (blank1 == null) //first blank
						blank1 = new Index(i,j);
					else { //second blank
						blank2 = new Index(i,j);
						isSingleBlank = false;
					}
				}
			}
		}
		prev = _prev;
		assert(equals(prev.apply(createdOp)));
	}
	
	public Board(Board other) {
		super(other.height, other.width, other.cells);
		this.createdOp = other.createdOp;
		this.blank1 = other.blank1;
		this.blank2 = other.blank2;
		this.isBlank1First = other.isBlank1First;
		this.isSingleBlank = other.isSingleBlank;
		this.prev = other.prev;
	}
	
	public Board(int _height, int _width, int[][] _cells) {
		this(_height, _width, _cells, null, null);
	}
	
	//Two board have the same content
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}	
	
	//Is applying this operator is possible
	public boolean possible(Operation operation) {
		//This operation is not the reverse of the last operation
		if (createdOp != null && createdOp.isInverseOf(this, operation)) //equals(operation.reverse(this)))
				return false;
		//Operate on 2 blanks together
		if (operation.isPair()) {
			if (isSingleBlank)
				return false;
			//Correct shape of blanks
			if (operation.getOp().isHorizontal() && !blank1.isTwinVertical(blank2))
				return false;
			if (operation.getOp().isVertical() && !blank1.isTwinHorizontal(blank2))
				return false;
			//Does not go beyond boundaries
			Index target1 = blank1.move(operation.reverse());
			Index target2 = blank1.move(operation.reverse());
			return (isInRange(target1) && isInRange(target2));
		}
			
		Index blank = operation.onFirstBlank() ? getFirstBlank() : getSecondBlank();
		Index target = blank.move(operation.reverse());
		
		//don't move blank to another blank
		if (!isSingleBlank) {
			Index otherBlank = operation.onFirstBlank() ? getSecondBlank() : getFirstBlank();
			if (otherBlank.equals(target))
				return false;
		}
		return (isInRange(target));
	}
	
	public boolean areBlankedFliped(Operation operation) {
		if (isSingleBlank)
			return false;
		if (operation.onFirstBlank())
			return getFirstBlank().move(operation.getOp().reverse()).compareTo(getSecondBlank()) > 0;
		else
			return getFirstBlank().compareTo(getSecondBlank().move(operation.getOp().reverse())) > 0;
	}
	
	private void flipBlanks() {
		isBlank1First = !isBlank1First;
	}
	
	//Return the resulting board from the operation 
	public Board apply(Operation operation) {
		Board result = new Board(this);
		result.applyVoid(operation);
		result.createdOp = operation;
		result.prev = this;
		return result;
	}
	
	//Run an operation on this board
	private void applyVoid(Operation operation) {
		Index firstBlank = getFirstBlank();
		Index secondBlank = getSecondBlank();
		boolean flip = areBlankedFliped(operation);
		
		//move first blank
		if (operation.isPair() || operation.onFirstBlank()) {
			Index target1 = firstBlank.move(operation.reverse());
			super.swapContent(target1, firstBlank);
			setFirstBlank(target1);
		}		
		
		//move second blank
		if (operation.isPair() || operation.onSecondBlank()) {
			Index target2 = secondBlank.move(operation.reverse());
			super.swapContent(target2, secondBlank);
			setSecondBlank(target2);
		}
		
		if (flip)
			flipBlanks();
	}
	
	public Index getFirstBlank() {
		return isBlank1First ? blank1 : blank2;
	}
	
	public Index getSecondBlank() {
		return isBlank1First ? blank2 : blank1;
	}
	
	public void setFirstBlank(Index idx) {
		if (isBlank1First)
			blank1 = idx;
		else
			blank2 = idx;
	}
	
	public void setSecondBlank(Index idx) {
		if (isBlank1First)
			blank2 = idx;
		else
			blank1 = idx;		
	}
	
	public String toString() {
		return super.toString();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}