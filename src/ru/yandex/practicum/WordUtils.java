package ru.yandex.practicum;

public class WordUtils {
    public static String normalize(String word) {
        return word.toLowerCase().replace("ё", "е").trim();
    }
}
