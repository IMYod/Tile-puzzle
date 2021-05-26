import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Algorithm {

	HashSet<Board> hash;
	Queue<Board> open_queue;
	
	@Override
	protected Path searchAlgo(Board start, Board goal) {
		hash = new HashSet<>();
		open_queue = new LinkedList<>();
		
		//Initialize by start node
		open_queue.add(start);
		hash.add(start);
		if (start.equals(goal))
			return new Path(start);

		//Main loop:
		while (!open_queue.isEmpty()) {
			//Poll the closest node
			print_open_list();
			Board currentBoard = open_queue.poll();
			
			//Create every legal next node
			IteratorOperations ops = new IteratorOperations(currentBoard);
			while (ops.hasNext()) {
				Operation currentOp = ops.next();
				Board next = currentBoard.apply(currentOp);
				created++;
				//If never seen before
				if (!hash.contains(next)) {
					//Is goal?
					if (next.equals(goal))
						return new Path(next);
					open_queue.add(next);
					hash.add(next);
				}
			} 
		}
		return null;
	}

	@Override
	protected void print_logic() {
		for (Board node : open_queue)
			System.out.println(node);
	}
}
