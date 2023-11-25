import java.util.ArrayList;
import java.util.List;

public class SmallestWindowSubSum {

    // get smallest sub-array which sum is greater than x.
    public static List<List<Integer>> other(List<Integer> input, int x) {

        var n = input.size();

        var sums = new ArrayList<Integer>();
        var windowSizes = new ArrayList<Integer>();

        var i = 0;
        var windowSize = 0;
        var lastIdx = 0;
        var initSum = 0;

        while (initSum < x && lastIdx < n - 1) {
            windowSize++;
            lastIdx = i + (windowSize - 1);
            initSum += input.get(lastIdx);
        }
        if (initSum < x) {
            return null;
        }
        sums.add(initSum);
        windowSizes.add(windowSize);

        i = 1;
        lastIdx++;

        while (i < n) {
            var previousInitValue = input.get(i - 1);
            Integer currLastValue;
            Integer newValue;
            if (lastIdx <= n - 1) {
                currLastValue = input.get(lastIdx);
                newValue = sums.get(i - 1) - previousInitValue + currLastValue;
            } else {
                lastIdx = n - 1;
                currLastValue = input.get(lastIdx);
                windowSize--;
                newValue = sums.get(i - 1) - previousInitValue;
            }

            while (windowSize > 1 && newValue - currLastValue >= x) {
                newValue -= currLastValue;
                windowSize--;
                lastIdx = i + (windowSize - 1);
                currLastValue = input.get(lastIdx);
            }
            while (lastIdx < n - 1 && newValue < x) {
                windowSize++;
                lastIdx = i + (windowSize - 1);
                currLastValue = input.get(lastIdx);
                newValue += currLastValue;
            }
            if (newValue < x) {
                break;
            }

            sums.add(newValue);
            windowSizes.add(windowSize);
            i++;
            lastIdx++;
        }

        return List.of(sums, windowSizes);
    }

    public static void main(String[] args) {

//        var input = List.of(1, 2, 3, 4, 5, 7);
        var input = List.of(1, 2, 3, 4, 3, 6, 0, 1, 5, 1, 6, 7, 7);
        var x = 7;

        System.out.println(other(input, x));
    }

}