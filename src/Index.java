
//Index in board
public class Index implements Comparable<Index> {
	int row, column;
	
	public Index(int r, int c) {
		row = r;
		column = c;
	}
	
	public Index(Index other) {
		row = other.row;
		column = other.column;
	}
	
	//New index after operation
	public Index move(OpTypeClass op) {
		Index idx = new Index(this);
		switch (op.getType()) {
		case D:
			idx.row++;
			break;
		case L:
			idx.column--;
			break;
		case R:
			idx.column++;
			break;
		case U:
			idx.row--;
			break;
		}		
		return idx;
	}
	
	//Is other index is either above or below this index
	public boolean isTwinVertical(Index other) {
		if (column != other.column)
			return false;
		return (Math.abs(row - other.row) == 1);
	}
	
	//Is other index is either left or right this index
	public boolean isTwinHorizontal(Index other) {
		if (row != other.row)
			return false;
		return (Math.abs(column- other.column) == 1);
	}

	//Ordering indices
	//By row and column
	@Override
	public int compareTo(Index i) {
		if (row < i.row)
			return -1;
		if (row > i.row)
			return 1;
		if (column < i.column)
			return -1;
		if (column > i.column)
			return 1;
		return 0;
	}
	
	public boolean equals(Index other) {
		return (row==other.row && column==other.column);
	}
	
	public String toString() {
		return "(" + row + "," + column + ")";
	}
}
