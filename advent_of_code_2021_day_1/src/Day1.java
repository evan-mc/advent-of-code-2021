import java.io.*;

public class Day1
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        int[] nums = br.lines().mapToInt(Integer::parseInt).toArray();

        System.out.println(countNumOfIncreases(nums, nums.length));
        System.out.println(problemTwo(nums));
    }

    public static int problemTwo(int[] nums)
    {
        //add sliding window of numbers to get new input
        for(int i = 2; i < nums.length; ++i)
        {
            nums[i - 2] = nums[i] + nums[i - 1] + nums[i - 2];
        }

        final int NEW_LENGTH = nums.length - 2;

        return countNumOfIncreases(nums, NEW_LENGTH);
    }

    public static int countNumOfIncreases(int[] nums, int length)
    {
        int count = 0;
        for(int i = 1; i < length; ++i)
        {
            if(nums[i] > nums[i - 1])
            {
                ++count;
            }
        }

        return count;
    }
}
