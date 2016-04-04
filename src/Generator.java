/**
 * Created by Kristján on 2.4.2016.
 */
import java.util.Random;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VariableFactory;
import org.chocosolver.util.tools.ArrayUtils;
public class Generator {



    public static int[][] SudokuGen(){
        int[][] newBoard = new int[9][9];

        newBoard = getFullBoard(newBoard);
        System.out.println("DA BOARD IS HEA");
        Printer(newBoard);


        return newBoard;
    }
    public static int[][] getFullBoard(int[][] theBoard){
        Random rand = new Random();
        Boolean uniqueFound = false;
        int counter = 0;
        while(!uniqueFound) {
            counter++;
            int random1 = rand.nextInt(9);
            int random2 = rand.nextInt(9);
            int randomValue = rand.nextInt((9 - 1) + 1) + 1;
            System.out.println("Rand1:" + random1 + " - Rand2: " + random2);
            if(theBoard[random1][random2] == 0){
                theBoard[random1][random2] = randomValue;
            }
            Solver saul = generatingSolver(theBoard);

            if(saul.findSolution()){
                if(saul.nextSolution())
                    theBoard[random1][random2] = 0;
            }
            else{
                if(isFilled(theBoard)){
                    uniqueFound = true;
                }
            }

        }
        System.out.println("iterations:" + counter);
        return theBoard;
    }
    public static void Printer(int[][] printBoard){
        StringBuilder sb = new StringBuilder();

        sb.append("\t");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(printBoard[i][j]).append("  ");
            }
            sb.append("\n\t");
        }
        System.out.println(sb.toString());
    }
    public static boolean isFilled(int[][] checkBoard){
        for(int i = 0; i < 9; i++){
            for(int j = 0 ; j < 9 ; j++){
                if(checkBoard[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }
    public static Solver generatingSolver(int[][] checkBoard){


        Solver solver = new Solver();
        SudokuGrid SudokuPuzzle = new SudokuGrid(checkBoard);
        int SIZE = 9;
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
        return solver;
    }

}
