import java.util.Set;
import java.util.Stack;

public class IDAStar extends Algorithm {

	MultiHashMap<BoardNode> hashFront; 
	Stack<BoardNode> stack;
	
	@Override
	protected Path searchAlgo(Board _start, Board goal) {
		Heuristic heur = new ManHeuristic();
		heur.setGoal(goal);
		hashFront = new MultiHashMap<>();
		stack = new Stack<>();
		
		//Initialize start board
		BoardNode start = new BoardNode (_start);
		int threshold = heur.f(start);
		//Main loop: Progress by threshold value
		while (threshold < Integer.MAX_VALUE) {
			//Minimum f value above threshold
			int minF = Integer.MAX_VALUE;
			start.out = false;
			stack.add(start);
			hashFront.add(start);
			
			//Secondary loop: Advanced according to the scanning process
			while (!stack.empty()) {
				print_open_list();
				BoardNode currentBoard = stack.pop();
				//loop avoidance
				if (currentBoard.out)
					hashFront.remove(currentBoard);
				else {
					currentBoard.out = true;
					stack.add(currentBoard);
					//Find legal children of current board
					IteratorOperations ops = new IteratorOperations(currentBoard);
					while (ops.hasNext()) {
						Operation currentOp = ops.next();
						BoardNode next = currentBoard.apply(currentOp);
						next.setSeriealNum(++created);
						
						//Check the f value of the child
						int fNext = heur.f(next);
						//Above the threshold
						if (fNext > threshold) {
							minF = Math.min(minF, fNext);
							continue;
						}
						//This child had been scanned earlier
						 if (hashFront.contains(next)) {
								Set<BoardNode> eqBoards = hashFront.get(next);
								for (BoardNode brd : eqBoards) {
									//If 'brd' is same board that had been scanned before 
									if (next.equals(brd)) {
										if (brd.out) {
											continue;
										}
										else {
											//Is the child have better f value?
											if (heur.f(brd) > heur.f(next)) {
												stack.removeElement(brd);
												hashFront.remove(brd);
											}
											else
												continue;
										}
									}
								}
						 }
						if (next.equals(goal))
							return new Path(next);
						stack.add(next);
						hashFront.add(next);		
					}
				}
			}
			//Update the threshold
			threshold = minF;
		}
		return null;
	}

	@Override
	protected void print_logic() {
		Set<BoardNode> frontBoards = hashFront.values();
		for (BoardNode board : frontBoards)
			if (!board.out)
				System.out.println(board);
	}
	
}
