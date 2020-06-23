package com.bitco.nsuns.items;

/**
 * Class that contains methods to deal with maths and calculations.
 */
public class Calculate {

    /**
     * Rounds a given number to the closest factor.
     * @param value to round.
     * @param factor to round to.
     * @return the rounded figure.
     */
    public static float round(float value, float factor) {
        return Math.round(value / factor) * factor;
    }

    public static float[] arrayRound(float[] numbers, float factor) {
        float[] rounded = new float[numbers.length];
        for(int i=0; i<numbers.length; i++) {
            if (numbers[i] == 0) {
                rounded[i] = numbers[i];
            }
            else {
                rounded[i] = Calculate.round(numbers[i], factor);
            }

        }

        return rounded;
    }
}
