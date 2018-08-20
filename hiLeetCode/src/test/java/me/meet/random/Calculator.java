package me.meet.random;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public final class Calculator {
    private Calculator() {
    }

    /**
     * url: https://blog.csdn.net/AnNanDu/article/details/106672955
     *
     * 四则运算计算器实现
     */
    private static final Pattern expr_pattern = Pattern.compile("[0-9\\.+*-/()= ]+");
    public static String compute(String expression) {
        if (null == expression || "".equals(expression.trim())
            || !expr_pattern.matcher(expression).matches()) {
            throw new IllegalArgumentException("error param.");
        }

        LinkedList<Character> opStack = new LinkedList<>();
        LinkedList<BigDecimal> nrStack = new LinkedList<>();
        StringBuilder sr = new StringBuilder();

        for (int i = 0, length = expression.length(); i < length; i++) {
            char c = expression.charAt(i);
            if (' ' == c) {
                continue;
            }
            if (('0' <= c && '9' >= c) || '.' == c) {
                sr.append(c); // number
            } else {
                int srLen;
                if ((srLen = sr.length()) > 0) {
                    nrStack.push(new BigDecimal(sr.toString()));
                    sr.delete(0, srLen);
                }

                if (opStack.isEmpty()) {
                    opStack.push(c); // op
                } else {
                    if ('(' == c) {
                        opStack.push(c);
                    } else if (')' == c) {
                        directCalc(opStack, nrStack, true);
                    } else if ('=' == c) {
                        directCalc(opStack, nrStack, false);
                    } else {
                        compareAndCalc(opStack, nrStack, c);
                    }
                }

            }
        }

        if (sr.length() > 0) {
            nrStack.push(new BigDecimal(sr.toString()));
        }
        directCalc(opStack, nrStack, false);
        return nrStack.pop().toString();
    }

    private static final HashMap<Character, Integer> op_priority = new HashMap<Character, Integer>() {
        {
            put('(', 0);
            put('+', 2);
            put('-', 2);
            put('*', 3);
            put('/', 3);
            put(')', 7);
            put('=', 20);
        }
    };

    private static void compareAndCalc(LinkedList<Character> opStack, LinkedList<BigDecimal> nrStack, char curOp) {
        int priority = op_priority.get(curOp) - op_priority.get(opStack.peek());
        if (-1 == priority || 0 == priority) {
            // 当前运算符优先级低一级 或者 同级别
            char op = opStack.pop();
            BigDecimal nr0 = nrStack.pop();
            BigDecimal nr1 = nrStack.pop();
            BigDecimal rs = bigDecimalCalc(op, nr0, nr1);
            nrStack.push(rs);

            if (opStack.isEmpty()) {
                opStack.push(curOp);
            } else {
                compareAndCalc(opStack, nrStack, curOp);
            }
        } else {
            // 当前运算符优先级高
            opStack.push(curOp);
        }
    }

    private static void directCalc(LinkedList<Character> opStack, LinkedList<BigDecimal> nrStack, boolean isBracket) {
        char curOp = opStack.pop();
        BigDecimal nr0 = nrStack.pop();
        BigDecimal nr1 = nrStack.pop();
        BigDecimal rs = bigDecimalCalc(curOp, nr0, nr1);
        nrStack.push(rs);

        if (isBracket) {
            assert opStack.peek() != null;
            if (opStack.peek().equals('(')) {
                opStack.pop();
            } else {
                directCalc(opStack, nrStack, isBracket);
            }
            // return;
        } else if (!opStack.isEmpty()) {
            directCalc(opStack, nrStack, isBracket);
        }
    }

    private static BigDecimal bigDecimalCalc(char curOp, BigDecimal nr0, BigDecimal nr1) {
        switch (curOp) {
            case '+':
                return nr1.add(nr0);
            case '-':
                return nr1.subtract(nr0);
            case '*':
                return nr1.multiply(nr0);
            case '/':
                return nr1.divide(nr0, 10, RoundingMode.HALF_DOWN);
            default:
                throw new IllegalArgumentException("error op.");
        }
    }

    public static void main(String[] args) {
        String rs = compute("1+(25*(31-27)+3+(66-52+1+2+3)*0)");
        System.out.println(rs);
    }
}
