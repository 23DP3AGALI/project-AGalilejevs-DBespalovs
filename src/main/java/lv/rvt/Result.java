package lv.rvt;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

public class Result {
    private static final String FILE_NAME = "wordle_results.csv";
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void saveResult(boolean success, int attempts, 
                               String correctWord, String lastGuess) {
        try {
            String record = String.join(",",
                LocalDateTime.now().format(FORMATTER),
                Player.getNickname(),
                success ? "WIN" : "LOSE",
                String.valueOf(attempts),
                correctWord,
                lastGuess
            );
            
            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
                if (new File(FILE_NAME).length() == 0) {
                    out.println("Timestamp,Player,Result,Attempts,Word,LastGuess");
                }
                out.println(record);
            }
        } catch (IOException e) {
            System.err.println("Error saving result: " + e.getMessage());
        }
    }

    public static List<String> getStatistics() {
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) {
                return Collections.emptyList();
            }
            
            return Files.lines(Paths.get(FILE_NAME))
                    .skip(1)
                    .filter(line -> line.split(",")[1].equals(Player.getNickname()))
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static int getWinCount() {
        return (int) getStatistics().stream()
            .filter(line -> line.split(",")[2].equals("WIN"))
            .count();
    }

    public static void clearPlayerStatistics() {
        try {
            List<String> allLines = Files.exists(Paths.get(FILE_NAME)) 
                ? Files.readAllLines(Paths.get(FILE_NAME)) 
                : new ArrayList<>();
                
            if (!allLines.isEmpty()) {
                List<String> filteredLines = allLines.stream()
                    .filter(line -> {
                        if (line.startsWith("Timestamp")) return true;
                        return !line.split(",")[1].equals(Player.getNickname());
                    })
                    .collect(Collectors.toList());
                
                Files.write(Paths.get(FILE_NAME), filteredLines);
            }
        } catch (IOException e) {
            System.err.println("Error clearing statistics: " + e.getMessage());
        }
    }
}