package org.zhd.test;

public class KMP {

    static void kmp(String source, String pattern) {
        int[] N = getN(pattern);
        int res = 0;
        int sourceLength = source.length();
        int patternLength = pattern.length();
        for (int i = 0; i <= (sourceLength - patternLength); ) {
            res++;
            String str = source.substring(i, i + patternLength);//要比较的字符串
            System.out.println(str);
            int count = getNext(pattern, str, N);
            System.out.println("移动" + count + "步");
            if (count == 0) {
                System.out.println("KMP：匹配成功");
                break;
            }
            i = i + count;
        }
        System.out.println("KMP：一共匹配" + res + "次数");
    }

    private static int getNext(String pattern, String str, int[] N) {
        int n = pattern.length();
        char[] v1 = str.toCharArray();
        char[] v2 = pattern.toCharArray();
        int x = 0;
        while (n-- != 0) {
            if (v1[x] != v2[x]) {
                if (x == 0) {
                    return 1;//如果第一个不相同，移动1步
                }
                return x - N[x - 1];//x:第一次出现不同的索引的位置，即j
            }
            x++;
        }
        return 0;
    }

    private static int[] getN(String pattern) {
        char[] pat = pattern.toCharArray();
        int j = pattern.length() - 1;
        int[] N = new int[j + 1];
        for (int i = j; i >= 2; i--) {
            N[i - 1] = getK(i, pat);
        }
        for (int a : N)
            System.out.println(a);
        return N;
    }

    private static int getK(int j, char[] pat) {
        int x = j - 2;
        int y = 1;
        while (x >= 0 && compare(pat, 0, x, y, j - 1)) {
            x--;
            y++;
        }
        return x + 1;
    }

    private static boolean compare(char[] pat, int b1, int e1, int b2, int e2) {
        int n = e1 - b1 + 1;
        while (n-- != 0) {
            if (pat[b1] != pat[b2]) {
                return true;
            }
            b1++;
            b2++;
        }
        return false;
    }

    public static void main(String[] args) {
        String source = "abcdefjhijklmnijkl";
        String pattern = "ijkl";
        kmp(source, pattern);
    }
}

