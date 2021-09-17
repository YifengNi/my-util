package com.yifeng;

/**
 * @Author niyf
 * @Date 2021-9-2 11:19
 * @Description
 **/
public class Test {

    public static void main(String[] args) {
        // Object obj = 123.4567;
        // BigDecimal decimal = BigDecimal.valueOf((Double) obj);
        // System.out.println("decimal = " + decimal);
        // BigDecimal res = decimal.add(BigDecimal.valueOf(1.23));
        // System.out.println("res = " + res);

        // BigDecimal n1 = BigDecimal.valueOf(0.0000);
        // BigDecimal n2 = BigDecimal.valueOf(0);
        // System.out.println(n1.compareTo(BigDecimal.ZERO));


        String s = "com/xiaopeng/car/mapper/oas/CarPreLockOperationMapper";
        int index = s.lastIndexOf('/');
        System.out.println(index);
        System.out.println(s.substring(index + 1));

    }
}
