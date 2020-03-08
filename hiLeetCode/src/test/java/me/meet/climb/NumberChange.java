package me.meet.climb;

public class NumberChange {


    public static String numberToChinese(int num) {

        String[] chineseSectionPosition = {"", "万", "亿", "万亿"}; //中文数字节权位

        if (num == 0) {
            return "零";
        }
        int sectionPosition = 0; //节权位标识
        String endChineseNum = new String(); //最终转换完成的中文字符串
        String sectionChineseNum = new String(); //每个小节转换的字符串
        while (num > 0) { //将阿拉伯数字从右往左每隔4位分成一个小节，在分别对每个小节进行转换处理，最后加上该小节对应的节权
            int section = num % 10000; //获取最后一个小节（低4位数：千百十个）
            sectionChineseNum = eachSection(section); //将当前小节转换为中文
            if (section != 0) { //当前小节不为0时，添加节权
                sectionChineseNum = sectionChineseNum + chineseSectionPosition[sectionPosition];
            }
            num = num / 10000; //去掉已经转换的末尾4位数
            endChineseNum = sectionChineseNum + endChineseNum;
            sectionPosition++; //节权位增加1
        }
        if ('零' == endChineseNum.charAt(0)) {
            endChineseNum = endChineseNum.substring(1);
        }
        return endChineseNum;
    }

    public static String eachSection(int num) { //重载方法
        String[] chineseNumber = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"}; //中文数字位
        String[] chinesePosition = {"", "十", "百", "千"}; //中文数字权位

        String chineseNum = new String(); //转换的中文数字
        boolean zero = true; //小节内部制零判断，每个小节内部只能出现一个中文“零”
        for (int i = 0; i < 4; i++) { //每个小节中只有4位数
            int end = num % 10; //获取末位值
            //判断该数字是否为0。若不是0就直接转换并加上权位，若是0，继续判断是否已经出现过中文数字“零”
            if (end == 0) { //该数字是0
                if (!zero) { //上一位数不为0，执行补0
                    zero = true;
                    chineseNum = chineseNumber[0] + chineseNum;
                }
            } else { //该数字不为0，直接转换并加上权位
                zero = false;
                chineseNum = chineseNumber[end] + chinesePosition[i] + chineseNum; //数字+权位
            }
            num = num / 10;
        }
        return chineseNum;
    }

    public static void main(String[] args) {
        String res = numberToChinese(1999999999);
        System.out.println(res);
    }
}
