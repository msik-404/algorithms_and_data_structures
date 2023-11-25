import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SubSum {

    public static List<Integer> v1(List<Integer> input, int k) {

        var n = input.size();

        var subArraysAmount = n - k + 1;
        var outputs = new int[subArraysAmount];

        var lastSubArrayIdx = n - k;

        for (int i = 0; i < n; i++) {
            var f = max(0, i - (k - 1));     // index of first sub-array containing i'th element
            var e = min(i, lastSubArrayIdx); // index of last sub array containing i'th element

            for (var j = f; j <= e; j++) {
                outputs[j] += input.get(i);
            }
        }

        return Arrays.stream(outputs).boxed().toList();
    }

    public static List<Integer> v2(List<Integer> input, int k) {

        var n = input.size();

        var subArraysAmount = n - k + 1;
        var outputs = new ArrayList<Integer>(subArraysAmount);

        var lastSubArrayIdx = n - k;

        for (int i = 0; i <= lastSubArrayIdx; i++) {
            var s = 0;
            for (int j = i; j < i + k; j++) {
                s += input.get(j);
            }
            outputs.add(s);
        }

        return outputs;
    }

    public static List<Integer> v3(List<Integer> input, int k) {

        var n = input.size();

        var subArraysAmount = n - k + 1;
        var outputs = new ArrayList<Integer>(subArraysAmount);

        var initSum = 0;
        for (int i = 0; i < k; i++) {
            initSum += input.get(i);
        }
        outputs.add(initSum);

        var lastSubArrayIdx = n - k;

        for (int i = 1; i <= lastSubArrayIdx; i++) {
            var previousInitValue = input.get(i - 1);
            var currLastValue = input.get(i + (k - 1));
            outputs.add(outputs.get(i - 1) - previousInitValue + currLastValue);
        }

        return outputs;

    }

    public static void main(String[] args) {

        var input = List.of(1, 0, 0, 1, 2, 3, 0, 1, 2, 0);
        var k = 3;

        System.out.println(v3(input, k));
    }
}