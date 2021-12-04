import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that repesents a 5x5 bingo board
 */
public class Board
{
    private static final int DIMENSION = 5;

    //lines that make up the board
    private int[][] boardLines;

    //keeps track of what indexes in the boardLines array have been marked. 1 = marked, 0 = unmarked
    private boolean[][] marked;

    private int sumOfUnmarked;

    public Board(int[][] boardLines)
    {
        this.boardLines = boardLines;
        marked = new boolean[5][5];

        //add all values to sum since nothing is marked yet
        for(int i = 0; i < DIMENSION; ++i)
        {
            for(int j = 0; j < DIMENSION; ++j)
            {
                sumOfUnmarked += boardLines[i][j];
            }
        }
    }

    //marks any instance of that number in the board. will return a non-negative number if that board won
    public int markNumber(int numberToMark)
    {
        //only one instance of number per board, so dont need to continue scanning board if we found it
        boolean exitFromOuter = false;

        for(int i = 0; i < DIMENSION; ++i)
        {
            for(int j = 0; j < DIMENSION; ++j)
            {
                if(boardLines[i][j] == numberToMark)
                {
                    exitFromOuter = true;
                    marked[i][j] = true;
                    sumOfUnmarked -= boardLines[i][j];

                    break;
                }
            }

            if(exitFromOuter)
                break;
        }

        //a new value was marked, so check if this board won
        if(exitFromOuter)
        {
            if(checkForWin())
            {
                return numberToMark * sumOfUnmarked;
            }
        }

        return -1;
    }

    private boolean checkForWin()
    {
        return checkForHorizontalWin() || checkForVerticalWin();
    }

    private boolean checkForHorizontalWin()
    {
        for(int i = 0; i < DIMENSION; ++i)
        {
            int count = 0;
            for(int j = 0; j < DIMENSION; ++j)
            {
                if(marked[i][j])
                    ++count;
                else
                    break;
            }

            if(count == 5)
                return true;
        }

        return false;
    }

    private boolean checkForVerticalWin()
    {
        for(int i = 0; i < DIMENSION; ++i)
        {
            int count = 0;
            for(int j = 0; j < DIMENSION; ++j)
            {
                if(marked[j][i])
                    ++count;
                else
                    break;
            }

            if(count == 5)
                return true;
        }

        return false;
    }

    //utility function for parsing input and creating all bingo boards
    public static ArrayList<Board> createBoards(BufferedReader br) throws IOException
    {
        //call br.readLine() once to get rid of initial empty line
        String str = br.readLine();

        ArrayList<Board> boards = new ArrayList<>();

        //keeps track of what index this should be in the board array
        int boardIdx = 0;
        int[][] boardLines = new int[0][];
        while((str = br.readLine()) != null)
        {
            if(str.isEmpty())
                continue;

            //create new boardLines for new board
            if(boardIdx == 0)
                boardLines = new int[5][5];

            boardLines[boardIdx++] = Arrays.stream(str.split(" ")).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();

            if(boardIdx == 5)
            {
                boards.add(new Board(boardLines));
                boardIdx = 0;
            }

        }

        return boards;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder(30);

        for(int i = 0; i < DIMENSION; ++i)
        {
            for(int j = 0; j < DIMENSION; ++j)
            {
                if(boardLines[i][j] < 10)
                    sb.append(' ');

                sb.append(boardLines[i][j]).append(' ');
            }

            sb.append('\n');
        }

        return sb.toString();
    }
}
