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
}
