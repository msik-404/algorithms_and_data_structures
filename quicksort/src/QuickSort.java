import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSort {

    public static void quickSort(ArrayList<Integer> data, int l, int r) {

        if (r <= l) {
            return;
        }

        var i = l - 1;
        var pivot = r;

        for (int j = l; j < pivot; j++) {
            if (data.get(j) < data.get(pivot)) {
                i++;
                if (i != j) {
                    Collections.swap(data, i, j);
                }
            }
        }

        Collections.swap(data, i + 1, pivot);
        pivot = i + 1;

        quickSort(data, l, pivot - 1);
        quickSort(data, pivot + 1, r);
    }

    public static void main(String[] args) {

        var data = new ArrayList<>(List.of(-2, 1, 23, 8, - 13, 4));

        quickSort(data, 0, data.size() - 1);

        System.out.println(data);
    }
}