
public class Cells {
	int height, width;
	int[][] cells; // (rows x columns)
	
	public Cells(int _height, int _width, int[][] _cells) {
		height = _height;
		width = _width;
		cells = new int[height][width];
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				cells[i][j] = _cells[i][j];
	}
	
	public int get(Index idx) {
		return get(idx.row, idx.column);
	}
	
	public int get(int r, int c) {
		return cells[r][c];
	}
	
	//Two boards have the same content
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		//if (o.getClass() != getClass())
		if (! (o instanceof Cells))
			return false;
		Cells other = (Cells) o;
		if (height != other.height || width != other.width)
			return false;
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				if (cells[i][j] != other.cells[i][j])
					return false;
		return true;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + Integer.hashCode(height);
	    result = prime * result + Integer.hashCode(width);
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				result = prime * result + Integer.hashCode(cells[i][j]);
		return result;
	}
	
	//swap content by two indices
	protected void swapContent(Index i1, Index i2) {
		int temp = cells[i1.row][i1.column];
		cells[i1.row][i1.column] = cells[i2.row][i2.column];
		cells[i2.row][i2.column] = temp;
	}
	
	//Is the index is in the range of this board
	public boolean isInRange(Index idx) {
		if (idx.row<0 || idx.column<0)
			return false;
		if (idx.row >= height || idx.column >= width)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++)
				sb.append(cells[i][j]).append(",");
			sb.append("\n");
		}
		return sb.toString();
	}
}
