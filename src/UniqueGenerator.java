import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

/**
 * Created by Kristján on 5.4.2016.
 */
public class UniqueGenerator {

    public static int[][] UniqueSudokuGen(){
        int[][] newBoard = new int[9][9];
        //getting a random board
        Boolean fuck = false;
        while(!fuck) {
            newBoard = Generator.SudokuGen();
            //solve that board with CSP agent
            //System.out.println("Board generated");
            Agent initialSolver = new Agent(newBoard);
            //System.out.println("Attempting Solving");
            Boolean singleSolution = initialSolver.solutionChecker();
            //System.out.println("Solver ran")
            //Boolean checker = initialSolver.solver.findSolution();
            if(singleSolution){
                System.out.println("multiple");
            }
            else{
                System.out.println("single");
                fuck = true;
            }

            //System.out.println("Bye");
        }
        return newBoard;

    }
    public static void printer(IntVar[][] printBoard){
        StringBuilder sb = new StringBuilder();

        sb.append("\t");
        for (int i = 0; i < 9; i++) {
            if(i%3 == 0 && i != 0){
                sb.append("------------------------------- \n\t");
            }
            for (int j = 0; j < 9; j++) {
                if(j%3==0 && j!=0){
                    sb.append("|  ");
                }
                sb.append(printBoard[i][j].getValue()).append("  ");
            }
            sb.append("\n\t");
        }
        System.out.println(sb.toString());
    }
    /*public int[][] generator(int[][] theBoard){

    }*/


}
