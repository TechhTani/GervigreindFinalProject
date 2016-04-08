import java.util.Random;

public class Generator {

    public static int[][] SudokuGen(){
        int[][] newBoard = new int[9][9];
        //System.out.print("Getting board....");
        newBoard = getFullBoard(newBoard);

        //System.out.print("check!\n");
        return newBoard;
    }
    
    public static int[][] getFullBoard(int [][] theBoard){
        Random rand = new Random();
        Boolean puzzleFound = false;
        while(!puzzleFound){
            int[][] newBoard = new int[9][9];
            int numberOfClues = 0;
            while(numberOfClues < 17) {
                int randomX = rand.nextInt(9);
                int randomY = rand.nextInt(9);
                int randomValue = rand.nextInt((9 - 1) + 1) + 1;
                if (newBoard[randomX][randomY] == 0) {
                    newBoard[randomX][randomY] = randomValue;
                    numberOfClues += 1;
                }
            }
            BruteForceAgent BruteFA = new BruteForceAgent(newBoard);
            if(BruteFA.baseState.checkIfSudokuIsValid()){
                    theBoard = newBoard;
                    puzzleFound = true;

            }

        }
        return theBoard;
    }
    
    public static void Printer(int[][] printBoard){
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
