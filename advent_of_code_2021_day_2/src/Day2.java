import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day2
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        List<String[]> arr = br.lines().map(str -> str.split(" ")).collect(Collectors.toList());

        System.out.println(problemOne(arr));
        System.out.println(problemTwo(arr));
    }

    public static int problemOne(List<String[]> arr)
    {
        int depth = 0;
        int horizontal = 0;
        for(String[] elem: arr)
        {
            int value = Integer.parseInt(elem[1]);
            switch (elem[0])
            {
                case "forward":
                    horizontal += value;
                    break;
                case "up":
                    depth -= value;
                    break;
                case "down":
                    depth += value;
                    break;
            }
        }

        return depth * horizontal;
    }

    public static int problemTwo(List<String[]> arr)
    {
        int aim = 0;
        int depth = 0;
        int horizontal = 0;
        for(String[] elem: arr)
        {
            int value = Integer.parseInt(elem[1]);
            switch (elem[0])
            {
                case "forward":
                    horizontal += value;
                    depth += aim * value;
                    break;
                case "up":
                    aim -= value;
                    break;
                case "down":
                    aim += value;
                    break;
            }
        }

        return depth * horizontal;
    }
}

