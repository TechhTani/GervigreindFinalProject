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
	    	e.printStackTrace();
	    	System.exit(1);
	    }
		
		return grid;
    }
	
	
	
	/**
	 * main procedure
	 */
	public static void main(String[] args) throws Exception {
		int[][] grid;
		Scanner in = new Scanner(System.in);
		
		int solver = pickSolver(in);
		
		if(solver != 1 && solver != 2 && solver != 3) {
			System.out.println("Wrong input please run again");
			return;
		}
		
		String puzzle = pickPuzzle(in);
		
		
		if(puzzle.equals("generate")){
			grid = Generator.SudokuGen();
			Generator.Printer(grid);
			long startTime, endTime;
			
			if(solver == 1) {
				BruteForceAgent a = new BruteForceAgent(grid);
				startTime = System.currentTimeMillis();
				a.solve();
				endTime = System.currentTimeMillis();
			} else if (solver == 2){
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
			
			System.out.println("Solving took " + (endTime - startTime) + " ms");
			System.out.println("");
		} else if(puzzle.equals("0")) {
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
				if(solver == 1) {
					BruteForceAgent a = new BruteForceAgent(grid);
					startTime = System.currentTimeMillis();
					a.solve();
					endTime = System.currentTimeMillis();
				} else if (solver == 2){
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
				
				time += (endTime - startTime);

				System.out.println(filename);
				//a.debug();
				System.out.println("Solving took " + time + " ms");
				System.out.println("");
			}
		} else {
			// Only one puzzle
			grid = readPuzzle(puzzle);
			long startTime, endTime;
			if(solver == 1) {
				BruteForceAgent a = new BruteForceAgent(grid);
				startTime = System.currentTimeMillis();
				a.solve();
				endTime = System.currentTimeMillis();
			} else if(solver == 2){
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
			System.out.println(puzzle);
			System.out.println("Solving took " + (endTime - startTime) + " ms");
			System.out.println("");
		}
	}
	
	private static int pickSolver(Scanner in) {
		System.out.println("Welcome To The Sudou Solver!");
		System.out.println("Input 1 for Bruteforce");
		System.out.println("Input 2 for Internal CSP");
		System.out.println("Input 3 for External CSP");
		//Scanner in = new Scanner(System.in);
		String solver = in.nextLine();
		//in.close();
		
		return Integer.parseInt(solver);
	}
	
	private static String pickPuzzle(Scanner in) {
		System.out.println("You are doing a great job! Now pick a puzzle to solve! "
				+ "(The puzzles are in the puzzle folder in the projects directory)");
		System.out.println("Example: diabotical-puzzle.txt");
		System.out.println("You can also type 0 to solve all puzzles or generate to generate a puzzle");
		
		//Scanner in = new Scanner(System.in);
		String file = in.nextLine();
		in.close();
		
		return file;
	}
}