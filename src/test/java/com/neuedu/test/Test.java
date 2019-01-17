package com.neuedu.test;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal("0.05");
        BigDecimal bigDecimal1=new BigDecimal("0.01");
        System.out.println(bigDecimal.add(bigDecimal1));
        System.out.println(bigDecimal.divide(bigDecimal1));
        System.out.println(bigDecimal.subtract(bigDecimal1));
        System.out.println(bigDecimal.multiply(bigDecimal1));
    }
}
