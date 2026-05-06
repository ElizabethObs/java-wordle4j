package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.exception.InvalidWordException;
import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;

import java.util.List;

class WordleTest {
    private WordleGame game;

    @BeforeEach
    void setUp() {
        WordleDictionary dictionary = new WordleDictionary(List.of("мороз", "огонь", "ропот", "котик",
                "лотос", "кокос"));
        game = new WordleGame(dictionary);
    }

    @Test
    void shouldThrowExceptionWhenWordTooShortTest() throws Exception {
        try {
            game.makeMove("дом");
            fail("InvalidWordException");
        } catch (InvalidWordException e) {
        }
    }

    @Test
    void shouldThrowExceptionWhenWordTooLongTest() throws Exception {
        try {
            game.makeMove("абрикос");
            fail("InvalidWordException");
        } catch (InvalidWordException e) {
        }
    }

    @Test
    void shouldThrowExceptionWhenWordNotInDictionaryTest() throws Exception {
        try {
            game.makeMove("xxxxx");
            fail("WordNotFoundInDictionary");
        } catch (WordNotFoundInDictionaryException e) {
        }
    }

    @Test
    void shouldAcceptValidFiveLetterWordTest() {
        try {
            game.makeMove("мороз");
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void shouldDecreaseAttemptsAfterMoveTest() {
        int before = game.getAttempts();
        try {
            game.makeMove("лотос");
        } catch (Exception e) {
            fail("Should not throw exception");
        }
        assertEquals(before - 1, game.getAttempts());
    }

    @Test
    void shouldEndGameAfterAllAttemptsTest() {
        for (int i = 0; i < 6; i++) {
            try {
                game.makeMove("кокос");
            } catch (Exception ignored) {
            }
        }
        assertTrue(game.getAttempts() >= 0);
    }

    @Test
    void ShouldWinWhenInputEqualsAnswerTest() {
        String answer = game.getAnswer();
        assertTrue(game.isWin(answer));
    }

    @Test
    void hintShouldAlwaysBeFiveCharsTest() {
        String hint = game.giveHint();
        assertEquals(5, hint.length());
    }

    @Test
    void shouldReturnAllPlusForCorrectWordTest() {
        try {
            WordleGame wg = new WordleGame(new WordleDictionary(List.of("огонь")));
            String answer = wg.makeMove("огонь");
            assertEquals("+++++", answer);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void giveHintShouldReturnWordFromDictionaryTest() {
        List<String> words = List.of("мороз", "огонь", "ропот", "котик",
                "лотос", "кокос");
        WordleDictionary testDictionary = new WordleDictionary(words);
        WordleGame testGame = new WordleGame(testDictionary);
        String hint = testGame.giveHint();
        assertNotNull(hint);
        assertTrue(words.contains(hint));
    }
}
