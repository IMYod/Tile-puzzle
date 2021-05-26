import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {
	
	BufferedWriter bw;
	
	public Output(String filePath) throws IOException {
		File file = new File("./" + filePath);
		file.createNewFile();
        bw = new BufferedWriter(new FileWriter(file));
	}
	
	public void writeSolutionFile(Instructions instruc, Path result) throws IOException {
        
		bw.append(result.toString());
        bw.append(System.lineSeparator()); 
        bw.append("Num: " + instruc.algo.created);
        bw.append(System.lineSeparator());
        bw.append("Cost: " + result.cost());
        bw.append(System.lineSeparator());
        if (instruc.time) {
        	bw.append("" + instruc.algo.runningTime() + " seconds");
        	bw.append(System.lineSeparator());
        }
        bw.flush();
        bw.close();
	}
	
	public void writeNoSolutionFile(Instructions instruc) throws IOException {   
        bw.append("no path");
        bw.append(System.lineSeparator()); 
        bw.append("Num: " + instruc.algo.created);
        bw.append(System.lineSeparator());
        if (instruc.time) {
        	bw.append("" + instruc.algo.runningTime() + " seconds");
        	bw.append(System.lineSeparator());
        }
        bw.flush();
        bw.close();
	}
}
