import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));

        List<String> lines = br.lines().collect(Collectors.toList());

        HashMap<Character, Character> pairs = new HashMap<>();
        pairs.put('(', ')');
        pairs.put('[', ']');
        pairs.put('{', '}');
        pairs.put('<', '>');

        System.out.println(problemOne(lines, pairs));
        System.out.println(problemTwo(lines, pairs));

    }

    public static int problemOne(List<String> lines, HashMap<Character, Character> pairs)
    {
        HashMap<Character, Integer> countOfEnding = new HashMap<>();
        countOfEnding.put(')', 0);
        countOfEnding.put(']', 0);
        countOfEnding.put('}', 0);
        countOfEnding.put('>', 0);

        for(String line: lines)
        {
            Stack<Character> openings = new Stack<>();
            for(char c: line.toCharArray())
            {
                //openings
                if(c == '(' || c == '[' || c == '{' || c == '<')
                {
                    openings.push(c);
                }
                else //its a closing
                {
                    if(pairs.get(openings.pop()) != c)
                    {
                        countOfEnding.put(c, countOfEnding.get(c) + 1);
                    }
                }
            }
        }

        int count = 0;

        for(Map.Entry e: countOfEnding.entrySet())
        {
            char ending = (char)e.getKey();

            int countForChar = (int)e.getValue();
            switch(ending)
            {
                case ')':
                    count += countForChar * 3;
                    break;
                case ']':
                    count += countForChar * 57;
                    break;
                case '}':
                    count += countForChar * 1197;
                    break;
                case '>':
                    count += countForChar * 25137;
                    break;
            }
        }

        return count;
    }

    public static long problemTwo(List<String> lines, HashMap<Character, Character> pairs)
    {
        ArrayList<Long> results = new ArrayList<>();

        for(String line: lines)
        {
            boolean normalLine = true;
            Stack<Character> openings = new Stack<>();
            for(char c: line.toCharArray())
            {
                //openings
                if(c == '(' || c == '[' || c == '{' || c == '<')
                {
                    openings.push(c);
                }
                else //its a closing
                {
                    if(pairs.get(openings.pop()) != c)
                    {
                        normalLine = false;
                        break;
                    }
                }
            }

            if(!normalLine)
            {
                continue;
            }

            long count = 0;
            while(!openings.isEmpty())
            {
                char closingChar = pairs.get(openings.pop());
                switch(closingChar)
                {
                    case ')':
                        count = count * 5 + 1;
                        break;
                    case ']':
                        count = count * 5 + 2;
                        break;
                    case '}':
                        count = count * 5 + 3;
                        break;
                    case '>':
                        count = count * 5 + 4;
                        break;
                }
            }

            results.add(count);
        }

        Collections.sort(results);
        return results.get(results.size() / 2);
    }
}
