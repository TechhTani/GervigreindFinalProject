/**
 * Created by Kristján on 1.4.2016.
 */
import org.chocosolver.solver.Solver;
import static org.chocosolver.solver.search.strategy.ISF.*;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;


public class Agent {

    String Board;
    Grid SudokuPuzzle;
    Solver solver;

    public Agent(String board){
        Board = board;
        solver = new Solver("titties");
    }

    public void Solver(){
        Solver solver = new Solver("First problem");
        IntVar[][] vs = VariableFactory.boundedMatrix("vs", 9, 9, 1, 9, solver);

        solver.post(IntConstraintFactory.arithm(x, "+", y, "<", 5));
        // 8 // 4. Define the search strategy 9
        solver.set(IntStrategyFactory.lexico_LB(x, y));
        // 10 // 5. Launch the resolution process
        solver.findSolution();
        //6. Print search statistics 13
        Chatterbox.printStatistics(solver);

    }

}
