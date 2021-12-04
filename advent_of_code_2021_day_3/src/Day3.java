import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day3
{
    static final int BITS = 12;

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        List<String> nums = br.lines().collect(Collectors.toList());

        System.out.println(problemOne(nums));
        System.out.println(problemTwo(nums));
    }

    public static int problemOne(List<String> nums)
    {
        int[] bitCount = getBitCount(nums);

        int gamma = 0;
        int epsilon = 0;
        for(int i = 0, numSize = nums.size(); i < BITS; ++i)
        {
            /***
             * if the total sum for a specific bit is less than the total number of numbers, then 0 is the most frequent bit.
             * bitshift value to the left once and do a bitwise or to add a 1 at the end.
             */

            //1 is least significant number
            if(bitCount[i] < numSize / 2)
            {
                epsilon = (epsilon << 1) | 1;
                gamma <<= 1;
            }
            else
            {
                gamma = (gamma << 1) | 1;
                epsilon <<= 1;
            }
        }

        return gamma * epsilon;
    }

    public static int problemTwo(List<String> nums)
    {
        List<String> nums2 = new ArrayList<>(nums.size());
        nums2.addAll(nums);


        int oxygen = getOxygenOrCo2('1', nums);
        int co2 = getOxygenOrCo2('0', nums2);

        return oxygen * co2;

    }

    //rating1 is the one that should be >= the number of bits in each position
    public static int getOxygenOrCo2(char rating1, List<String> nums)
    {
        for(int i = 0, numSize = nums.size(); numSize > 1; ++i, numSize = nums.size())
        {
            HashSet<String> numIdxsOfRating1 = new HashSet<>(numSize);
            HashSet<String> numIdxsOfRating2 = new HashSet<>(numSize);

            //store all indexes where rating1 is available at the specified ith bit position
            for(int j = 0; j < numSize; ++j)
            {
                String str = nums.get(j);
                if(str.charAt(i) == rating1)
                    numIdxsOfRating1.add(str);
                else
                {
                    numIdxsOfRating2.add(str);
                }
            }

            if (rating1 == '1')
            {
                //remove all instances of numbers that contain rating1 at the ith bit position
                if(numIdxsOfRating1.size() < numIdxsOfRating2.size())
                {
                    nums.removeAll(numIdxsOfRating1);
                }
                else //remove all instances of numbers that contain rating2 at the ith bit position
                {
                    nums.removeAll(numIdxsOfRating2);
                }
            }
            else
            {
                //remove all instances of numbers that contain rating1 at the ith bit position
                if(numIdxsOfRating1.size() <= numIdxsOfRating2.size())
                {
                    nums.removeAll(numIdxsOfRating2);
                }
                else //remove all instances of numbers that contain rating2 at the ith bit position
                {
                    nums.removeAll(numIdxsOfRating1);
                }
            }

        }

        return Integer.parseInt(nums.get(0),2);
    }

    //returns total sum of bit count per each bit position
    public static int[] getBitCount(List<String> nums)
    {
        int[] bitCount = new int[BITS];

        //sums up the total value of each bit position
        for(int i = 0, numSize = nums.size(); i < numSize; ++i)
        {
            String number = nums.get(i);
            for(int j = 0; j < BITS; ++j)
            {
                bitCount[j] += number.charAt(j) - '0';
            }
        }

        return bitCount;
    }
}
