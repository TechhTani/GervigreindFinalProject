import java.util.ArrayList;
import java.util.List;

public class BruteState {

    /*
	 * Map of sudoku table
	 */

    //Each cell contains value & possible values
    Cell[][] sudokuTable;
    int SIZE;

    public BruteState(Cell[][] sudokuTable, int SIZE){

        this.sudokuTable = new Cell[SIZE][SIZE];

        //completely re initialize the table, so we don't get same memory addresses
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                Cell newCell = new Cell();
                newCell.value = sudokuTable[i][j].value;
                newCell.possibleValues = new int[SIZE];

                if(sudokuTable[i][j].possibleValues.length != 0){
                    for(int x= 0; x < SIZE; x++){
                        newCell.possibleValues[x] = sudokuTable[i][j].possibleValues[x];
                    }
                }

                this.sudokuTable[i][j] = newCell;
            }
        }
        this.SIZE = SIZE;
    }

    public ArrayList<BruteState> legalMoves() {
        //call sudokuRulesEnforce()
        if(!checkIfSudokuIsValid())
            return null;

        ArrayList<BruteState> newMoves = new ArrayList<BruteState>();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(sudokuTable[i][j].value == 0){
                    for(int item : sudokuTable[i][j].possibleValues){
                        //Create new cell to add to ArrayList, then change "value" for empty cell (these are the next moves)
                        Cell[][] newCell = sudokuTable;
                        newCell[i][j].value = item;
                        newMoves.add(new BruteState(newCell,SIZE));
                    }
                    return newMoves;
                }
            }
        }

        //No legal moves found (finished??)
        return null;
    }

    public boolean checkIfSudokuIsValid(){
        //Enforce rules for sudoku.

        // 1. check each row if it is valid
        for(int i = 0; i < SIZE; i++){
            List<Integer> rowArray = new ArrayList<Integer>(); //stores all values for a ROW
            List<Integer> columnArray = new ArrayList<Integer>(); //stores all values for a COLUMN

            //CHECK ROW IF IT HAS TWO OF SAME DIGITS
            for(int j = 0; j < SIZE; j++){
                if(sudokuTable[i][j].value != 0){ //check if value in this cell (ROW)
                    if(!rowArray.contains(sudokuTable[i][j].value)) //check if value is in array already
                        rowArray.add(sudokuTable[i][j].value);
                    else
                        return false; //if value is already in array, then there are 2 of same number in a row.
                }

                //CHECK COLUMN IF IT HAS TWO OF SAME DIGITS
                if(sudokuTable[j][i].value != 0){
                    if(!columnArray.contains(sudokuTable[j][i].value)) //check if value is in array already
                        columnArray.add(sudokuTable[j][i].value);
                    else
                        return false; //if value is already in array, then there are 2 of same number in a row.
                }
            }
        }

        int[][] sudokuBoxes = getSudokuBoxes();

        //This for-for loop does the same thing as the two above.
        for(int i = 0; i < SIZE; i++){
            List<Integer> boxArray = new ArrayList<Integer>(); //stores all values for a BOX
            for(int j = 0; j < SIZE; j++){
                if(sudokuBoxes[i][j] != 0){
                    if(!boxArray.contains(sudokuBoxes[i][j]))
                        boxArray.add(sudokuBoxes[i][j]);
                    else
                        return false;
                }
            }
        }

        return true;
    }

    public int[][] getSudokuBoxes(){

        int[][] boxing = new int[SIZE][SIZE];

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boxing[i][j] = sudokuTable[0][(i*3)+j].value;
                boxing[i][j+3] = sudokuTable[1][(i*3)+j].value;
                boxing[i][j+6] = sudokuTable[2][(i*3)+j].value;

                boxing[i+3][j] = sudokuTable[3][(i*3)+j].value;
                boxing[i+3][j+3] = sudokuTable[4][(i*3)+j].value;
                boxing[i+3][j+6] = sudokuTable[5][(i*3)+j].value;

                boxing[i+6][j] = sudokuTable[6][(i*3)+j].value;
                boxing[i+6][j+3] = sudokuTable[7][(i*3)+j].value;
                boxing[i+6][j+6] = sudokuTable[8][(i*3)+j].value;
            }
        }

        return boxing;
    }

    public BruteState getNextState(String action){
        return null;
    }

    public boolean isGoal(){
        //check if all cells have value (completed sudoku)

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(sudokuTable[i][j].value == 0)
                    return false;
            }
        }

        //then check if is valid
        if(!checkIfSudokuIsValid()){
            return false;
        }

        return true;
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();
        int unknowns = 0;

        sb.append("\t");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(sudokuTable[i][j].value).append("  ");
                if(sudokuTable[i][j].value == 0) {
                    unknowns++;
                }
            }
            sb.append("\n\t");
        }
        System.out.println(sb.toString());
        System.out.println("Unknowns are " + unknowns);
    }

}
