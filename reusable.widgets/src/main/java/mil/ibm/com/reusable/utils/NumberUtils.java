/*
 * Licensed Materials - Property of IBM
 * © Copyright IBM Corporation 2015. All Rights Reserved.
 */

package mil.ibm.com.reusable.utils;

/**
 * Collections of utility functions for operating on numbers.
 */
public class NumberUtils {

    /**
     * Transforms the input integer into a string representation.
     *
     * @param number
     * @return The transformed integer value.
     */
    public static String transformNumber(int number) {
        int length = String.valueOf(number).length();
        int div = number / (int) (Math.pow(10, (length - 1)));
        int numberOfZeroes;
        String suffix;
        if (length >= 10) {
            suffix = "B";
            numberOfZeroes = length - 10;
        } else if (length >= 7) {
            suffix = "M";
            numberOfZeroes = length - 7;
        } else if (length >= 4) {
            suffix = "K";
            numberOfZeroes = length - 4;
        } else {
            div = number;
            suffix = "";
            numberOfZeroes = 0;
        }

        StringBuilder strBuilder = new StringBuilder(String.valueOf(div));
        for (int i = 0; i < numberOfZeroes; i++) {
            strBuilder.append('0');
        }
        strBuilder.append(suffix);
        return strBuilder.toString();
    }

    /**
     * Converts the total number of seconds to a string value that contains the representation in
     * minutes and seconds.
     *
     * @param totalSeconds
     * @return The string representation in minutes and seconds.
     */
    public static String convertSecsToMins(int totalSeconds) {
        String formattedTime = null;
        if (totalSeconds >= 0) {
            int remainder = totalSeconds % 3600;
            int minutes = remainder / 60;
            int seconds = remainder % 60;
            String displaySeconds = (seconds <= 9 ? "0"
                    + String.valueOf(seconds) : String.valueOf(seconds));
            String displayMinutes = (minutes == 0 ? "00" : String
                    .valueOf(minutes));
            formattedTime = displayMinutes + ":" + displaySeconds;
        }
        return formattedTime;
    }

    /**
     * Computes the percentage for the given total and numerator values.
     *
     * @param numerator
     * @param total
     * @return The computed percentage.
     */
    public static int calculatePercentage(double numerator, double total) {
        if (total != 0) {
            double percentage = (numerator / total);
            return (int) Math.round((percentage * 100.00));
        } else {
            return 0;
        }
    }
}
