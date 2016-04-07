import java.util.ArrayList;

/**
 * Created by Natan on 02.04.2016.
 */
public class BruteForceAgent {

    final int SIZE = 9;
    Cell[][] board;
    BruteState baseState;

    public BruteForceAgent(int[][] board){
        //Initialize the Cell array
        this.board = new Cell[SIZE][SIZE];

        //Convert int[][] into Cell[][]
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(board[i][j] != 0){
                    this.board[i][j] = new Cell();
                    this.board[i][j].possibleValues = new int[]{};
                    this.board[i][j].value = board[i][j];
                }
                else{
                    this.board[i][j] = new Cell();
                }
            }
        }

        baseState = new BruteState(this.board, SIZE);
        //Fixed the "possibleValues" array, so it only contains valid numbers
    }

    public boolean solve(){
        BruteState answer = bruteForce(baseState);
        if(answer == null)
            return false;
        answer.printBoard();
        return true;
    }

    public BruteState bruteForce(BruteState state){

        //Check if goal? (completed without rule break)
        if(state.isGoal()){
            return state;
        }

        //Get next LEGAL moves? (if none then return)
        ArrayList<BruteState> legalMoves = state.legalMoves();

        if(legalMoves == null) //no legal moves found (INVALID SUDOKU STATE)
            return null;

        //Start going through possible moves & search for solution
        for(BruteState nextMove : legalMoves){
            //Create new BruteNode with next move

            //Call bruteForce() function with new BruteNode
            BruteState answer = bruteForce(nextMove);
            //if Solution is found, return that BruteNode, else return NULL
            if(answer != null)
                return answer;
        }

        //no answers found for this state.
        return null;
    }

}
