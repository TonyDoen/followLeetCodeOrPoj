package org.zhd.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {
    class Kstr {
        Double k;
        String v;

    }

    static void sort(List<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (null == o1 || null == o2) {
                    return 0;
                }
                String[] p1 = o1.split(",");
                String[] p2 = o2.split(",");
                Double score1 = Double.parseDouble(p1[2]);
                Double score2 = Double.parseDouble(p2[2]);
                return score1 > score2 ? -1 : 1;
            }
        });
    }

    static List<String> paginate(int resultsPerPage, List<String> results) {
        if (resultsPerPage < 0 || null == results || results.isEmpty()) {
            return results;
        }
        Map<String, List<String>> map = new HashMap<>();
        LinkedList<String> remain = new LinkedList<>();
        for (String str : results) {
            if (null == str) {
                continue;
            }
            String[] piece = str.split(",");
            if (piece.length < 4) {
                continue;
            }
            remain.add(str);
            String hostId = piece[0];

            List<String> list = map.computeIfAbsent(hostId, k -> new LinkedList<>());
            list.add(str);
            sort(list);
        }


        List<String> res = new ArrayList<>();
//        while (!results.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                List<String> value = entry.getValue();
                if (null == value || value.isEmpty()) {
                    continue;
                }
                i++;
                String _0 = value.get(0);
                value.remove(_0);
                remain.remove(_0);
                res.add(_0);

                if (i >= resultsPerPage) {
                    res.add("");
                    i = 0;
                    break;
                } else {

                    Iterator<String> it = remain.iterator();
                    while (it.hasNext()) {
                        i++;
                        String bak = it.next();
                        value.remove(bak);
                        it.remove();
                        res.add(bak);
                        if (i >= resultsPerPage || remain.isEmpty()) {
                            res.add("");
                            i = 0;
                            break;
                        }
                    }

                }
//            }
        }

        return res;
    }


    public static void main(String[] args) {
        String[] str = new String[]{
                "1,28,300.6,San Francisco",
                "4,5,209.1,San Francisco",
                "20,7,203.4,Oakland",
                "6,8,202.9,San Francisco",
                "6,10,199.8,San Francisco",
                "1,16,190.5,San Francisco",
                "6,29,185.3,San Francisco",
                "7,20,180.0,Oakland",
                "6,21,162.2,San Francisco",
                "2,18,161.7,San Jose",
                "2,30,149.8,San Jose",
                "3,76,146.7,San Francisco",
                "2,14,141.8,San Jose"};

        int resultsPerPage = 5;
        List<String> results = Arrays.asList(str);

        List<String> list = paginate(resultsPerPage, results);

        System.out.println(list);
    }
}
