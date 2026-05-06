package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;
    private final Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String getRandomWord(int length) {
        List<String> filtered = new ArrayList<>();
        for (String word : words) {
            if (word.length() == length) {
                filtered.add(word);
            }
        }
        if (filtered.isEmpty()) {
            throw new IllegalStateException("Нет слов подходящей длины");
        }
        return filtered.get(random.nextInt(filtered.size()));
    }

    public List<String> getWordsByLength(int length) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() == length) {
                result.add(word);
            }
        }
        return result;
    }

    public boolean contains(String word) {
        return words.contains(WordUtils.normalize(word));
    }

}
