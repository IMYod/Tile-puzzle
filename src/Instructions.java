//Instructions for determining the search parameters
//Obtained from the input file
public class Instructions {
	Algorithm algo;
	boolean time;
	boolean printOpenList;
	int rows, columns;
	Board start, goal;
	
	public Algorithm getAlgo() {
		return algo;
	}
	public void setAlgo(Algorithm algo) {
		this.algo = algo;
	}
	public boolean isTime() {
		return time;
	}
	public void setTime(boolean time) {
		this.time = time;
	}
	public boolean isPrintOpenList() {
		return printOpenList;
	}
	public void setPrintOpenList(boolean printOpenList) {
		this.printOpenList = printOpenList;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public Board getStart() {
		return start;
	}
	public void setStart(Board start) {
		this.start = start;
	}
	public Board getGoal() {
		return goal;
	}
	public void setGoal(Board goal) {
		this.goal = goal;
	}
}
