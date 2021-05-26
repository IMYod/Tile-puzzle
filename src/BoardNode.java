//Board as node in searching
//With data about the state of the node in the searching process
public class BoardNode extends Board {
	private int cost=0, depth=0, seriealNum;
	boolean out = false;

	public BoardNode(Board inner) {
		super(inner);
	}
	
	public int getSeriealNum() {
		return seriealNum;
	}

	public void setSeriealNum(int seriealNum) {
		this.seriealNum = seriealNum;
	}

	public BoardNode(BoardNode other) {
		this((Board)other);
		cost = other.cost;
		depth = other.cost;
		out = other.out;
	}
	
	public BoardNode apply(Operation operation) {
		BoardNode result = new BoardNode(super.apply(operation));
		result.cost = cost + operation.cost();
		result.depth = depth+1;
		return result;
	}

	public int getCost() {
		return cost;
	}

	public int getDepth() {
		return depth;
	}
}