package com.aston.health.Utils;

public class StringReplace {
    public static String clearString(String string) {
        return string
                .replaceAll("[«»']", "\"")
                .replaceAll("\\s{2,}", " ")
                .replaceAll("№\\s+", "№")
                .replaceAll("(\\s[А-Я]\\.)\\s([А-Я]\\.\\s)", "$1$2");

    }
}
