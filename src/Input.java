import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//Reads the input file
public class Input {
	Instructions instruc;
    BufferedReader br;
    
	public Input(String filePath) throws FileNotFoundException {
		File file = new File("./" + filePath);
		br = new BufferedReader(new FileReader(file));
	}
	
    public Instructions readFile() {
    	instruc = new Instructions();
        try {
            //read algorithm type
            String st = br.readLine();
            chooseAlgorithm(st);
            
            //read running time
            st = br.readLine();
            choosePrintTime(st);
            
            //read open list
            st = br.readLine();
            choosePrintOpenList(st);
            
            //read board size
            st = br.readLine();
            readBoardSize(st);
            
            //read boards
            instruc.setStart(new Board(instruc.getRows(), instruc.getColumns(), ReadCells(br)));
            st = br.readLine();
            if (!st.equals("Goal state:"))
            	throw new IOException("Not read Goal state:");
            instruc.setGoal(new Board(instruc.getRows(), instruc.getColumns(), ReadCells(br)));
            
            br.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getInstruc();
    }

	public Instructions getInstruc() {
		return instruc;
	}

	//read board
	private int[][] ReadCells(BufferedReader br) throws IOException {
		int[][] initState = new int[instruc.getRows()][instruc.getColumns()];
		for (int i=0; i<instruc.getRows(); i++) {
        	String st = br.readLine();
	        String[] splited = st.split(",");
	        for (int j=0; j<instruc.getColumns(); j++)
	        	initState[i][j] = readNum(splited[j]);
        }
		return initState;
	}

	//read cell by number
	// '_' means 0
	private int readNum(String string) {
		if (string.equals("_"))
			return 0;
		return Integer.parseInt(string);
	}

	//read board size
	private void readBoardSize(String st) {
		String[] splited = st.split("x");
		instruc.setRows(Integer.parseInt(splited[0]));
		instruc.setColumns(Integer.parseInt(splited[1]));
	}

    //read open list
	private void choosePrintOpenList(String st) throws IOException {
		if (st.equals("with open"))
			instruc.setPrintOpenList(true);
        else if (st.equals("no open"))
        	instruc.setPrintOpenList(false);
        else
        	throw new IOException("Bad choose print open list:");
	}

    //read running time
	private void choosePrintTime(String st) throws IOException {
		if (st.equals("with time"))
        	instruc.setTime(true);
        else if (st.equals("no time"))
        	instruc.setTime(false);
        else
        	throw new IOException("Bad choose print time:");
	}

	//read algorithm type
	private void chooseAlgorithm(String st) throws IOException {
		if (st.equals("BFS"))
        	instruc.setAlgo(new BFS());
        else if (st.equals("DFID"))
        	instruc.setAlgo(new DFID());
        else if (st.equals("A*"))
        	instruc.setAlgo(new AStar());
        else if (st.equals("IDA*"))
        	instruc.setAlgo(new IDAStar());
        else if (st.equals("DFBnB"))
        	instruc.setAlgo(new DFBnB());
        else
        	throw new IOException("Bad choose algorithm:");
	}
}
