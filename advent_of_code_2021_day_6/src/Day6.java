import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        int[] nums = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("problem one: " + getNumOfFishes(80, nums));
        System.out.println("problem two: " + getNumOfFishes(256, nums));

    }

    public static long getNumOfFishes(int numOfDaysLeft, int[] nums)
    {
        long[] countOfFish = new long[9];

        for (int num : nums)
        {
            ++countOfFish[num];
        }

        while(numOfDaysLeft > 0)
        {

            long prevNumToMove = 0;

            //loop through all numbers in array
            for(int i = 8; i >= 0; --i)
            {

                //store how many fishes need to move down to next day
                prevNumToMove = countOfFish[i] - prevNumToMove;
                if(i != 0)
                {
                    //move fishes down to next level
                    countOfFish[i - 1] += prevNumToMove;
                }
                else //reset fish to beginning, add new fishes
                {
                    countOfFish[6] += prevNumToMove;
                    countOfFish[8] += prevNumToMove;
                }

                //remove all fish that were here before new fish showed up
                countOfFish[i] -= prevNumToMove;
            }

            --numOfDaysLeft;
        }

        long totalCountOfFishes = 0;

        for(int i = 0; i < 9; ++i)
        {
            totalCountOfFishes += countOfFish[i];
        }

        return totalCountOfFishes;

    }
}
