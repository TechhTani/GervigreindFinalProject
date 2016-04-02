import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Kristjan on 1.4.2016.
 */
public class Main {
	
	public static int[][] readPuzzle() {
		int[][] grid = new int[9][9];
		
		try(BufferedReader br = new BufferedReader(new FileReader("easy-puzzle.txt"))) {
	        String line = br.readLine();
	        int counter = 0;
	        while (line != null) {
	        	String[] strArr = line.split("");
	        	for(int i = 0; i < 9; i++) {
	        		if(strArr[i].equals(".")) {
	        			grid[counter][i] = 0;
	        		} else {
	        			grid[counter][i] = Integer.parseInt(strArr[i]);
	        		}
	        		
	        	}
	        	counter++;
	            line = br.readLine();
	        }

	    } catch(Exception e) {
	    	System.out.println("Could not read puzzle");
	    }
		
		return grid;
    }
	
	/**
	 * main procedure
	 */
	public static void main(String[] args) throws Exception {
	    int[][] array = readPuzzle();

		Agent a = new Agent(array);
		a.Solver();
	}
    
}
