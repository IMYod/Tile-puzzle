import java.util.HashSet;

public class DFID extends Algorithm {
	
	public static final Board fail = null; //No solution
	public static final Board cutoff = new Board(2, 2, new int[][]{{0,0},{0,0}}); //Impossible real board, just marker of cutoff

	@Override
	protected Path searchAlgo(Board start, Board goal) {
		int depth = 1;
		//Run loop with limited depth
		while (true) {
			HashSet<Board> hash = new HashSet<>();
			Board result = limitedDFS(start, goal, depth, hash);
			if (result != cutoff)
				return new Path(result); //Either path or fail 
			depth++;
		}
	}
	
	private Board limitedDFS(Board start, Board goal, int limit, HashSet<Board> hash) {
		if (start.equals(goal))
			return start;
		//The limit got the depth
		else if (limit==0)
			return cutoff;
		else {
			hash.add(start);
			boolean isCutoff = false;
			//Get every legal next operation
			IteratorOperations ops = new IteratorOperations(start);
			
			while (ops.hasNext()) {
				Operation currentOp = ops.next();
				Board child = start.apply(currentOp);
				created++;
				//This child has been created
				if (hash.contains(child))
					continue;
				//Search from the child
				Board result = limitedDFS(child, goal, limit-1, hash);
				//Touch the maximal depth
				if (result == cutoff)
					isCutoff = true;
				//Search all the nodes but didn't find
				else if (result != fail)
					return result;
			}
			print_open_list(hash);
			hash.remove(start);
			if (isCutoff)
				return cutoff;
			return fail;
		}
	}

	protected void print_open_list(HashSet<Board> hash) {
		if (open) {
			System.out.println("***---***---***");
			for (Board board : hash)
				System.out.println(board);
		}
	}
	
	//Not use this function
	@Override
	protected void print_logic() {
		// TODO Auto-generated method stub
	}

}
