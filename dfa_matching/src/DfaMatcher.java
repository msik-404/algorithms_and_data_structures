import java.util.ArrayList;
import java.util.List;

public class DfaMatcher {

    private static final char minChar = ' ';
    private static final char maxChar = '~';

    private static int getAlphabetSize() {
        return (int) maxChar - (int) minChar + 1;
    }

    private static int getOffsetChar(String text, int idx) {
        return text.charAt(idx) - minChar;
    }

    /**
     * This is implementation of KMP for dfa construction algorithm. It works in O(n*ALPHABET).
     *
     * @param pattern String for which dfa will be constructed.
     * @return Dfa in int matrix format.
     */
    public static int[][] constructDfa(String pattern) {

        int colAmount = pattern.length() + 1;
        int[][] dfa = new int[getAlphabetSize()][colAmount];

        for (int i = 0; i < pattern.length(); i++) {
            dfa[getOffsetChar(pattern, i)][i] = i + 1;
        }

        int x = 0;
        for (int i = 1; i < colAmount; i++) {
            for (int j = 0; j < dfa.length; j++) {
                if (dfa[j][x] > dfa[j][i]) {
                    dfa[j][i] = dfa[j][x];
                }
            }
            if (i < pattern.length()) {
                x = dfa[getOffsetChar(pattern, i)][x];
            }
        }

        return dfa;
    }

    /**
     * Uses dfa for finding all pattern occurrences inside text.
     * In combination with construction it works in O(n*ALPHABET + n). Just searching works in O(n).
     *
     * @param text String in which we are looking for patterns.
     * @param pattern String which we are searching inside text.
     * @return List of starting indices of patterns.
     */
    public static List<Integer> search(String text, String pattern) {

        int[][] dfa = constructDfa(pattern);
        List<Integer> output = new ArrayList<>();

        int state = 0;
        for (int i = 0; i < text.length(); i++) {
            state = dfa[getOffsetChar(text, i)][state];
            if (state == pattern.length()) {
                output.add(i - state + 1);
            }
        }

        return output;
    }

    public static void main(String[] args) {

        var pattern = "ALALA";
        var text = "ALA MA KOTA KOT MA ALALALALA";
        System.out.println(search(text, pattern));
    }

}