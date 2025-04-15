package lv.rvt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== Welcome to Wordle Game ===");
            
            while (!Player.isNicknameSet()) {
                System.out.print("Enter your nickname: ");
                String input = scanner.nextLine();
                
                try {
                    Player.setNickname(input);
                    System.out.println("\nHello, " + Player.getNickname() + "!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            
            Menu.main(args);
        } catch (Exception e) {
            System.err.println("Critical error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}