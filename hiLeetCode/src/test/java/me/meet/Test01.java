package me.meet;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test01 {

    // 大根堆
    private static void downAdjust(int[] arr, int k) {
        // arr = int[k+1];
        // parent = i, left = 2i+1, right = 2i+2, i < k
        for (int i = 0; 2 * i + 2 < k; i++) {
            int l = 2 * i + 1, r = 2 * i + 2;
            if (arr[i] < arr[l]) {
                // swap
                swap(arr, i, l);
            }
            if (arr[i] < arr[r]) {
                // swap
                swap(arr, i, r);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * ------------>x
     *  1 2 3 4
     *  2 3 4 5
     *  4 5 6 7
     *  5 6 7 8
     *
     */
    public static int findInMatrix(int[][] matrix, int k) {
        int[] arr = new int[k];
        int row = matrix.length, col = matrix[0].length;
        int count = 0, i = 0, j = 0;
        for (; i < row; i++) {
            for (; j < col; j++) {
                if (count >= k) {
                    break;
                }
                int cur = matrix[i][j];
                arr[count++] = cur;
            }
            if (count >= k) {
                break;
            }
        }
        downAdjust(arr, k);
        for (; i < row; i++) {
            for (; j < col; j++) {
                int cur = matrix[i][j];
                if (cur < arr[0]) {
                    arr[0] = cur;
                    downAdjust(arr, k);
                }
            }
            j = 0;
        }
        return arr[0];
    }

    private static void testFindK() {
        int[][] arr = new int[][]{
            {1,2,3,4},
            {2,3,4,5},
            {3,4,5,6},
            {4,5,6,7}};
        int k = 3;
        int rs = findInMatrix(arr, k);
        System.out.println(rs);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
//        testFindK();
    }
}
