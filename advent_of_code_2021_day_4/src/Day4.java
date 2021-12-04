import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Day4
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        int[] input = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();

        //System.out.println(problemOne(input, br));
        System.out.println(problemTwo(input, br));
    }

    public static int problemOne(int[] input, BufferedReader br) throws IOException
    {
        ArrayList<Board> boards = Board.createBoards(br);

        for (int i = 0; i < input.length; ++i)
        {
            int finalI = i;
            Optional<Integer> win = boards.stream().map(b -> b.markNumber(input[finalI])).filter(num -> num > 0).findAny();

            if (win.isPresent())
                return win.get();
        }

        return -5;
    }

    public static int problemTwo(int[] input, BufferedReader br) throws IOException
    {
        ArrayList<Board> boards = Board.createBoards(br);

        for (int i = 0; i < input.length; ++i)
        {

            for(int j = 0, boardSize = boards.size(); j < boardSize; ++j, boardSize = boards.size())
            {
                int num = boards.get(j).markNumber(input[i]);
                if(num > 0 && boards.size() == 1)
                {
                    return num;
                }
                else if(num > 0)
                {
                    boards.remove(j);
                    --j;
                }
            }

        }

        return -5;
    }
}
