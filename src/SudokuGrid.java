/**
 * Created by Kristj�n on 1.4.2016.
 */
public class SudokuGrid {

    int[][] Grid;

    public SudokuGrid(int[][] grid){
        Grid = grid;
    }

    public int[][] getGrid(){
        return Grid;
    }
    public int getCell(int i, int j){
        return Grid[i][j];
    }

}
