import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        List<int[]> lines = br.lines().map(s -> s.replace(" -> ", ",").split(","))
                .map(strArray -> Arrays.stream(strArray).mapToInt(Integer::parseInt).toArray()).collect(Collectors.toList());

        System.out.println(problemOne(lines));
        System.out.println(problemTwo(lines));

    }

    public static int problemOne(List<int[]> lines)
    {
        List<int[]> filtered = lines.stream().filter(arr -> arr[0] == arr[2] || arr[1] == arr[3]).collect(Collectors.toList());

        int[][] map = new int[1000][1000];

        for(int[] arr : filtered)
        {
            addHorizontalOrVerticalVent(map, arr);
        }

        return countOverlappingVents(map);
    }

    public static int problemTwo(List<int[]> lines)
    {
        int[][] map = new int[1000][1000];

        for(int[] arr: lines)
        {
            boolean x1EqualToX2 = arr[0] == arr[2];
            boolean y1EqualToY2 = arr[1] == arr[3];

            if(x1EqualToX2 || y1EqualToY2)
            {
                addHorizontalOrVerticalVent(map, arr);
            }
            else
            {
                addDiagonalVent(map, arr);
            }

        }

        return countOverlappingVents(map);

    }

    public static void addHorizontalOrVerticalVent(int[][] map, int[] input)
    {
        int lowest;
        int highest;
        //x1 and x2 are the same, find min and max for y
        if(input[0] == input[2])
        {
            lowest = Math.min(input[1], input[3]);
            highest = Math.max(input[1], input[3]);

            while(lowest <= highest)
            {
                ++map[input[0]][lowest];

                ++lowest;
            }

        }
        else //y1 and y2 are the same, find min and max for x
        {
            lowest = Math.min(input[0], input[2]);
            highest = Math.max(input[0], input[2]);

            while(lowest <= highest)
            {
                ++map[lowest][input[1]];

                ++lowest;
            }
        }
    }

    public static void addDiagonalVent(int[][] map, int[] input)
    {
        boolean x1LowerThanX2 = input[0] < input[2];
        boolean y1LowerThanY2 = input[1] < input[3];

        int x1 = input[0];
        int x2 = input[2];
        int y1 = input[1];
        int y2 = input[3];

        if (x1LowerThanX2 && y1LowerThanY2)
        {
            while (x1 <= x2 && y1 <= y2)
            {
                ++map[x1][y1];

                ++x1;
                ++y1;
            }
        }
        else if (!x1LowerThanX2 && !y1LowerThanY2)
        {
            while (x1 >= x2 && y1 >= y2)
            {
                ++map[x1][y1];

                --x1;
                --y1;
            }
        }
        else if (x1LowerThanX2)
        {
            while (x1 <= x2 && y1 >= y2)
            {
                ++map[x1][y1];

                ++x1;
                --y1;
            }
        }
        else
        {
            while (x1 >= x2 && y1 <= y2)
            {
                ++map[x1][y1];

                --x1;
                ++y1;
            }
        }
    }

    public static int countOverlappingVents(int[][] map)
    {
        int count = 0;
        for(int[] outer: map)
        {
            for(int inner: outer)
            {
                if(inner > 1)
                {
                    ++count;
                }
            }
        }

        return count;
    }

}
