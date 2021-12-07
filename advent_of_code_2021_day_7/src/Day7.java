import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day7
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        int[] nums = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).sorted().toArray();

        System.out.println(problemOne(nums));
        System.out.println(problemTwo(nums));
    }

    public static int problemOne(int[] nums)
    {
        return calculateLeastFuelUsage(nums, false);
    }

    public static int problemTwo(int[] nums)
    {
        return calculateLeastFuelUsage(nums, true);
    }

    public static int calculateLeastFuelUsage(int[] nums, boolean useIncrement)
    {
        int numToMoveTo = 0;
        int leastFuel = Integer.MAX_VALUE;
        while(numToMoveTo <= nums[nums.length - 1])
        {
            int currentLeastFuel = 0;
            for(int i = 0; i < nums.length; ++i)
            {
                int difference = Math.abs(nums[i] - numToMoveTo);

                currentLeastFuel += useIncrement ? ((difference + 1) * (difference) / 2) : difference;
            }

            leastFuel = Math.min(leastFuel, currentLeastFuel);

            ++numToMoveTo;
        }

        return leastFuel;
    }
}
