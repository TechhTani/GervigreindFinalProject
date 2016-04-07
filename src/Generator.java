/**
 * Created by Kristj�n on 2.4.2016.
 */
import java.util.Random;
public class Generator {



    public static int[][] SudokuGen(){
        int[][] newBoard = new int[9][9];

        newBoard = getFullBoard(newBoard);
        System.out.println("DA BOARD IS HEA");
        Printer(newBoard);


        return newBoard;
    }
    public static int[][] getFullBoard(int [][] theBoard){
        Random rand = new Random();
        Boolean puzzleFound = false;
        int numbersonboard = 0;
        while(!puzzleFound){
            int numberOfClues = 0;
            while(numberOfClues < 17) {
                int randomX = rand.nextInt(9);
                int randomY = rand.nextInt(9);
                int randomValue = rand.nextInt((9 - 1) + 1) + 1;
                if (theBoard[randomX][randomY] == 0) {
                    theBoard[randomX][randomY] = randomValue;
                    numberOfClues += 1;
                }
            }
            //CSPAgent tester = new CSPAgent(theBoard);
            BruteForceAgent tester = new BruteForceAgent(theBoard);
            if(tester.solve()){
                puzzleFound = true;
            }
        }
        return theBoard;
    }
    /*public static int[][] getFullBoard(int[][] theBoard){
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
    }*/
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

}
