package models.ServerMessage;

import models.Player;

public class RegistrationResultMessageBody {
    private String jwt;
    private Player player;
    private String refreshToken;
    private RegistrationResultType result;

    public RegistrationResultMessageBody(RegistrationResultType result){
        this.jwt = null;
        this.player = null;
        this.refreshToken = null;
        this.result = result;
    }

    public RegistrationResultMessageBody(RegistrationResultType result, Player player, String jwt, String refreshToken){
        this.jwt = null;
        this.player = player;
        this.refreshToken = refreshToken;
        this.result = result;
    }

    public String getJwt() {
        return jwt;
    }
    
    public Player getPlayer(){ return player; }

    public String getRefreshToken() {
        return refreshToken;
    }

    public RegistrationResultType getResult(){ return result; }
}

