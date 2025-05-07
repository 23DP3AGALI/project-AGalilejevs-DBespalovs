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
        return getFilteredStatistics(1, 6, null, null);
    }

    public static List<String> getFilteredStatistics(int minAttempts, int maxAttempts, 
                                                   LocalDate fromDate, LocalDate toDate) {
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) {
                return Collections.emptyList();
            }

            return Files.lines(Paths.get(FILE_NAME))
                    .skip(1)
                    .filter(line -> {
                        String[] parts = line.split(",");
                        if (!parts[1].equals(Player.getNickname())) return false;
                        
                        int attempts = Integer.parseInt(parts[3]);
                        LocalDateTime gameTime = LocalDateTime.parse(parts[0], FORMATTER);
                        
                        boolean attemptsMatch = attempts >= minAttempts && attempts <= maxAttempts;
                        boolean dateMatch = (fromDate == null || gameTime.toLocalDate().isAfter(fromDate)) &&
                                          (toDate == null || gameTime.toLocalDate().isBefore(toDate));
                        
                        return attemptsMatch && dateMatch;
                    })
                    .sorted(Comparator.comparingInt(line -> Integer.parseInt(line.split(",")[3])))
                    .limit(20)
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
}