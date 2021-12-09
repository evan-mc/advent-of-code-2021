
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(".\\src\\input.txt"));
        List<String[]> arr = br.lines().map(line -> line.split(" \\| ")).collect(Collectors.toList());

        ArrayList<Signal> signals = Signal.buildSignals(arr);

        System.out.println(problemOne(signals));
        System.out.println(problemTwo(signals));
    }

    public static int problemOne(ArrayList<Signal> signals)
    {
        //make hashset of unique digit counts
        HashSet<Integer> unique = new HashSet<>();
        unique.add(2);
        unique.add(3);
        unique.add(4);
        unique.add(7);

        int countOfUnique = 0;
        for(Signal sig: signals)
        {
            for(String out: sig.getOutput())
            {
                if(unique.contains(out.length()))
                {
                    ++countOfUnique;
                }
            }
        }

        return countOfUnique;
    }

    public static int problemTwo(ArrayList<Signal> signals)
    {

        for(Signal sig: signals)
        {
            /*
                unique digit numbers
                1 -> 2 digits
                4 -> 4 digits
                7 -> 3 digits
                8 -> 7 digits
            */

            String[] pattern = sig.getPattern();
            char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
            HashSet<Character> charactersUsed = sig.getCharactersUsed();

            //one
            String one = Arrays.stream(pattern).filter(str -> str.length() == 2).findFirst().get();
            sig.topRight = one.charAt(0);
            sig.bottomRight = one.charAt(1);
            charactersUsed.add(sig.topRight);
            charactersUsed.add(sig.bottomRight);

            /*
                seven. seven shares 2/3 values with one, so simply get the one character they dont share,
                and set that as the top position.
             */
            String seven = Arrays.stream(pattern).filter(str -> str.length() == 3).findFirst().get();
            for(char c: seven.toCharArray())
            {
                if(!charactersUsed.contains(c))
                {
                    sig.top = c;
                    charactersUsed.add(c);
                    break;
                }
            }

            /*
                zero, four, six, nine. with the exception of four, these 3 numbers all have 6 digits.
                they also share the same characters except for 1 different character per number. one
                of those characters will already be apart of the value one and also four, so we can exclude that.
                between the other two, one of those characters is not in four, so we can exclude that one as well.
                this leaves us with the final character, which is the middle character. this also allows us to set
                the top left character, since fours input only has two unknown positions for characters, and we just
                set middles character.
             */
            String four = Arrays.stream(pattern).filter(str -> str.length() == 4).findFirst().get();

            String[] zeroSixNine = Arrays.stream(pattern).filter(str -> str.length() == 6).toArray(String[]::new);

            sig.middle = Arrays.stream(zeroSixNine).map(str -> {
                for(char c: characters)
                {
                    //look for missing value
                    if(!str.contains(c + ""))
                    {
                        return c;
                    }
                }

                //shouldnt ever hit
                return 'z';

            }).filter(c -> !charactersUsed.contains(c)).filter(c -> four.contains(c + "")).findFirst().get();

            /*
                determine whether topRight and bottomRight are actually correct.
                we dont know the actual order of topright and bottomright from one yet, so it can be topright -> a bottomRight -> b
                or vice versa. the one value we HAVE seen is the REAL top right
             */
            char actualTopRight = Arrays.stream(zeroSixNine).map(str -> {
                for(char c: characters)
                {
                    //look for missing value
                    if(!str.contains(c + ""))
                    {
                        return c;
                    }
                }

                //shouldnt ever hit
                return 'z';

            }).filter(charactersUsed::contains).findFirst().get();

            charactersUsed.add(sig.middle);


            if(actualTopRight != sig.topRight)
            {
                sig.bottomRight = sig.topRight;
                sig.topRight = actualTopRight;
            }

            for(char c: four.toCharArray())
            {
                if(!charactersUsed.contains(c))
                {
                    sig.topLeft = c;
                    charactersUsed.add(c);
                }
            }

            //9 includes middle and top right
            sig.bottom = Arrays.stream(zeroSixNine).filter(str -> str.contains(sig.middle + "") && str.contains(sig.topRight + ""))
                    .map(str -> {
                        for(char c: str.toCharArray())
                        {
                            if(!charactersUsed.contains(c))
                            {
                                return c;
                            }
                        }

                        return 'z';
                    }).findFirst().get();

            charactersUsed.add(sig.bottom);

            for(char c: characters)
            {
                if(!charactersUsed.contains(c))
                {
                    sig.bottomLeft = c;
                    charactersUsed.add(c);
                }
            }

            //add set of number characters to lookup
            ArrayList<Set<Character>> numberCharacters = sig.getNumberCharacters();

            //0
            numberCharacters.add(Set.of(sig.top, sig.topLeft, sig.topRight, sig.bottomLeft, sig.bottomRight, sig.bottom));

            //1
            numberCharacters.add(Set.of(sig.topRight, sig.bottomRight));

            //2
            numberCharacters.add(Set.of(sig.top, sig.topRight, sig.middle, sig.bottomLeft, sig.bottom));

            //3
            numberCharacters.add(Set.of(sig.top, sig.topRight, sig.middle, sig.bottomRight, sig.bottom));

            //4
            numberCharacters.add(Set.of(sig.topLeft, sig.topRight, sig.bottomRight, sig.middle));

            //5
            numberCharacters.add(Set.of(sig.top, sig.topLeft, sig.middle, sig.bottomRight, sig.bottom));

            //6
            numberCharacters.add(Set.of(sig.top, sig.topLeft, sig.middle, sig.bottomLeft, sig.bottomRight, sig.bottom));

            //7
            numberCharacters.add(Set.of(sig.top, sig.topRight, sig.bottomRight));

            //8
            numberCharacters.add(Set.of(sig.top, sig.topLeft, sig.topRight, sig.middle, sig.bottomLeft, sig.bottomRight, sig.bottom));

            //9
            numberCharacters.add(Set.of(sig.top, sig.topLeft, sig.topRight, sig.middle, sig.bottomRight, sig.bottom));

        }
        ArrayList<Integer> numsToAdd = new ArrayList<>();
        for(Signal sig: signals)
        {
            int addDigits = 0;
            for(String str: sig.getOutput())
            {
                ArrayList<Set<Character>> numberCharacters = sig.getNumberCharacters();
                for(int i = 0, numbersSize = numberCharacters.size(); i < numbersSize; ++i)
                {
                    if(str.length() == numberCharacters.get(i).size())
                    {
                        boolean isSame = true;

                        for(char c: str.toCharArray())
                        {
                            if(!numberCharacters.get(i).contains(c))
                                isSame = false;
                        }

                        if(isSame)
                        {
                            addDigits = addDigits * 10 + (i);
                            break;
                        }
                    }

                }
            }

            numsToAdd.add(addDigits);
        }

        int count = 0;
        for(int num: numsToAdd)
        {
            count += num;
        }

        return count;
    }
}
