package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    public WordleDictionary load(String fileName) throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String word = WordUtils.normalize(line);
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }
        return new WordleDictionary(words);
    }
}

