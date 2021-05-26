import java.io.FileNotFoundException;
import java.io.IOException;

public class Ex1 {
	
	public static void main(String[] args) {
		Algorithm algo;
		Input input;
		Instructions instruc = null;

		//Reads instructions from input file
		try {
			input = new Input("input2.txt");
			instruc = input.readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		algo = instruc.getAlgo();
		
		//Runs the searching
		Path result = algo.search(instruc.start, instruc.goal, instruc.isPrintOpenList());
		
		//Write result path to output
		Output out;
		try {
			out = new Output("output.txt");
			if (result == null)
				out.writeNoSolutionFile(instruc);
			else
				out.writeSolutionFile(instruc, result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
