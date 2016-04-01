/**
 * Created by Kristj�n on 1.4.2016.
 */
import static org.chocosolver.solver.search.strategy.ISF.*;

public class State {

    /*
	 * Map of suduko table
	 */

    int[][] sudukoTable = new int[9][9];

    public State(int[][] sudukoTable){
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

    public State getNextState(String action){
        return null;
    }

    public boolean isGoal(){
        return false;
    }

    public int evaluate(){
        return 0;
    }










}
