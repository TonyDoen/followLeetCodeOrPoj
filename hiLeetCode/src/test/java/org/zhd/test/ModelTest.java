package org.zhd.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class ModelTest {
    // src = "2[dd3[cc]]"
    public static String solve(String src) {

        int len = src.length();
        char[] pieces = src.toCharArray();
        LinkedList<Integer> num = new LinkedList<Integer>();
        List<Character> chars = new ArrayList<Character>();
        for (int i = 0; i < len; i++) {
            char c = pieces[i];
            if ((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z')|| (c == '[' || c == ']') ) {
                chars.add(0,c);
            } else {
                num.add(0,Integer.valueOf(String.valueOf(c)));
            }
        }

        StringBuilder sbr = new StringBuilder();
        for (Character c : chars) {
            if (c == ']') {
                continue;
            }
            if (c == '[') {
                Integer n = num.poll();
                String tmp = sbr.toString();

                for (int i = 0; i < n-1; i++) {
                    sbr.append(tmp);
                }
                continue;
            }
            sbr.insert(0,c);
        }

        System.out.println(sbr.toString());
        return sbr.toString();
    }



    public static void main(String[] args) {

        solve("2[dd3[cc]]");
    }
}
