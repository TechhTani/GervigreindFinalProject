import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
		// Only one puzzle
		long time = 0;
		for(int i= 0; i < 100; i++) {
			//Agent a = new Agent(grid);
			CSPAgent a = new CSPAgent(readPuzzle("unsolvable-puzzle.txt"));
			//BruteForceAgent a = new BruteForceAgent(readPuzzle("db.txt"));
			
			long startTime = System.currentTimeMillis();
			a.solve();
			long endTime = System.currentTimeMillis();
			//a.printBoard();
			
			//a.debug();
			
			time += (endTime - startTime);
		}
		System.out.println("Solving took " + (time / 100.0) + " ms");
		//*/
		
		// All puzzles
		for(File f : listOfFiles) {
			String filename = f.getName();
			int[][] grid = readPuzzle(filename);
			long time = 0;
			for(int i= 0; i < 1; i++) {
				Agent a = new Agent(grid);
				//CSPAgent a = new CSPAgent(grid);
				//BruteForceAgent a = new BruteForceAgent(grid);
				
				long startTime = System.currentTimeMillis();
				a.solve();
				long endTime = System.currentTimeMillis();
				//a.printBoard();
				time += (endTime - startTime);
			}
			System.out.println(filename);
			//a.debug();
			System.out.println("Solving took " + (time / 100.0) + " ms");
			System.out.println("");
			
		}
		//*/
	}
}
