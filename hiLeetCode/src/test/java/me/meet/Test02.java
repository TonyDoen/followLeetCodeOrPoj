//package me.meet;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//public class Test02 {
//    // "a"->"1", "b"->"2", "c"->"3", …, "z"->"26"
//    // Input: digit string ("1212")
//    // Output: letter strings ("abab", "lab", "abl", "aub", …)
//    private static final Map<String, String> mp = new HashMap<>();
//
//    static {
//        // mp.put("1", "a");
//        for (int i = 1; i < 27; i++) {
//            mp.put(String.valueOf(i), String.valueOf((char) ('a' + (i - 1))));
//        }
//    }
//
//    public static String mayStr(String numStr) {
//        // base case
//        if (null == numStr) {
//            return null;
//        }
//
//        if (numStr.length() <= 2) {
//            return mp.get(numStr);
//        }
//
//        // case 1
//        String s1Pre = mp.get(numStr.substring(0, 1));
//        String s1Suf = mayStr(numStr.substring(1));
//        if (null != s1Suf) {
//        }
//
//
//
//
//
//        // case 2
//        String s2Pre = mp.get(numStr.substring(0, 2));
//        if (null != s2Pre) {
//            String s2 = s2Pre + mayStr(numStr.substring(2));
//        }
//
//    }
//
//    public static List<String> listSrs(String numStr) {
//        // eg:
//        // numStr: "1212"
//        //          1 [212]
//        //          12 [12]
//        //          121 x
//        // base case
//        if (null == numStr) {
//            return null;
//        }
//
//        final int length = numStr.length();
//        final List<String> retPiece = new LinkedList<>();
//        if (1 == length) {
//            retPiece.add(mp.get(numStr));
//            return retPiece;
//        }
//        if (2 == length) {
//            String secondVal;
//            if (null != (secondVal = mp.get(numStr))) {
//                retPiece.add(secondVal);
//            }
//            return retPiece;
//        }
//
//        HashSet<String> rs = new HashSet<>();
//        // case 1
//        String sV0 = mp.get(numStr.substring(0, 1));
//        List<String> splitOtherList = listSrs(numStr.substring(1));
//        for (String s : splitOtherList) {
//            rs.add(sV0 + s);
//        }
//        // case 2
//        String sV1 = mp.get(numStr.substring(0, 2));
//        if (null != sV1) {
//            splitOtherList = listSrs(numStr.substring(2));
//            for (String s : splitOtherList) {
//                rs.add(sV1 + s);
//            }
//        }
//        return new LinkedList<>(rs);
//    }
//
//    public static void main(String[] args) {
//        String s = "1212";
//        List<String> rs = listSrs(s);
//        System.out.println(rs);
//    }
//
////    public static List<String> listStrs(String numStr) {
////        // eg:
////        // numStr: "1212"
////        //          1 [212]
////        //          12 [12]
////        //          121 x
////
////        // base case
////        if (null == numStr) {
////            return null;
////        }
////
////        final int length = numStr.length();
////        final List<String> retPiece = new LinkedList<>();
////        if (1 == length) {
////            retPiece.add(mp.get(numStr));
////            return retPiece;
////        }
////        if (2 == length) {
////            String firstVal = mp.get(String.valueOf(numStr.charAt(0))) + mp.get(String.valueOf(numStr.charAt(1)));
////            retPiece.add(firstVal);
////
////            String secondVal;
////            if (null != (secondVal = mp.get(numStr))) {
////                retPiece.add(secondVal);
////            }
////            return retPiece;
////        }
////
////        HashSet<String> rs = new HashSet<>();
////        // first situation
////        String split0 = numStr.substring(0, 1);
////        String sV0 = mp.get(split0);
////        String splitOther = numStr.substring(1);
////        List<String> splitOtherList = listStrs(splitOther);
////        for (String s : splitOtherList) {
////            String v = sV0 + s;
////            rs.add(v);
////        }
////
////        // second situation
////        String split1 = numStr.substring(0, 2);
////        String sV1 = mp.get(split1);
////        if (null != sV1) {
////            splitOther = numStr.substring(2);
////            splitOtherList = listStrs(splitOther);
////            for (String s : splitOtherList) {
////                String v = sV1 + s;
////                rs.add(v);
////            }
////        }
////        return new LinkedList<>(rs);
////    }
//
////    private static final List<List<String>> rs = new LinkedList<>();
////
////    public static List<List<String>> listAllStrs(String numStr) {
////        // numStr: "1212"
////        //          1 [212]
////        //          12 [12]
////        //          121 x
////
////        // base case
////        if (null == numStr) {
////            return Collections.emptyList();
////        }
////        if (1 == numStr.length()) {
////            return Arrays.asList(mp.get(numStr));
////        }
////        if (2 == numStr.length()) {
////            LinkedList<String> ret = new LinkedList<>();
////            String val = mp.get(numStr);
////            if (null != val) {
////                ret.add(val);
////            }
////            ret.add(numStr.substring(0, 1));
////            ret.add(numStr.substring(1, 1));
////            return ret;
////        }
////
////        // numStr.substring(0) + numStr.substring(1)
////        // numStr.substring(2) + numStr.substring(2)
////        List<String> l0 = new LinkedList<>();
////        l0.add(mp.get(numStr.substring(0, 1)));
////        l0.addAll(listAllStrs(numStr.substring(1)));
////
////        List<String> l1 = new LinkedList<>();
////        l1.add(mp.get(numStr.substring(0, 2)));
////        l1.addAll(listAllStrs(numStr.substring(2)));
////
////        rs.add(l0);
////        rs.add(l1);
////        return rs;
////    }
//
//}
