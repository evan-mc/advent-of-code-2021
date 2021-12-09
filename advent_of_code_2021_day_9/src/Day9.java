import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Day9
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));
        ArrayList<ArrayList<Integer>> map = new ArrayList<>();

        br.lines().map(String::toCharArray).forEach(charArr -> {
            ArrayList<Integer> row = new ArrayList<>();
            for(char c: charArr)
            {
                row.add(c - '0');
            }
            map.add(row);
        });

        System.out.println(problemOne(map));
        System.out.println(problemTwo(map));

    }

    public static int problemOne(ArrayList<ArrayList<Integer>> map)
    {
        int sumOfRisk = 0;

        //num that will always be bigger than current value
        final int BIGGER_NUM = 10;

        for(int i = 0, numOfRows = map.size(); i < numOfRows; ++i)
        {
            for(int j = 0, rowSize = map.get(i).size(); j < rowSize; ++j)
            {
                int leftValue = j != 0 ? map.get(i).get(j - 1) : BIGGER_NUM;
                int rightValue = j != map.get(i).size() - 1 ? map.get(i).get(j + 1) : BIGGER_NUM;
                int topValue = i != 0 ? map.get(i - 1).get(j) : BIGGER_NUM;
                int bottomValue = i != map.size() - 1 ? map.get(i + 1).get(j) : BIGGER_NUM;

                int currentValue = map.get(i).get(j);
                int minAdjValue = Math.min(Math.min(leftValue, rightValue), Math.min(topValue, bottomValue));

                sumOfRisk += currentValue < minAdjValue ? currentValue + 1 : 0;
            }
        }

        return sumOfRisk;
    }

    public static int problemTwo(ArrayList<ArrayList<Integer>> map)
    {
        boolean[][] visited = new boolean[map.size()][map.get(0).size()];

        ArrayList<Integer> basinSizes = new ArrayList<>();

        for(int i = 0, numOfRows = map.size(); i < numOfRows; ++i)
        {
            for(int j = 0, rowSize = map.get(i).size(); j < rowSize; ++j)
            {
                if(map.get(i).get(j) != 9 && !visited[i][j])
                {
                    int count = dfs(map, visited, i, j);

                    basinSizes.add(count);
                }
            }
        }

        Collections.sort(basinSizes);
        int arrEnd = basinSizes.size();
        return basinSizes.get(arrEnd - 1) * basinSizes.get(arrEnd - 2) * basinSizes.get(arrEnd - 3);

    }

    public static int dfs(ArrayList<ArrayList<Integer>> map, boolean[][] visited, int i, int j)
    {
        //out of bounds, is not a basin(9), or seen before
        if(i < 0 || j < 0 || i == map.size() || j == map.get(i).size() || map.get(i).get(j) == 9 || visited[i][j])
            return 0;

        visited[i][j] = true;

        int top = dfs(map, visited, i - 1, j);
        int bottom = dfs(map, visited, i + 1, j);
        int left = dfs(map, visited, i, j - 1);
        int right = dfs(map, visited, i, j + 1);

        return 1 + top + bottom + left + right;
    }
}
