package lv.rvt;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Menu {
    private static final String[] OPTIONS = {"Start Game", "Statistics", "Rules", "Change Nickname", "Exit"};
    private static int selected = 0;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            handleInput();
        }
    }

    private static void printMenu() {
        System.out.println("\n=== WORDLE GAME ===");
        System.out.println("Current player: " + Player.getNickname());
        System.out.println("Select option (1-5 + Enter):");
        
        for (int i = 0; i < OPTIONS.length; i++) {
            System.out.println((i == selected ? "> " : "  ") + (i+1) + ". " + OPTIONS[i]);
        }
    }

    private static void handleInput() {
        String input = scanner.nextLine();
        
        switch (input) {
            case "1": selected = 0; break;
            case "2": selected = 1; break;
            case "3": selected = 2; break;
            case "4": selected = 3; break;
            case "5": selected = 4; break;
            case "":
                executeSelectedOption();
                break;
            default:
                System.out.println("Invalid input. Use 1-5 to select options.");
        }
    }

    private static void executeSelectedOption() {
        System.out.println("\nSelected: " + OPTIONS[selected]);
        
        switch (OPTIONS[selected]) {
            case "Start Game": startGame(); break;
            case "Statistics": showStatistics(); break;
            case "Rules": printRules(); break;
            case "Change Nickname": changeNickname(); break;
            case "Exit": System.exit(0);
        }
    }

    private static void changeNickname() {
        System.out.print("\nEnter new nickname: ");
        String newNickname = scanner.nextLine();
        
        try {
            Player.setNickname(newNickname);
            System.out.println("Nickname successfully changed to: " + Player.getNickname());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void startGame() {
        try {
            String word = new RandomWordGenerator().getWord();
            new Game().start(word);
        } catch (Exception e) {
            System.out.println("Error starting game: " + e.getMessage());
        }
    }

    private static void showStatistics() {
        System.out.println("\n=== STATISTICS ===");
        System.out.println("Player: " + Player.getNickname());
        System.out.println("Total wins: " + Result.getWinCount());
        System.out.println("Recent games:");
        
        List<String> stats = Result.getStatistics();
        if (stats.isEmpty()) {
            System.out.println("No games played yet.");
        } else {
            stats.forEach(System.out::println);
        }
    }

    private static void printRules() {
        System.out.println("\n=== GAME RULES ===");
        System.out.println("1. Guess the hidden 5-letter word");
        System.out.println("2. You have 6 attempts");
        System.out.println("3. After each guess, you get feedback:");
        System.out.println("   🟢 - Correct letter in correct position");
        System.out.println("   🟡 - Correct letter in wrong position");
        System.out.println("   ⚪ - Letter not in the word");
    }
}