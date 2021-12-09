import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Signal
{
    private final String[] pattern;
    private final String[] output;
    private final HashSet<Character> charactersUsed;

    //keeps track of what set of characters each number uses
    private ArrayList<Set<Character>> numberCharacters;

    char top;
    char bottom;
    char middle;
    char topLeft;
    char topRight;
    char bottomLeft;
    char bottomRight;

    Signal(String[] pattern, String[] output)
    {
        this.pattern = pattern;
        this.output = output;
        charactersUsed = new HashSet<>();
        numberCharacters = new ArrayList<>();
    }

    public String[] getPattern()
    {
        return pattern;
    }

    public String[] getOutput()
    {
        return output;
    }

    public HashSet<Character> getCharactersUsed()
    {
        return charactersUsed;
    }

    public ArrayList<Set<Character>> getNumberCharacters()
    {
        return numberCharacters;
    }

    public static ArrayList<Signal> buildSignals(List<String[]> input)
    {
        ArrayList<Signal> signals = new ArrayList<>(input.size());

        for(String[] strArray: input)
        {
            String[] pattern = strArray[0].split(" ");
            String[] output = strArray[1].split(" ");

            signals.add(new Signal(pattern, output));
        }

        return signals;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder().append(" ").append(top).append(top).append(top).append(top).append('\n')
                .append(topLeft).append(" ").append(" ").append(" ").append(" ").append(topRight).append('\n')
                .append(topLeft).append(" ").append(" ").append(" ").append(" ").append(topRight).append('\n')
                .append(" ").append(middle).append(middle).append(middle).append(middle).append('\n')
                .append(bottomLeft).append(" ").append(" ").append(" ").append(" ").append(bottomRight).append('\n')
                .append(bottomLeft).append(" ").append(" ").append(" ").append(" ").append(bottomRight).append('\n')
                .append(" ").append(bottom).append(bottom).append(bottom).append(bottom).append('\n');

        return sb.toString();
    }
}
