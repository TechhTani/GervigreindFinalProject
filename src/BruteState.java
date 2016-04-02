import java.util.ArrayList;

/**
 * Created by Kristj�n on 1.4.2016.
 */
public class BruteState {

    /*
	 * Map of suduko table
	 */

    //Each cell contains value & possible values
    Cell[][] sudukoTable = new Cell[9][9];

    public BruteState(Cell[][] sudukoTable){
        this.sudukoTable = sudukoTable;
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
        return null;
    }

    public void forceRulesToCellsPossibleValues(){

    }

    public BruteState getNextState(String action){
        return null;
    }

    public boolean isGoal(){
        return false;
    }

    public int evaluate(){
        return 0;
    }










}
