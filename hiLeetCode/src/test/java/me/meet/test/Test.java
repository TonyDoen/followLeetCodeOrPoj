package me.meet.test;

public class Test {
    // 123+456
    // 321 654
    // cms
    // G1
    // ZGC
    // memcallc

    // DAG / HA
    // koblier master/worker
    // mysql  task/DAG
    //

    public static String compute(String i0, String i1) {
        // lvs
        // ip
        // spark
        // kafka
        //

        // default i0.length > i1.length
        StringBuilder sr = new StringBuilder();
        int d = 0; //进位
        for (int i = 0; i < i1.length(); i++) {
            int cur = (int) (i0.charAt(i)-'0') + (int) (i1.charAt(i)-'0') + d;
            if (cur > 9) {
                d = 1;
            } else {
                d = 0;
            }
            sr.insert(0, (cur%10));
        }
        for (int i = i1.length(); i < i0.length(); i++) {
            int cur = i0.charAt(i)-'0' + d;
            if (cur > 9) {
                d = 1;
            } else {
                d = 0;
            }
            sr.insert(0, cur%10);
        }
        return sr.toString();
    }

//    public static String precompute(String i0, String i1) {
//        if (i0.length() > i1.length()) {
//            return compute(i0.revese(), i1.reverse());
//        } else {
//            reutrn compute(i1.reverse(), i0.reverse())
//        }
//
//        // Spark shuffle(sort base shuffle ) bypass C
//        // text 1,2,3
//        // distinct  map shuffle reduce
//        // broadcast
//        // kafka
//        // Spark Streaming
//        //
//    }

    /**
     * A table (userId,loginDate)
     *                 20210101
     * 每个用户最长连续登录天数
     *
     * select userId, udf(row partion by userId) as cnt from A
     *
     *
     * array[] int sorted -10->99 abs
     * logN
     *
     *
     * map detla
     * 4:1
     *
     * 24
     *
     *
     *
     */
    //   l       m           r
    // [-10, -7, 3, 5, 10, 11]
    //
    public static int findAbsMin(int[] arr) {
        // arr: sorted
        int rs = Integer.MAX_VALUE;
        int left = 0, right = arr.length-1;
        for (; left < right;) {
            int mid = left+(right-left)/2;
            if (arr[mid] > 0) {
                right = mid;
            } else {
                left = mid;
            }


            if (Math.abs(arr[left]) > Math.abs(arr[right])) {
                left = mid - 1;
            } else if (Math.abs(arr[left]) < Math.abs(arr[right])) {
                right = mid + 1;
            } else {
                // abs(arr[left]) == abs(arr[right])
                left++;
                right--;

            }
            if (Math.abs(arr[mid]) < Math.abs(rs)) {
                rs = arr[mid];
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{-10, -7, 3, 5, 10, 11};
        int rs = findAbsMin(arr);
        System.out.println(rs);

//        String i0 = "123";
//        String i1 = "456";
//        String i0 = "321";
//        String i1 = "654";
//        String rs = compute(i0, i1);
//        System.out.println(rs);
    }
}
