/**
 * Created by Kristján on 1.4.2016.
 */
import org.chocosolver.solver.Solver;
import static org.chocosolver.solver.search.strategy.ISF.*;
import org.chocosolver.util.tools.ArrayUtils;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;


public class Agent {
    final int SIZE = 9;
    int[][] Board;
    SudokuGrid SudokuPuzzle;

    Solver solver;

    public Agent(int[][] board){
        Board = board;
        solver = new Solver("Big Titties");
        SudokuPuzzle = new SudokuGrid(board);
    }
    
    public void Solver(){

        IntVar[][] ripped = new IntVar[SIZE][SIZE];
        IntVar[][] colons = new IntVar[SIZE][SIZE];
        IntVar[][] boxing = new IntVar[SIZE][SIZE];

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

        solver.set(IntStrategyFactory.minDom_LB(ArrayUtils.append(ripped)));
        ///fingerscrossed
        //solver.set(IntStrategyFactory.firstFail_InDomainMin(ArrayUtils.append(ripped)));
        if(solver.findSolution()){
            System.out.println("SolvedBitch");
        }

    }

}
