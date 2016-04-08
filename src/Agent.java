import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.tools.ArrayUtils;

public class Agent {
    final int SIZE = 9;
    SudokuGrid SudokuPuzzle;
    IntVar[][] rows = new IntVar[SIZE][SIZE];
    IntVar[][] columns = new IntVar[SIZE][SIZE];
    IntVar[][] subgrids = new IntVar[SIZE][SIZE];

    Solver solver;

    public Agent(int[][] board){
        solver = new Solver("SudokuSolver");
        SudokuPuzzle = new SudokuGrid(board);
    }

    public void solve(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) {
                if(SudokuPuzzle.getCell(i,j) == 0){
                    rows[i][j] = VariableFactory.enumerated(i + ", " + j, 1, SIZE, solver);
                }
                else{
                    rows[i][j] = VariableFactory.fixed(SudokuPuzzle.getCell(i,j), solver);
                }
                columns[j][i] = rows[i][j];
            }
        }

        for(int i = 0; i < 3; i++){
        	for(int j = 0; j < 3; j++){
        		for(int k = 0; k < 3; k++){
        			subgrids[j+k*3][i] = rows[k*3][i+j*3];
        			subgrids[j+k*3][i+3] = rows[1+k*3][i+j*3];
        			subgrids[j+k*3][i+6] = rows[2+k*3][i+j*3];
        		}
    		}
        }

        for(int i = 0; i < SIZE; i++){
        	solver.post(IntConstraintFactory.alldifferent(rows[i], "DEFAULT"));
        	solver.post(IntConstraintFactory.alldifferent(columns[i], "DEFAULT"));
        	solver.post(IntConstraintFactory.alldifferent(subgrids[i], "DEFAULT"));
        }


        //solver.set(IntStrategyFactory.firstFail_InDomainMin(ArrayUtils.append(ripped)));
        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(rows)));
        
        solver.findSolution();
    }

    //Used for the generator
    public boolean solutionChecker(){

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) {
                if(SudokuPuzzle.getCell(i,j) == 0){
                    rows[i][j] = VariableFactory.enumerated(i + ", " + j, 1, SIZE, solver);
                }
                else{
                    rows[i][j] = VariableFactory.fixed(SudokuPuzzle.getCell(i,j), solver);
                }
                columns[j][i] = rows[i][j];
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    subgrids[j+k*3][i] = rows[k*3][i+j*3];
                    subgrids[j+k*3][i+3] = rows[1+k*3][i+j*3];
                    subgrids[j+k*3][i+6] = rows[2+k*3][i+j*3];
                }
            }
        }

        for(int i = 0; i < SIZE; i++){
            solver.post(IntConstraintFactory.alldifferent(rows[i], "DEFAULT"));
            solver.post(IntConstraintFactory.alldifferent(columns[i], "DEFAULT"));
            solver.post(IntConstraintFactory.alldifferent(subgrids[i], "DEFAULT"));
        }


        //solver.set(IntStrategyFactory.firstFail_InDomainMin(ArrayUtils.append(ripped)));
        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(rows)));
        solver.findSolution();
        return solver.nextSolution();
       //ystem.out.println("Number of solutions: " + theCheck);

    }

    
    public void printBoard() {
    	StringBuilder sb = new StringBuilder();

        sb.append("\t");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(rows[i][j].getValue()).append("  ");
            }
            sb.append("\n\t");
        }
        System.out.println(sb.toString());
	}
}

