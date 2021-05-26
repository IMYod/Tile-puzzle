import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UCS extends Algorithm {

	HashSet<Board> hashClose;
	MultiHashMap<BoardNode> hashOpen; 
	Queue<BoardNode> queueOpen;
	Heuristic heur;
	
	public UCS() {
		setHeur();
	}
	
	//In this case: f() = g()
	//Set heuristic h()=0.
	protected void setHeur() {
		heur = new Heuristic() {
			
			@Override
			public int heuristic(BoardNode current) {
				return 0;
			}
		};
	}
	
	
	@Override
	protected Path searchAlgo(Board _start, Board goal) {
		heur.setGoal(goal);
		CompBoard comp = new CompBoard(heur);
		BoardNode start = new BoardNode(_start);
		start.setSeriealNum(created);
		hashOpen = new MultiHashMap<>();
		hashClose = new HashSet<>();
		queueOpen = new PriorityQueue<>(comp);
		
		//Initialize open list
		queueOpen.add(start);
		hashOpen.add(start);
		if (start.equals(goal))
			return new Path(start);
		
		while (!queueOpen.isEmpty()) {
			//Get next node
			print_open_list();
			BoardNode currentBoard = queueOpen.poll();
			
			hashOpen.remove(currentBoard);
			//Is goal?
			if (currentBoard.equals(goal))
				return new Path(currentBoard);
			hashClose.add(currentBoard);
			
			//Get any legal next operation
			IteratorOperations ops = new IteratorOperations(currentBoard);
			while (ops.hasNext()) {
				Operation currentOp = ops.next();
				BoardNode next = currentBoard.apply(currentOp);
				next.setSeriealNum(++created);
				//Is it never created? 
				if (!hashOpen.contains(next) && !hashClose.contains(next)) {
					queueOpen.add(next);
					hashOpen.add(next);
				}
				//Is in the open list?
				else if (hashOpen.contains(next)) {
					Set<BoardNode> eqBoards = hashOpen.get(next);
					for (BoardNode brd : eqBoards) {
						//Which node is better?
						if (next.equals(brd) && comp.compare(next, brd) < 0) {
							print_open_list();
							hashOpen.remove(brd);
							queueOpen.remove(brd);
							hashOpen.add(next);
							queueOpen.add(next);
							break;
						}
					}
				}
			}
		} 
		return null;
	}

	@Override
	protected void print_logic() {
		for (BoardNode node : queueOpen)
			System.out.println(node);
	}
}