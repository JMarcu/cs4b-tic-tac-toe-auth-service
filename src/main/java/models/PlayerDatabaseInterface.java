package models;

import java.util.UUID;

public class PlayerDatabaseInterface {

    public static void setRefreshToken(UUID uuid, String refreshToken) {
        System.out.println("setRefreshToken");
    }

    public static String getRefreshToken(UUID playerId) {
        System.out.println("getRefreshToken");
        return null;
    }

    public static void deleteRefreshToken(UUID playerId, String refreshToken) {
        System.out.println("deleteRefreshToken");
    }

    public static void setJwt(UUID uuid, String jwt) {
        System.out.println("setJwt");
    }

    public static String getJwt(UUID playerId) {
        System.out.println("getJwt");
        return null;
    }

    public static boolean userNameIsUnique(String userName) {
        System.out.println("userNameIsUnique");
        return true;
    }

    public static boolean validatePassword(String userName, String password){
        System.out.println("validatePassword");
        return true;
    }

    public static boolean setPassword(String userName, String password) {
        System.out.println("setPassword");
        return true;
    }

    public static String getPassword(String userName) {
        System.out.println("getPassword");
        return null;
    }

    public static void setplayer(Player player) {
        System.out.println("setplayer");
    }

    public static Player getPlayer(UUID playerId) {
        System.out.println("getPlayer");
        return null;
    }


    

}
