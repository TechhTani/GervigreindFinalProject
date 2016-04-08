import java.util.Stack;

public class CSPAgent {
	final int SIZE = 9;
	private CSPCell[][] grid;
	private Stack<CSPCell[][]> stack = new Stack<>();
	private int guessCounter;
	private int hiddenSinglesCounter;
	
	
	public CSPAgent(int[][] board) {
		grid = new CSPCell[SIZE][SIZE];
		guessCounter = 0;
		hiddenSinglesCounter = 0;
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				grid[i][j] = new CSPCell();		// Variable
				if(board[i][j] != 0) {
					grid[i][j].value = board[i][j];
					grid[i][j].domain.clear();
				}
			}
		}
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(board[i][j] != 0) {
					updateDomain(i, j, grid[i][j].value);
				}
			}
		}
	}
	
	public void solve() {	
		boolean doContinue = true;
		while(doContinue) {
			if(!checkDomain()) {
				if(!findHiddenSingles()) {
					if(!guess()) {
						doContinue = false;
					}					
				}
			}
		}
	}
	
	private boolean checkDomain() {
		boolean valueSet = false;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(grid[i][j].domainSize() == 1) {
					grid[i][j].value = grid[i][j].domain.pop().intValue();
					updateDomain(i, j, grid[i][j].value);
					valueSet = true;
				}
			}
		}
		return valueSet;
	}
	
	// Constraint propagation
	private void updateDomain(int row, int col, int value) {
		for(int i = 0; i < SIZE; i++) {
			grid[row][i].domain.remove((Object)value);
			grid[i][col].domain.remove((Object)value);
		}
		int rowFloor = (row/3) * 3;
		int colFloor = (col/3) * 3;
		
		for(int i = rowFloor; i < rowFloor + 3; i++) {
			for(int j = colFloor ; j < colFloor + 3; j++) {
				grid[i][j].domain.remove((Object)value);
			}
		}
		
	}
	
	private boolean findHiddenSingles() {
		boolean foundSingle = false;
		int[] emptyCell = new int[] {0, 0};
		
		while(findNextUnassigned(emptyCell)) {
			int newX = emptyCell[0];
			int newY = emptyCell[1];
			for(int d : grid[newY][newX].domain) {
				boolean singleInRow = true;
				boolean singleInColumn = true;
				boolean singleInSubgrid = true;
				
				for(int i = 0; i < SIZE; i++) {
					// Check the row
					if(i != newX && singleInRow && grid[newY][i].domain.contains((Object)d)) {
						singleInRow = false;
					}
					
					// Check the column
					if(i != newY && singleInColumn && grid[i][newX].domain.contains((Object)d)) {
						singleInColumn = false;
					}
				}
				
				if(!singleInRow && !singleInColumn) {
					// Number not unique in row or column, check subgrid
					int rowFloor = (newY/3) * 3;
					int colFloor = (newX/3) * 3;
					
					subgridLoop: 
					for(int i = rowFloor; i < rowFloor + 3; i++) {
						for(int j = colFloor ; j < colFloor + 3; j++) {
							if(grid[i][j].domain.contains((Object)d)) {
								// number not unique in subgrid
								singleInSubgrid = false;
								break subgridLoop;
							}
						}
					}
				}

				if(singleInRow || singleInColumn || singleInSubgrid) {
					grid[newY][newX].value = d;
					grid[newY][newX].domain.clear();
					updateDomain(newY, newX, d);
					hiddenSinglesCounter++;
					foundSingle = true;
					continue;
				}
			}
			
			emptyCell[0]++;
			emptyCell[1]++;
			
		}
		
		return foundSingle;
		
	}
	
	private boolean guess() {
		// Look for error because of wrong guess or unsolvable puzzle
		for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(grid[i][j].value == 0 && grid[i][j].domain.isEmpty()) {
                	// Found error try backtracking
                	if(stack.isEmpty()) {
                		// Unsolvable
                		return false;
                	} else {
                		grid = stack.pop();
                		return true;
                	}
                }
            }
        }
		for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(grid[i][j].value == 0 && !grid[i][j].domain.isEmpty()) {
                	int value = grid[i][j].domain.pop().intValue();
                	
                	CSPCell[][] tmpGrid = new CSPCell[SIZE][SIZE];
                	for (int k = 0; k < SIZE; k++) {
                        for (int l = 0; l < SIZE; l++) {
                        	CSPCell c = grid[k][l];
                            tmpGrid[k][l] = new CSPCell(c.value, c.domain);
                        }
                    }
                	
                	guessCounter++;
                	stack.push(tmpGrid);
                	
                	
                	grid[i][j].value = value;
                	grid[i][j].domain.clear();
                	updateDomain(i, j, value);
                	return true;
                }
            }
        }
		
		return false;
	}
	
	private boolean findNextUnassigned(int[] pos) {
		for (int i = pos[1]; i < SIZE; i++) {
            for (int j = pos[0]; j < SIZE; j++) {
                if(!grid[i][j].domain.isEmpty()) {
                	// Update pos to contain the coordinates of the cell
                	pos[0] = j;
                	pos[1] = i;
                	return true;
                }
            }
        }
		// None found
		return false;
	}
	
	public void printBoard() {
		StringBuilder sb = new StringBuilder();
		int unknowns = 0;

        sb.append("\t");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(grid[i][j].value).append("  ");
                if(grid[i][j].value == 0) {
                	unknowns++;
                }
            }
            sb.append("\n\t");
        }
        System.out.println(sb.toString());
        System.out.println("Unknowns are " + unknowns);
	}
	
	private int countUnknowns() {
		int unknowns = 0;
		for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(grid[i][j].value == 0) {
                	unknowns++;
                }
            }
        }
		return unknowns;
	}
	
	public void debug() {
		System.out.println("Unknowns:" + 		"\t" + countUnknowns());
		System.out.println("Hidden singles:" + 	"\t" + hiddenSinglesCounter);
		System.out.println("Guesses:" + 		"\t" + guessCounter);
	}
}
