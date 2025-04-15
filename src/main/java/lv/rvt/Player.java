package lv.rvt;

public class Player {
    private static String nickname;
    
    public static void setNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be empty");
        }
        if (nickname.length() > 20) {
            throw new IllegalArgumentException("Nickname too long (max 20 chars)");
        }
        Player.nickname = nickname.trim();
    }
    
    public static String getNickname() {
        return nickname;
    }
    
    public static boolean isNicknameSet() {
        return nickname != null && !nickname.isEmpty();
    }
}