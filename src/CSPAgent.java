
public class CSPAgent {
	final int SIZE = 9;
	private CSPCell[][] grid;
	
	
	public CSPAgent(int[][] board) {
		grid = new CSPCell[SIZE][SIZE];
		
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
					doContinue = false;
				}
			}
		}
		
		//printBoard();
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
	
	public int countUnknowns() {
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
	
	private boolean findHiddenSingles() {
		boolean foundSingle = false;
		for(int i = 0; i < SIZE; i++) {
			int[] countersRow = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};	// Count instances of each number in domains
			int[] countersCol = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};	// Count instances of each number in domains
			// Count occurrences of each number for each domain in a row and a column
			for(int j = 0 ; j < SIZE; j++) {
				for(int d : grid[i][j].domain) {
					countersRow[d - 1]++;
				}
				for(int d : grid[j][i].domain) {
					countersCol[d - 1]++;
				}
			}
			// Check if any number has only one occurrence in a row or a column
			for(int k = 0; k < SIZE; k++) {
				if(countersRow[k] == 1) {
					for(int l = 0; l < SIZE; l++) {
						if(grid[i][l].domain.contains(k)) {
							grid[i][l].value = k;
							grid[i][l].domain.clear();
							updateDomain(i, l, k);
							foundSingle = true;
						}
					}
				}
				if(countersCol[k] == 1) {
					for(int l = 0; l < SIZE; l++) {
						if(grid[l][i].domain.contains(k)) {
							grid[l][i].value = k;
							grid[l][i].domain.clear();
							updateDomain(l, i, k);
							foundSingle = true;
						}
					}
				}
			}
		}
		// Count occurrences of each number for each domain in a box
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				int rowFloor = (row/3) * 3;
				int colFloor = (col/3) * 3;
				int[] countersBox = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};	// Count instances of each number in domains
				
				for(int i = rowFloor; i < rowFloor + 3; i++) {
					for(int j = colFloor ; j < colFloor + 3; j++) {
						for(int d : grid[i][j].domain) {
							countersBox[d - 1]++;
						}
					}
				}
				// Check if any number has only one occurrence in a box
				for(int k = 0; k < SIZE; k++) {
					if(countersBox[k] == 1) {
						for(int i = rowFloor; i < rowFloor + 3; i++) {
							for(int j = colFloor ; j < colFloor + 3; j++) {
								if(grid[i][j].domain.contains(k)) {
									grid[i][j].value = k;
									grid[i][j].domain.clear();
									updateDomain(i, j, k);
									foundSingle = true;
								}
							}
						}
					}
				}
			}
		}
		
		return foundSingle;
	}
}
