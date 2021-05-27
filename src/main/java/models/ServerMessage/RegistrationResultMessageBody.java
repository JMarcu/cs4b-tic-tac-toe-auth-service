package models.ServerMessage;

import models.Player;

public class RegistrationResultMessageBody {
    private String jwt;
    private Player player;
    private RegistrationResultType result;

    public RegistrationResultMessageBody(RegistrationResultType result){
        this.jwt = null;
        this.player = null;
        this.result = result;
    }

    public RegistrationResultMessageBody(RegistrationResultType result, Player player, String jwt){
        this.jwt = null;
        this.player = player;
        this.result = result;
    }

    public String getJwt() {
        return jwt;
    }
    public Player getPlayer(){ return player; }
    public RegistrationResultType getResult(){ return result; }
}

