package lv.rvt;

import java.util.Scanner;

public class Game {
    private static final int MAX_ATTEMPTS = 6;
    private final Scanner scanner = new Scanner(System.in);

    public void start(String correctWord) {
        System.out.println("\n=== New Game ===");
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
            System.out.println("🟢🟢🟢🟢🟢 Congratulations! You won in " + attempt + " attempts!");
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
                feedback.append("🟢");
            } else if (correct.indexOf(c) >= 0) {
                feedback.append("🟡");
            } else {
                feedback.append("⚪");
            }
        }
        
        System.out.println("Feedback: " + feedback);
    }

    private void endGame(String correctWord) {
        System.out.println("\nGame over! The word was: " + correctWord);
        Result.saveResult(false, MAX_ATTEMPTS, correctWord, "-");
    }
}   