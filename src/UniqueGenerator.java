import org.chocosolver.solver.variables.IntVar;

public class UniqueGenerator {

    public static int[][] UniqueSudokuGen(){
        int[][] newBoard = new int[9][9];
        //getting a random board
        Boolean fuck = false;
        while(!fuck) {
            newBoard = Generator.SudokuGen();

            Agent initialSolver = new Agent(newBoard);

            Boolean singleSolution = initialSolver.solutionChecker();

            if(singleSolution){
                //System.out.println("multiple");
            }
            else{
                //System.out.println("single");
                fuck = true;
            }

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
}
