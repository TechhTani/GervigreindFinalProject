import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Kristjan on 1.4.2016.
 */
public class Main {
	
	public static int[][] readPuzzle(String filename) {
		int[][] grid = new int[9][9];
		
		try(BufferedReader br = new BufferedReader(new FileReader("puzzles/" + filename))) {
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
	    File folder = null;
		try {
			folder = new File("puzzles");
		} catch(Exception e) {
	    	System.out.println("Could not read folder");
	    	e.printStackTrace();
	    	System.exit(1);
	    }
		File[] listOfFiles = folder.listFiles();
		
		for(File f : listOfFiles) {
			String filename = f.getName();
			int[][] grid = readPuzzle(filename);
			
			CSPAgent a = new CSPAgent(grid);
			//BruteForceAgent a = new BruteForceAgent(grid);
			
			long startTime = System.currentTimeMillis();
			a.solve();
			long endTime = System.currentTimeMillis();
			//a.printBoard();
			System.out.println(filename);
			System.out.println(a.countUnknowns());
			System.out.println("Solving took " + (endTime - startTime) + " ms");
		}
	}
}
