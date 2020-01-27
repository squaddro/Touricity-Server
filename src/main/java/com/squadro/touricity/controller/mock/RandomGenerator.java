package com.squadro.touricity.controller.mock;

import java.util.Random;

public class RandomGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnoprstuvyzxw";

    public static int randomIntGenerator(int bound){
        return new Random().nextInt(bound);
    }
    public static double randomDoubleGenerator(int min, int max){
        return min + (max-min) * new Random().nextDouble();
    }
    public static String randomAlphaNumericGenerator(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
