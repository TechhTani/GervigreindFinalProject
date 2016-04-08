import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

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
		int[][] grid = new int[9][9];
		System.out.println("Welcome To The Sudou Solver!");
		System.out.println("Input 1 for Bruteforce");
		System.out.println("Input 2 for Internal CSP");
		System.out.println("Input 3 for External CSP");
		Scanner solver = new Scanner(System.in);
		String puzzle = solver.nextLine();
		if(!puzzle.equals("1") && !puzzle.equals("2") && !puzzle.equals("3")) {
			System.out.println("wrong input please run again");
			return;
		}
		
		System.out.println("You are doing a great job! Now pick a puzzle to solve! "
				+ "(The puzzles are in the puzzle folder in the projects direcotry)");
		System.out.println("Example: diabotical-puzzle.txt");
		System.out.println("type 0 to solve all puzzles");
		
		Scanner file = new Scanner(System.in);
		
		String fileName = file.nextLine();
		if(fileName.equals("generate")){
			/*-------Generator stuff-------*/
		    // int[][] tester = Generator.SudokuGen();
			int[][] tester = Generator.SudokuGen();
		}else if(fileName.equals("0")) {
			File folder = null;
			try {
				folder = new File("puzzles");
			} catch(Exception e) {
		    	System.out.println("Could not read folder");
		    	e.printStackTrace();
		    	System.exit(1);
		    }
			File[] listOfFiles = folder.listFiles();
			// All puzzles
			for(File f : listOfFiles) {
				String filename = f.getName();
				grid = readPuzzle(filename);
				long time = 0;
				long startTime, endTime;
				for(int i= 0; i < 1; i++) {
					if(puzzle.equals("1")) {
						BruteForceAgent a = new BruteForceAgent(grid);
						startTime = System.currentTimeMillis();
						a.solve();
						endTime = System.currentTimeMillis();
					} else if (puzzle.equals("2")){
						CSPAgent a = new CSPAgent(grid);
						startTime = System.currentTimeMillis();
						a.solve();
						endTime = System.currentTimeMillis();
					} else {
						Agent a = new Agent(grid);
						startTime = System.currentTimeMillis();
						a.solve();
						endTime = System.currentTimeMillis();
					}
					//a.printBoard();
					time += (endTime - startTime);
				}
				System.out.println(filename);
				//a.debug();
				System.out.println("Solving took " + (time / 100.0) + " ms");
				System.out.println("");
			}
		} else {
			// Only one puzzle
			grid = readPuzzle(fileName);
			long startTime, endTime;
			if(puzzle.equals("1")) {
				BruteForceAgent a = new BruteForceAgent(grid);
				startTime = System.currentTimeMillis();
				a.solve();
				endTime = System.currentTimeMillis();
			} else if (puzzle.equals("2")){
				CSPAgent a = new CSPAgent(grid);
				startTime = System.currentTimeMillis();
				a.solve();
				endTime = System.currentTimeMillis();
				a.printBoard();
			} else {
				Agent a = new Agent(grid);
				startTime = System.currentTimeMillis();
				a.solve();
				endTime = System.currentTimeMillis();
				a.printBoard();
			}
			System.out.println(fileName + "-puzzle.txt");
			System.out.println("Solving took " + (endTime - startTime) + " ms");
			System.out.println("");
			}
	}
}