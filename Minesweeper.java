import java.util.*;

public class Minesweeper {
    public static void main(String []args){
        Scanner in = new Scanner(System.in);

        // This is my main method in which it includes all of the null values inside the arrays to fill in the values for the board when it's shown.
        // This while true loop shows the user the board's default which it then grabs the user's choice. If the cell chosen is within the range, it will run through the floodfill.
        // Then, once the game is over, it will reveal all mines. The mines will be an * if the player wins or the mine will be an x if user looses.

        int[][] board = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}};

        boolean[][] explored = {{false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}, 
                                {false, false, false, false, false, false, false, false, false}}; 
                                
                                int[][] mineLocation = setMines(board, 10);
                                countAdjacentMines(board);

        while(true){
                    showboard(board, explored);
                    System.out.println();
                    int[] choice = getUserChoice(explored);
                    explored[choice[0]][choice[1]] = true;
                        if(board[choice[0]][choice[1]] == 0){
                            floodFill(board, explored, choice[0], choice[1]);
                        }
                        else if(isGameOver(board, explored)){
                            boolean[][] newexplored = {{true, true, true, true, true, true, true, true, true}, 
                                                        {true, true, true, true, true, true, true, true, true},
                                                        {true, true, true, true, true, true, true, true, true},
                                                        {true, true, true, true, true, true, true, true, true},
                                                        {true, true, true, true, true, true, true, true, true}, 
                                                        {true, true, true, true, true, true, true, true, true}, 
                                                        {true, true, true, true, true, true, true, true, true}, 
                                                        {true, true, true, true, true, true, true, true, true}, 
                                                        {true, true, true, true, true, true, true, true, true}}; 
                            showboard(board, newexplored);
                            System.out.println();
                            System.out.println();
                            boolean result = false;
                                if(result == true){
                                    System.out.print("YOU WIN!");
                                }  
                                else{
                                    System.out.print("YOU LOSE!");
                                }
                                    break;
                        }
                    }
                }
    public static int[][] setMines(int [][]board, int mines){
    // This method creates the mine locations set at different locations randomly within the row and columns. 

        int row = 0;
        int col = 0;
        int[][] mineLocation = new int[mines][2];

        for(int i = 0; i < mines; i++){
            row = (int) (board.length * Math.random());
            col = (int) (board.length * Math.random());
            if(board[row][col] == -1){
                i-=1;
                continue;
            } 
            board [row][col] = -1;
            mineLocation[i][0] = row;
            mineLocation[i][1] = col;
        }
        return mineLocation;

    }
    public static void countAdjacentMines(int [][]board){
        // This increments the board's length from row to column and it checks all of the adjacent cells to determine if it's a mine. 
        // it shows you how many numbers are adjacent to each mine. 
        
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board.length; col++){

                int count = 0;

                if(board[row][col] == -1){
                    continue;
                }
                // Check upper-left cell
                if(row > 0 && col > 0){
                    if(board[row-1][col-1] == -1){
                        count++;
                    }
                }
                // Check middle-left cell
                if(col > 0){
                    if(board[row][col-1] == -1){
                        count++;
                    }
                }
                // Check bottom-left cell
                if(row < 8 && col > 0){
                    if(board[row+1][col-1] == -1){
                        count++;
                    }
                }
                // Check upper-middle cell
                if(row > 0){
                    if(board[row-1][col] == -1){
                        count++;
                    }
                }
                // Check bottom-middle cell
                if(row < 8){
                    if(board[row+1][col] == -1){
                        count++;
                    }
                }
                // Check upper-right cell
                if(row > 0 && col < 8){
                    if(board[row-1][col+1] == -1){
                        count++;
                    }
                }
                // Check middle-right cell
                if(col < 8){
                    if(board[row][col+1] == -1){
                        count++;
                    }
                }
                // Check bottom-right cell
                if(row < board.length - 1 && col < board[row].length - 1){
                    if(board[row+1][col+1] == -1){
                        count++;
                    }
                }
                board[row][col] = count;
            }
        }
    }
    
    public static void showboard(int [][]board, boolean [][]explored){

        // This prints out the board itself from 0 - 8.
        // if the board is not explored, it will show a dot. if a mine is explored, it will reveal an x when you lose.
        // it will reveal an * when you win to reveal all mines.
        // it will reveal a space if there is 0 value on the board. 

        System.out.println();
        System.out.println("    0 1 2 3 4 5 6 7 8");
        System.out.print("----------------------");

        for(int i = 0; i < board.length; i++){
            System.out.println();
            System.out.print(i + " | ");
            for(int j = 0; j < board[i].length; j++){
                if(!explored[i][j]){
                    System.out.print(". ");
                }
                else{
                if(board[i][j] == -1){
                    System.out.print("x ");
                }
                else if(board[i][j] == -2){
                    System.out.print("* ");
                }
                else if(board[i][j] == 0){
                    System.out.print("  ");
                }
                else{
                    System.out.print(board[i][j] + " ");
                }
            }
        }
    }
}
    public static int[] getUserChoice(boolean [][]board){

        // This calls for the user to select an inputted cell of their choice.
        // This nested loop determines if the value of the cell is within the range.
        // the rows and columns must be in between 0 - 8 (or less than 9 and greater than or equal to 0).
        // If the user doesn't input a valid cell, or a cell that was already used, it will summon the print statements made below.

        Scanner in = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.print("Select a valid cell (row, col): ");

            int row = in.nextInt();
            int col = in.nextInt();

            if(row < 9 && col < 9){  
                if(row >= 0 && col >= 0){
                    if(!board[row][col]){
                        int[] temp = {row, col};
                        return temp;
                    }
                    else{
                        System.out.print("Sorry, that cell has already been explored. Try again.");
                    }
                }
                else{
                    System.out.println("Sorry, one or more of those inputs are invalid. Try again.");
                }
            }
            else{
                System.out.println("Sorry, one or more of those inputs are invalid. Try again.");
            }
        }  
    }
    public static void floodFill(int [][]board, boolean[][] explored, int row, int col){

        // The floodfill checks all of the cells that are adjacent to the middle cell 

        // Check upper-left cell
        if(row > 0 && col > 0 && !explored[row-1][col-1]){
            explored[row-1][col-1] = true;
            if(board[row-1][col-1] == 0){
                floodFill(board, explored, row-1, col-1);
            }
        }
        // Check middle-left cell
        if(col > 0 && !explored[row][col-1]){
            explored[row][col-1] = true;
            if(board[row][col-1] == 0){
                floodFill(board, explored, row, col-1);
            }
        }
        // Check bottom-left cell
        if(row < board.length - 1 && col > 0 && !explored[row+1][col-1]){
            explored[row+1][col-1] = true;
            if(board[row+1][col-1] == 0){
                floodFill(board, explored, row+1, col-1);
            }
        }
        // Check upper-middle cell
        if(row > 0 && !explored[row-1][col]){
            explored[row-1][col] = true;
            if(board[row-1][col] == 0){
                floodFill(board, explored, row-1, col);
            }
        }
        // Check bottom-middle cell
        if(row < board.length - 1 && !explored[row+1][col]){
            explored[row+1][col] = true;
            if(board[row+1][col] == 0){
                floodFill(board, explored, row+1, col);
            }
        }
        // Check upper-right cell
        if(row > 0 && col < board[row].length - 1 && !explored[row-1][col+1]){
            explored[row-1][col+1] = true;
            if(board[row-1][col+1] == 0){
                floodFill(board, explored, row-1, col+1);
            }
        }
        // Check middle-right cell
        if(col < board[row].length - 1 && !explored[row][col+1]){
            explored[row][col+1] = true;
            if(board[row][col+1] == 0){
                floodFill(board, explored, row, col+1);
            }
        }
        // Check bottom-right cell
        if(row < board.length - 1 && col < board[row].length - 1 && !explored[row+1][col+1]){
            explored[row+1][col+1] = true;
            if(board[row+1][col+1] == 0){
                floodFill(board, explored, row+1, col+1);
            }
        }
    }
    public static boolean isGameOver(int [][]board, boolean [][]explored){

        // The method returns true if there is a mine and has been explored.
        // If a mine nor the board has been explored, it reveals the boolean value to be true.
        // When the player wins, the mines will become an *.
        
        boolean flag = false;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
        
                if(board[i][j] == -1 && explored[i][j]){
                    return true;
                }
                else if(board[i][j] != -1 && !explored[i][j]){
                    flag = true;
                }
            }
        }   
        if(!flag){
            System.out.print("YOU WIN!");
            
            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board.length; j++){
            
                    if(board[i][j] == -1){
                        board[i][j] = -2;
                    }
                    }
                }
            }   
        return !flag;
    }
}
