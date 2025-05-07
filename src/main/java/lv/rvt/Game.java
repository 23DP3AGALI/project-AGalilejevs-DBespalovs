package lv.rvt;

import java.util.Scanner;

public class Game {
    private static final int MAX_ATTEMPTS = 6;
    private final Scanner scanner = new Scanner(System.in);

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GRAY = "\u001B[90m";

    public void start(String correctWord) {
        System.out.println("\n=== NEW GAME ===");
        System.out.println("Guess the 5-letter word. You have " + MAX_ATTEMPTS + " attempts.");
        
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String guess = getValidGuess(attempt);
            
            if (processGuess(correctWord, guess, attempt)) {
                return;
            }
        }
        
        endGame(correctWord);
    }

    private String getValidGuess(int attempt) {
        while (true) {
            System.out.printf("Attempt %d/%d: ", attempt, MAX_ATTEMPTS);
            String guess = scanner.nextLine().toLowerCase().trim();
            
            if (guess.length() == 5) {
                return guess;
            }
            System.out.println("Please enter exactly 5 letters.");
        }
    }

    private boolean processGuess(String correct, String guess, int attempt) {
        if (guess.equals(correct)) {
            System.out.println("\u001B[32mCorrect! You won in " + attempt + " attempts!\u001B[0m");
            Result.saveResult(true, attempt, correct, guess);
            return true;
        }
    
        showFeedback(correct, guess);
        return false;
    }
    
    private void showFeedback(String correct, String guess) {
        StringBuilder feedback = new StringBuilder();
    
        for (int i = 0; i < correct.length(); i++) {
            char c = guess.charAt(i);
            if (c == correct.charAt(i)) {
                feedback.append("\u001B[32m").append(c).append("\u001B[0m");
            } else if (correct.indexOf(c) >= 0) {
                feedback.append("\u001B[33m").append(c).append("\u001B[0m");
            } else {
                feedback.append("\u001B[37m").append(c).append("\u001B[0m");
            }
        }
    
        System.out.println("Feedback: " + feedback);
    }

    private void endGame(String correctWord) {
        System.out.println("\nGame over! The word was: " + ANSI_GREEN + correctWord + ANSI_RESET);
        Result.saveResult(false, MAX_ATTEMPTS, correctWord, "-");
    }
}