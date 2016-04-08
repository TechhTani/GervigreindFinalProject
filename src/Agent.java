/**
 * Created by Kristj�n on 1.4.2016.
 */
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.tools.ArrayUtils;


public class Agent {
    final int SIZE = 9;
    int[][] Board;
    SudokuGrid SudokuPuzzle;
    IntVar[][] ripped = new IntVar[SIZE][SIZE];
    IntVar[][] colons = new IntVar[SIZE][SIZE];
    IntVar[][] boxing = new IntVar[SIZE][SIZE];

    Solver solver;

    public Agent(int[][] board){
        Board = board;
        solver = new Solver("Big Titties");
        SudokuPuzzle = new SudokuGrid(board);
    }

    public void solve(){

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) {
                if(SudokuPuzzle.getCell(i,j) == 0){
                    ripped[i][j] = VariableFactory.enumerated(i + ", " + j, 1, SIZE, solver);
                }
                else{
                    ripped[i][j] = VariableFactory.fixed(SudokuPuzzle.getCell(i,j), solver);
                }
                colons[j][i] = ripped[i][j];
            }
        }

        for(int i = 0; i < 3; i++){
        	for(int j = 0; j < 3; j++){
        		for(int k = 0; k < 3; k++){
        			boxing[j+k*3][i] = ripped[k*3][i+j*3];
        			boxing[j+k*3][i+3] = ripped[1+k*3][i+j*3];
        			boxing[j+k*3][i+6] = ripped[2+k*3][i+j*3];
        		}
    		}
        }

        for(int i = 0; i < SIZE; i++){
        	solver.post(IntConstraintFactory.alldifferent(ripped[i], "DEFAULT"));
        	solver.post(IntConstraintFactory.alldifferent(colons[i], "DEFAULT"));
        	solver.post(IntConstraintFactory.alldifferent(boxing[i], "DEFAULT"));
        }


        //solver.set(IntStrategyFactory.firstFail_InDomainMin(ArrayUtils.append(ripped)));
        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(ripped)));
        if(solver.findSolution()){
        	/*           System.out.println("Solving sudoku grid!\n\n");

            StringBuilder sb = new StringBuilder();

            sb.append("\t");
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    sb.append(ripped[i][j].getValue()).append("  ");
                }
                sb.append("\n\t");
            }
            System.out.println(sb.toString());
*/
        }

    }
    //Used for the generator
    public boolean solutionChecker(){

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) {
                if(SudokuPuzzle.getCell(i,j) == 0){
                    ripped[i][j] = VariableFactory.enumerated(i + ", " + j, 1, SIZE, solver);
                }
                else{
                    ripped[i][j] = VariableFactory.fixed(SudokuPuzzle.getCell(i,j), solver);
                }
                colons[j][i] = ripped[i][j];
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    boxing[j+k*3][i] = ripped[k*3][i+j*3];
                    boxing[j+k*3][i+3] = ripped[1+k*3][i+j*3];
                    boxing[j+k*3][i+6] = ripped[2+k*3][i+j*3];
                }
            }
        }

        for(int i = 0; i < SIZE; i++){
            solver.post(IntConstraintFactory.alldifferent(ripped[i], "DEFAULT"));
            solver.post(IntConstraintFactory.alldifferent(colons[i], "DEFAULT"));
            solver.post(IntConstraintFactory.alldifferent(boxing[i], "DEFAULT"));
        }


        //solver.set(IntStrategyFactory.firstFail_InDomainMin(ArrayUtils.append(ripped)));
        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(ripped)));
        solver.findSolution();
        return solver.nextSolution();
       //ystem.out.println("Number of solutions: " + theCheck);

    }

    public int countUnknowns() {
		int unknowns = 0;
		for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(ripped[i][j].getValue() == 0) {
                	unknowns++;
                }
            }
        }
		return unknowns;
	}




    //Önnur aðferð til að setja í boxing
    //Method to populate Boxing #2
    /*for(int i = 0; i < SIZE; i+=3){
        for(int j = 0; j < SIZE; j++){
            boxing[i][j] = ripped[0][i+j];
            boxing[i][j+3] = ripped[1][i+j];
            boxing[i][j+6] = ripped[2][i+j];

            boxing[i+3][j] = ripped[3][i+j];
            boxing[i+3][j+3] = ripped[4][i+j];
            boxing[i+3][j+6] = ripped[5][i+j];

            boxing[i+6][j] = ripped[6][i+j];
            boxing[i+6][j+3] = ripped[7][i+j];
            boxing[i+6][j+6] = ripped[8][i+j];
        }
    }*/



}

