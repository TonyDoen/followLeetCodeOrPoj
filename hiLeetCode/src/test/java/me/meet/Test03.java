package me.meet;

import java.util.*;

public class Test03 {
    static class Alarm {
        long id;
        int weekday;
        int hour;
        int minute;
        int typeId;

//        private Alarm() {}
        Alarm(long id, int typeId, int weekday, int hour
            , int minute) {
            if (id < 1) {
                throw new IllegalArgumentException("id");
            }
            if (typeId < 0 || typeId > 2) {
                throw new IllegalArgumentException("typeId");
            }
            if (weekday < 1 || weekday > 7) {
                throw new IllegalArgumentException("weekday");
            }
            // 0 < hour < 24 && 0 < startminute < 60 && 0 < endminute < 60
            this.id = id;
            this.typeId = typeId;
            this.weekday = weekday;
            this.hour = hour;
            this.minute = minute;
        }
    }

    static class AlarmSystem {
        // weekday,hour,startminute,endminute
        private final PriorityQueue<Alarm> queue
            = new PriorityQueue<>((a0, a1)->{
            if (a0.weekday != a1.weekday) {
                return a0.weekday - a1.weekday;
            }
            if (a0.hour != a1.hour) {
                return a0.hour - a1.hour;
            }
            if (a0.minute != a1.minute) {
                return a0.minute - a1.minute;
            }
            if (a0.typeId != a1.typeId) {
                return a0.typeId - a1.typeId;
            }
            return (int)(a0.id - a1.id);
        });
        private final Map<Long, Alarm> mp
            = new HashMap<>();

        public boolean addAlarm(long id, int typeId
            , int weekday, int hour, int minute) {
            Alarm v = mp.get(id);
            if (null != v) {
                return false;
            }
            v = new Alarm(id, typeId, weekday
                , hour, minute);
            mp.put(id, v);
            return queue.add(v);
        }

        public boolean deleteAlarm(long id) {
            Alarm v = mp.get(id);
            if (null == v) {
                return false;
            }
            return queue.remove(v);
        }

        public List<Alarm> queryAlarm(int weekday
            , int hour, int startminute, int endminute) {
            List<Alarm> rs = new ArrayList<>();
            for (Alarm a : queue) {
                if (weekday != a.weekday
                    || hour != a.hour) {
                    continue;
                }
                if (startminute <= a.minute
                    && endminute >= a.minute) {
                    rs.add(a);
                }
            }
            return rs;
        }
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        char r = 'A' | ' ';
        a: {
            System.out.println("a");
        }

        System.out.println(r);
    }
}
