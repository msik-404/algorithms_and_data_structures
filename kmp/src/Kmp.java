import java.util.ArrayList;
import java.util.List;

public class Kmp {

    public static int[] getPiFunction(String w) {

        var pi = new int[w.length()];

        var j = 0;

        for (int i = 1; i < w.length(); i++) {

            // 'valid' means prefix which is not the entire string.
            // When there is no match. Look into already matched part for 'valid' longest prefix-suffix and use it.
            // To use it, use pi function which returns size of longest valid prefix-suffix at position i.
            // We were comparing w[i] to w[j]. So look for prefix-suffix of pi[j-1] (j-1) was already matched.
            while (j > 0 && w.charAt(i) != w.charAt(j)) {
                // set j to this size. By doing so we set j so that it is just after prefix-suffix.
                j = pi[j-1];
                // we repeat this until w[i] = w[j]. If this doesn't happen j will be 0.
            }

            // compare w[i] to w[j] if they are equal we extend prefix-suffix.
            if (w.charAt(i) == w.charAt(j)) {
                j++;
            }

            // fill pi function table value. This value is either one plus size of last prefix-suffix or 0 if there was
            // no match.
            pi[i] = j;
        }

        return pi;
    }

    /**
     *
     * This algorithm is practically the same as getPiFunction, but instead of computing pi, it saves  matches when
     * j becomes equal to w.length(). When this happens, to search further j needs to be set to some value. To maximise
     * performance, we set it to the last element of pi function table. In the next step, procedure will try to find
     * best prefix-suffix to start from. If it fails it will start at j = 0.
     *
     * @param s Input text
     * @param w Input word to be search
     * @return List of start indexes of matches
     */
    public static List<Integer> kmp(String s, String w) {

        var matches = new ArrayList<Integer>();

        var pi = getPiFunction(w);

        var j = 0;

        for (var i = 0; i < s.length(); i++) {

            while (j > 0 && s.charAt(i) != w.charAt(j)) {
                j = pi[j-1];
            }

            if (s.charAt(i) == w.charAt(j)) {
                j++;
                if (j == w.length()) {
                    // start of the match.
                    matches.add(i - (w.length() - 1));
                    j = pi[j-1];
                }
            }
        }

        return matches;
    }

    public static void main(String[] args) {

        var s = "aaabaabaabaaaabaabaaaaacaabbaaabaaaabaabaaaabaaabaaaabaab";
        var w = "aabaaaabaab";

        var out = kmp(s, w);

        for (int i : out) {
            System.out.println(i);
            System.out.println(s.substring(i, i + w.length()));
            System.out.println();
        }
    }

}