import java.util.ArrayList;

/**
 * Created by Kristj�n on 1.4.2016.
 */
public class BruteState {

    /*
	 * Map of suduko table
	 */

    //Each cell contains value & possible values
    Cell[][] sudukoTable;
    int SIZE;

    public BruteState(Cell[][] sudukoTable, int SIZE){
        this.sudukoTable = sudukoTable;
        this.SIZE = SIZE;
    }

    @Override
    public int hashCode(){
        return 0;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    public ArrayList<BruteNode> legalMoves() {
        //call sudukoRulesEnforce()
        if(!sudukoRulesEnforce())
            return null;

        ArrayList<BruteState> newMoves = new ArrayList<BruteState>();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(sudukoTable[i][j].value == 0){
                    for(int item : sudukoTable[i][j].possibleValues){
                        //Create new cell to add to ArrayList, then change "value" for empty cell (these are the next moves)
                        Cell[][] newCell = sudukoTable;
                        newCell[i][j].value = item;

                        newMoves.add(new BruteState(newCell,SIZE));
                    }
                    break;
                }
            }
        }

        //No legal moves found (finished??)
        return null;
    }

    public boolean sudukoRulesEnforce(){
        //Enforce rules for suduko.

        // 1. Go through each know value, and remove from domain


        return false;
    }

    public BruteState getNextState(String action){
        return null;
    }

    public boolean isGoal(){
        //check if all cells have value (completed Suduko)

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(sudukoTable[i][j].value == 0)
                    return false;
            }
        }

        //then check if is valid
        if(!sudukoRulesEnforce()){
            return false;
        }

        //if true, then we found goal
        return true;
    }

    public int evaluate(){
        return 0;
    }

}
