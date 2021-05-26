import java.util.HashMap;

//Manhattan heuristic function
public class ManHeuristic extends Heuristic {

	private final int cost1Horizontal = 5;
	private final int cost1Vertical = 5;
	private final int cost2Horizontal = 6;
	private final int cost2Vertical = 7;
	
	private BoardNode current;
	private int countTotHorizontal, countTotVertical;
	private int count1Horizontal, count1Vertical;
	
	private void setCurrent(BoardNode _current) {
		current = _current;
		countTotHorizontal = 0;
		countTotVertical = 0;
	}
	
	@Override
	public int heuristic(BoardNode current) {
		
		setCurrent(current);
		manhattan();
		rowInversions();
		columnInversions();
		
		//If there is single blank, the cost of any moving is 5
		if (current.isSingleBlank) {
			return (int) (cost1Horizontal * countTotHorizontal + cost1Vertical * countTotVertical);
		}

		// If there are 2 blanks,
		//minimum expected cost of one operation is 6 or 7 for two blanks
		else {

			//count single moves from current to attached blanks
			attachBlanks(current);
			//count single moves from attached blanks to goal
			attachBlanks(goal);
			
			//If the shortest path is without blanks
			count1Horizontal = Math.min(count1Horizontal, countTotHorizontal);
			count1Vertical = Math.min(count1Vertical, countTotVertical);
			
			return (int) (count1Horizontal*cost1Horizontal
					+ count1Vertical*cost1Vertical
					+ (countTotHorizontal - count1Horizontal) * cost2Horizontal/2.0
					+ (countTotVertical - count1Vertical) * cost2Vertical/2.0);
		}
	}
		
	private void manhattan() {
		HashMap<Integer, Index> map_goal = new HashMap<>();
		// Iterate goal, mapping value -> Index
		for (int i=0; i<current.height; i++)
			for (int j=0; j<current.width; j++)
				map_goal.put(goal.get(i,j), new Index(i,j));
		// Iterate current, summing total Manhattan distance
		for (int i=0; i<current.height; i++) {
			for (int j=0; j<current.width; j++) {
				Integer value = current.get(i, j);
				if (value != 0) {
					Index valueGoalIndex = map_goal.get(value);
					countTotVertical += Math.abs(i - valueGoalIndex.row);
					countTotHorizontal += Math.abs(j - valueGoalIndex.column);
				}
			}
		}
	}
	
	//How many single moves needed to attach blanks from some board
	private void attachBlanks(Board board) {
		Index b1 = board.getFirstBlank();
		Index b2 = board.getSecondBlank();
		//Two blanks in the same row
		if (b1.row == b2.row)
			count1Horizontal += Math.abs(b1.column - b2.column) - 1;
		//As we give here lower bound, we want the blanks to be ":" and not ".."
		else {
			count1Horizontal += Math.abs(b1.column - b2.column);
			count1Vertical += Math.abs(b1.row - b2.row) - 1;
		}
	}
	
	//Count pairs like 2,...,1 in some row
	//But in the goal, the order in this row is 1,...,2
	private void rowInversions() {
		for (int row=0; row<current.height; row++) { //for each row
			for (int col1=0; col1<current.width-1; col1++) { //for each int in row of current
				int firstCurrent = current.get(row, col1);
				if (firstCurrent == 0) continue;
				IntList goalRow = new IntList(goal.cells[row]); //equiv row in the goal
				int firstInGoal = goalRow.find(firstCurrent); //where does first appears in goal 
				if (firstInGoal != -1) {
					for (int col2=col1+1; col2<current.width; col2++) { //for each int right to first in current
						int secondCurrent = current.get(row, col2);
						if (secondCurrent == 0) continue;
						if (goalRow.containsUntil(secondCurrent, firstInGoal)) //is it left to first in goal
							countTotVertical += 2;
					}
				}
			}
		}
	}

	//Count pairs like 2,...,1 in some column
	//But in the goal, the order in this column is 1,...,2
	private void columnInversions() {
		for (int col=0; col<current.width; col++) { //for each column
			for (int row1=0; row1<current.height-1; row1++) { //for each int in column of current
				int firstCurrent = current.get(row1, col);
				if (firstCurrent == 0) continue;
				
				int[] goalColArr = new int[goal.height];
				for (int i=0; i<goal.height; i++)
					goalColArr[i] = goal.cells[i][col];
				IntList goalCol = new IntList(goalColArr); //equiv column in the goal
				
				int firstInGoal = goalCol.find(firstCurrent); //where does first appears in goal 
				if (firstInGoal != -1) {
					for (int row2=row1+1; row2<current.height; row2++) { //for each int down to first in current
						int secondCurrent = current.get(row2, col);
						if (secondCurrent == 0) continue;
						if (goalCol.containsUntil(secondCurrent, firstInGoal)) //is it up to first in goal
							countTotHorizontal += 2;
					}
				}
			}
		}
	}
	
	//List of int, implements by array
	private class IntList {
		int[] arr;
		public IntList(int[] _arr) { arr = _arr;}
		public int get (int i) {return arr[i];}
		public int find(int i) {
			for (int index=0; index<arr.length; index++)
				if (get(index) == i)
					return index;
			return -1;
		}
		//contains number i until the index breaker?
		public boolean containsUntil(int i, int breaker) {
			for (int index=0; index<breaker; index++)
				if (get(index) == i)
					return true;
			return false;
		}
	}

}
