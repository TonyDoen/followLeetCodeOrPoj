package me.meet.test;

import java.util.*;

public class Test1 {

    public static List<List<Integer>> find3Sum0(int[] arr) {
        if (null == arr || arr.length < 1) {
            return null;
        }

        Arrays.sort(arr);
        int length = arr.length;
        Map<Integer, Integer> posMp = new HashMap<>();
        for (int i = 0; i < length; i++) {
            posMp.put(arr[i], i);
        }

        Set<String> set = new HashSet<>();
        List<List<Integer>> rs = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int p2Sum = arr[i] + arr[j];
                if (p2Sum > 0) {
                    return rs;
                }
                int remain = -p2Sum;
                Integer idx = posMp.get(remain);
                if (null == idx) {
                    continue;
                }
                if (idx < i || idx < j) {
                    continue;
                }
                String reduKey = arr[i] + "," + arr[j] + "," + arr[remain];
                if (!set.contains(reduKey)) {
                    set.add(reduKey);

                    ArrayList<Integer> tp = new ArrayList<>();
                    tp.add(arr[i]);
                    tp.add(arr[j]);
                    tp.add(arr[remain]);
                    rs.add(tp);
                }
            }
        }
        return rs;
    }


    // string[] s1=a1,a22,a3,a44,a5,...
    // string[] s2=b1,b2,b33,b4,b5,...
    // string[] s3=c11,c2,c33,c44,c5,...
    // ...
    // string[] sn
    //
    //
    // 第十一行 string s=c11b33a1c33a5b4c5
    //                 c11: s3
    //                 b33: s2
    //                 a1:  s1
    //                 c33: s3
    //                 a5:  s1
    //                 b4:  s2
    //                 c5:  s3
    //
    // string sr = ?
    // 1. s1...sn 组成
    // 2. 字符串选取顺序是一样的
    //
    //
    // tip:
    //


    public static boolean compute(String[][] src, String pattern, String checkStr) {
        Map<String, Integer> mp = new HashMap<>();
        // 构造位置 map
        int row = 0;
        for (String[] sArr: src) {
            // 同属一列
            for (String s: sArr) {
                mp.put(s, row);
            }
            row++;
        }

        StringBuilder remain = new StringBuilder();
        StringBuilder checkOrder = helpGetOrder(checkStr, remain, mp);
        if (0 != remain.length()) {
            return false;
        }
        StringBuilder patternOrder = helpGetOrder(pattern, remain, mp);

        return checkOrder.toString().equals(patternOrder.toString());
    }

    private static StringBuilder helpGetOrder(String checkStr, StringBuilder remain, Map<String, Integer> mp) {
        StringBuilder checkOrder = new StringBuilder();
        for (int i = 0, length = checkStr.length(); i < length; i++) {
            remain.append(checkStr.charAt(i));
            Integer r = mp.get(remain.toString());
            if (null == r) {
                continue;
            }
            checkOrder.append(r).append(",");
            remain = new StringBuilder();
        }
        return checkOrder;
    }

    public static void testCompute() {
        Integer a = 0;
        Integer b = 0;
        System.out.print(a.equals(b));
        System.out.print(a == b);

        String s0 = "abc";
        String s1 = "abc";
        System.out.print(s0.equals(s1));
        System.out.print(s0 == s1);
    }

    public static void main(String[] args) {
        System.out.println("hello");

        int[] arr = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> rs = find3Sum0(arr);
        System.out.println(rs);

        testCompute();
    }
}
