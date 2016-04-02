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
        this.board = new Cell[][]{};

        //Convert int[][] into Cell[][]
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; i < SIZE; j++){
                if(board[i][j] != 0){
                    this.board[i][j].possibleValues = new int[]{};
                    this.board[i][j].value = board[i][j];
                }
            }
        }

        baseState = new BruteState(this.board);
        baseState.forceRulesToCellsPossibleValues(); //Fixed the "possibleValues" array, so it only contains valid numbers
    }

    public BruteNode bruteForce(BruteNode n){

        //Check if goal? (completed without rule break)
        if(n.state.isGoal()){
            return n;
        }

        //Get next LEGAL moves? (if none then return)
        ArrayList<BruteNode> legalMoves = n.state.legalMoves();

        //Start going through possible moves & search for solution
        for(BruteNode nextMove : legalMoves){
            //Create new BruteNode with next move

            //Call bruteForce() function with new BruteNode
            BruteNode answer = bruteForce(nextMove);
            //if Solution is found, return that BruteNode, else return NULL
            if(answer != null)
                return answer;
        }


        return null;
    }

}
