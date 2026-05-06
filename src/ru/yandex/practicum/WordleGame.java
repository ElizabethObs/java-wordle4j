package ru.yandex.practicum;

import ru.yandex.practicum.exception.InvalidWordException;
import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleGame {

    private final String answer;
    private int attempts = 6;
    private final WordleDictionary dictionary;
    private final List<String> history = new ArrayList<>();
    private List<String> possibleWords;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord(5);
        this.possibleWords = new ArrayList<>(dictionary.getWordsByLength(5));
    }

    public String makeMove(String input) throws InvalidWordException, WordNotFoundInDictionaryException {
        validateWordLength(input);
        validateWordInDictionary(input);

        history.add(input);
        attempts--;

        String hint = buildHint(input);
        updatePossibleWords(input, hint);

        return hint;
    }

    public boolean isWin(String input) {

        return answer.equals(input);
    }

    public boolean isGameOver() {

        return attempts <= 0;
    }

    public int getAttempts() {

        return attempts;
    }

    public String getAnswer() {
        return answer;
    }


    public String giveHint() {
        if (possibleWords.isEmpty()) {
            return "Подсказок нет";
        }
        return possibleWords.get(new Random().nextInt(possibleWords.size()));
    }

    private void validateWordLength(String word) throws InvalidWordException {
        if (word.length() != 5) {
            throw new InvalidWordException("Слово должно быть из 5 букв");
        }
    }

    private void validateWordInDictionary(String word) throws WordNotFoundInDictionaryException {
        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionaryException("Слово не найдено в словаре");
        }
    }

    private String buildHint(String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char c = guess.charAt(i);
            if (c == answer.charAt(i)) {
                hint.append('+');
            } else if (answer.contains(String.valueOf(c))) {
                hint.append('^');
            } else {
                hint.append('-');
            }
        }
        return hint.toString();
    }

    private void updatePossibleWords(String guess, String hint) {
        List<String> newList = new ArrayList<>();
        for (String word : possibleWords) {
            if (matches(word, guess, hint)) {
                newList.add(word);
            }
        }
        possibleWords = newList;
    }

    private boolean matches(String word, String guess, String hint) {
        for (int i = 0; i < 5; i++) {
            char h = hint.charAt(i);
            char g = guess.charAt(i);
            if (h == '+') {
                if (word.charAt(i) != g) return false;
            } else if (h == '^') {
                if (word.charAt(i) == g || !word.contains(String.valueOf(g))) return false;
            } else {
                if (word.contains(String.valueOf(g))) return false;
            }
        }
        return true;
    }
}