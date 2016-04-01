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


    public Agent(String board){
        Board = board;
    }

    public void test(){
        Solver solver = new Solver("First problem");
        IntVar[][] vs = VariableFactory.boundedMatrix("vs", 9, 9, 1, 9, solver);
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                vs[i][j] = Board.charAt(j+i);
            }
        }
        solver.post(IntConstraintFactory.arithm(x, "+", y, "<", 5));
        // 8 // 4. Define the search strategy 9
        solver.set(IntStrategyFactory.lexico_LB(x, y));
        // 10 // 5. Launch the resolution process
        solver.findSolution();
        //6. Print search statistics 13
        Chatterbox.printStatistics(solver);

    }

}
