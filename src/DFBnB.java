import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class DFBnB extends Algorithm {

	MultiHashMap<BoardNode> hashFront; 
	Stack<BoardNode> stack;
	
	@Override
	protected Path searchAlgo(Board _start, Board goal) {
		Heuristic heur = new ManHeuristic();
		heur.setGoal(goal);
		CompBoard comp = new CompBoard(heur);
		Path result = null;
		
		BoardNode start = new BoardNode (_start);
		hashFront = new MultiHashMap<>();
		stack = new Stack<>();
		stack.push(start);
		hashFront.add(start);
		int threshold = Integer.MAX_VALUE;
		
		while (!stack.empty()) {
			//pop next board
			print_open_list();
			BoardNode currentBoard = stack.pop();
			if (currentBoard.out)
				hashFront.remove(currentBoard);
			else {
				//mark current as out
				currentBoard.out = true;
				stack.push(currentBoard);
				List<BoardNode> childrenOrder = new ArrayList<>();
				IteratorOperations ops = new IteratorOperations(currentBoard);
				//create all children
				while (ops.hasNext()) {
					Operation currentOp = ops.next();
					BoardNode child = currentBoard.apply(currentOp);
					child.setSeriealNum(++created);
					childrenOrder.add(child);
				}
				//Sort children by heuristic criteria
				childrenOrder.sort(comp);
				
				
				for (int i=0; i<childrenOrder.size(); i++) {
					BoardNode next = childrenOrder.get(i);
					int fNext = heur.f(next);

					//heuristic(next) >= threshold
					//by the ordering, all the rest boards in the forward also irrelevant
					if (fNext >= threshold) {
						while (i<childrenOrder.size())
							childrenOrder.remove(i);
						break;
					}
					
					//next appears in the front hash
					else if (hashFront.contains(next)) {
						Set<BoardNode> eqBoards = hashFront.get(next);
						for (BoardNode sameBoard : eqBoards) {
							if (next.equals(sameBoard)) {
								//loop avoidance
								if (sameBoard.out) {
									childrenOrder.remove(i);
									i--;
								}
								else {
									//There is better path to this board
									if (heur.f(sameBoard) <= heur.f(next)) {
										childrenOrder.remove(i);
										i--;
									}
									else {
										stack.removeElement(sameBoard); //
										hashFront.remove(sameBoard);
									}
								}
							}
						}
					 }
					
					//Is next reaches goal with better heuristic?
					else if (next.equals(goal)) {
						 //update threshold and best path
						threshold = fNext;
						//result = new Path(next);
						result = new Path(stack, next);
						while (i<childrenOrder.size())
							childrenOrder.remove(i);
					}
				}
				//update the stack and the frontier
				for (int i=childrenOrder.size()-1; i>=0; i--) {
					stack.push(childrenOrder.get(i));
					hashFront.add(childrenOrder.get(i));
				}
			}
		}
		return result;
	}

	//print not out in the open list
	@Override
	protected void print_logic() {
		Set<BoardNode> frontBoards = hashFront.values();
		for (BoardNode board : frontBoards)
			if (!board.out)
				System.out.println(board);
	}
}
