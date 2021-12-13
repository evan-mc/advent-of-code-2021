import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day11
{
    static int countOfFlashes = 0;
    static int countOfResets = 0;

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));
        ArrayList<ArrayList<Integer>> probOne = new ArrayList<>();
        ArrayList<ArrayList<Integer>> probTwo = new ArrayList<>();

        br.lines().map(String::toCharArray).forEach(charArr -> {
            ArrayList<Integer> row1 = new ArrayList<>();
            ArrayList<Integer> row2 = new ArrayList<>();
            for(char c: charArr)
            {
                int charConverted = c - '0';
                row1.add(charConverted);
                row2.add(charConverted);
            }
            probOne.add(row1);
            probTwo.add(row2);
        });


        System.out.println(problemOne(probOne));
        System.out.println(problemTwo(probTwo));

    }

    public static int problemOne(ArrayList<ArrayList<Integer>> map)
    {
        for(int time = 0; time < 100; ++time)
        {
            boolean[][] hasReset = new boolean[map.size()][map.get(0).size()];
            for(int i = 0, mapSize = map.size(); i < mapSize; ++i)
            {
                for(int j = 0, innerSize = map.get(i).size(); j < innerSize; ++j)
                {
                    dfs(map, hasReset, i, j);
                }
            }
        }

        return countOfFlashes;
    }

    public static int problemTwo(ArrayList<ArrayList<Integer>> map)
    {
        int countOfSteps = 0;
        while(true)
        {
            ++countOfSteps;
            boolean[][] hasReset = new boolean[map.size()][map.get(0).size()];
            for(int i = 0, mapSize = map.size(); i < mapSize; ++i)
            {
                for(int j = 0, innerSize = map.get(i).size(); j < innerSize; ++j)
                {
                    dfs(map, hasReset, i, j);
                    if(countOfResets == 100)
                        return countOfSteps;
                }
            }

            countOfResets = 0;
        }

    }

    public static void dfs(ArrayList<ArrayList<Integer>> map, boolean[][] hasReset, int i, int j)
    {
        //out of bounds or has already reset
        if(i < 0 || j < 0 || i == map.size() || j == map.get(i).size() || hasReset[i][j])
            return;

        map.get(i).set(j, map.get(i).get(j) + 1);

        //add 1 to any adjacent values
        if(map.get(i).get(j) > 9)
        {
            hasReset[i][j] = true;
            ++countOfFlashes;
            //top
            dfs(map, hasReset, i - 1, j);
            //bottom
            dfs(map, hasReset, i + 1, j);
            //left
            dfs(map, hasReset, i, j - 1);
            //right
            dfs(map, hasReset, i, j + 1);
            //top left
            dfs(map, hasReset, i - 1, j - 1);
            //top right
            dfs(map, hasReset, i - 1, j + 1);
            //bottom left
            dfs(map, hasReset, i + 1, j - 1);
            //bottom right
            dfs(map, hasReset, i + 1, j + 1);

            map.get(i).set(j, 0);
            ++countOfResets;
        }
    }
}
