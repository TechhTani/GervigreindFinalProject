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
	        	if(line.length() == 9) {
	        		// Puzzle in 9 lines
	        		for(int i = 0; i < 9; i++) {
		        		if(strArr[i].equals(".")) {
		        			grid[counter][i] = 0;
		        		} else {
		        			grid[counter][i] = Integer.parseInt(strArr[i]);
		        		}
		        		
		        	}
	        	} else {
	        		// Puzzle in one line
	        		for(int i = 0; i < 9; i++) {
	        			for(int j = 0; j < 9; j++, counter++) {
	        				if(strArr[counter].equals(".")) {
			        			grid[i][j] = 0;
			        		} else {
			        			grid[i][j] = Integer.parseInt(strArr[counter]);
			        		}
	        			}
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
		/*
		CSPAgent a = new CSPAgent(readPuzzle("deathBlossom-puzzle.txt"));
		//BruteForceAgent a = new BruteForceAgent(grid);
		//BruteState node = a.bruteForce(a.baseState);
		
		long startTime = System.currentTimeMillis();
		a.solve();
		long endTime = System.currentTimeMillis();
		a.printBoard();
		System.out.println("deathBlossom-puzzle.txt");
		System.out.println("Unknowns are : " + a.countUnknowns());
		System.out.println("Solving took " + (endTime - startTime) + " ms");
		System.out.println("");
		*/
		for(File f : listOfFiles) {
			String filename = f.getName();
			int[][] grid = readPuzzle(filename);
			
			Agent a = new Agent(grid);
			//BruteForceAgent a = new BruteForceAgent(grid);
			//BruteState node = a.bruteForce(a.baseState);
			
			long startTime = System.currentTimeMillis();
			a.solve();
			long endTime = System.currentTimeMillis();
			//a.printBoard();
			System.out.println(filename);
			System.out.println("Unknowns are : " + a.countUnknowns());
			System.out.println("Solving took " + (endTime - startTime) + " ms");
			System.out.println("");
		}
		//*/
	}
}
