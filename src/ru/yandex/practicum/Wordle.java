package ru.yandex.practicum;

import ru.yandex.practicum.exception.InvalidWordException;
import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) throws IOException {

        PrintWriter log;
        try {
            log = new PrintWriter(new FileWriter("log.txt"));
        } catch (Exception e) {
            throw new RuntimeException("Cannot create log file", e);
        }

        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.load("words_ru.txt");

        WordleGame game = new WordleGame(dictionary);
        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            System.out.println("Введите слово (или нажмите Enter для подсказки):");
            String input = WordUtils.normalize(scanner.nextLine());

            if (input.isEmpty()) {
                String hint = game.giveHint();
                System.out.println("Подсказка: " + hint);
                log.println("HINT: " + hint);
                continue;
            }
            try {
                String hint = game.makeMove(input);
                System.out.println(hint);
                log.println(input + " -> " + hint);
                log.println("attempts=" + game.getAttempts());

                if (game.isWin(input)) {
                    System.out.println("Победа!");
                    log.println("WIN");
                    log.close();
                    return;
                }
            } catch (InvalidWordException | WordNotFoundInDictionaryException e) {
                System.out.println(e.getMessage());
                log.println("ERROR: " + e.getMessage());
            }
        }
        System.out.println("Проигрыш. Загаданное слово: " + game.getAnswer());
        log.println("LOSE" + game.getAnswer());
        log.close();
    }
}
