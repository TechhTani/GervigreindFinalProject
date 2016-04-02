/**
 * Created by Kristján on 1.4.2016.
 */
public class Grid {

    int[][] Grid;

    public Grid(int[][] grid){
        Grid = grid;
    }

    public int[][] getGrid(){
        return Grid;
    }
    public int getCell(int i, int j){
        return Grid[i][j];
    }

}
