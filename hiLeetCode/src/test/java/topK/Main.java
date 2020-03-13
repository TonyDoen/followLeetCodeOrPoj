package topK;

public class Main {
    static class LinkNode {
        Integer val;
        LinkNode next;
        LinkNode() {}
        LinkNode(Integer val, LinkNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    static LinkNode combine(LinkNode l1, LinkNode l2) {
        if (null == l1 || null == l2) {
            return null == l2?l1:l2;
        }
        LinkNode res = new LinkNode();
        LinkNode tmp = res;
        
        LinkNode t1 = l1, t2 = l2; 
        for (; null != t1 && null != t2;) {
            if (t1.val > t2.val) {
                tmp.next = t2;
                t2 = t2.next;
            } else {
                tmp.next = t1;
                t1 = t1.next;
            }
            tmp = tmp.next;
        }
        if (null != t1) {
            tmp.next = t1;
        }
        if (null != t2) {
            tmp.next = t2;
        }
        return res;
    }
    
    static void testCombine() {
        LinkNode _7 = new LinkNode(7, null);
        LinkNode _4 = new LinkNode(4, _7);
        LinkNode _1 = new LinkNode(1, _4);
        
        LinkNode _8 = new LinkNode(8, null);
        LinkNode _5 = new LinkNode(5, _8);
        LinkNode _2 = new LinkNode(2, _5);
        
        LinkNode res = combine(_1, _2);
        for (; null != res; ) {
            System.out.println(res.val);
            res = res.next;
        }
    }
    
    
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        testCombine();
        System.out.println("Hello World!");
    }
}